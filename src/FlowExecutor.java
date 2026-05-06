import environment.Environment;
import environment.Variable;
import gen.SymNoteParser;

import java.util.ArrayList;
import java.util.List;

public class FlowExecutor {
    private final SymNoteInterpreter interpreter;

    public FlowExecutor(SymNoteInterpreter interpreter) {
        this.interpreter = interpreter;
    }

    public Object executeCall(SymNoteParser.CallExprContext ctx) {
        String functionName = ctx.ID().getText();
        List<Object> args = new ArrayList<>();

        if (ctx.arguments() != null) {
            for (SymNoteParser.ExpressionContext exprCtx : ctx.arguments().expression()) {
                Object argVal = interpreter.visit(exprCtx);
                if (argVal == null) {
                    throw new RuntimeException("Cannot pass void value as argument at line " + ctx.getStart().getLine());
                }
                args.add(argVal);
            }
        }

        switch (functionName) {
            case "set_bpm":
                if (args.isEmpty()) {
                    throw new RuntimeException(
                            "set_bpm requires one argument at line " + ctx.getStart().getLine());
                }
                interpreter.bpm = interpreter.toNumber(args.get(0), ctx.getStart().getLine()).floatValue();
                return null;
            case "load_synth":
                if (args.isEmpty()) {
                    throw new RuntimeException(
                            "load_synth requires one argument at line " + ctx.getStart().getLine());
                }
                return String.valueOf(args.get(0));
            case "use_synth":
                if (args.isEmpty()) {
                    throw new RuntimeException(
                            "use_synth requires one argument at line " + ctx.getStart().getLine());
                }
                interpreter.currentSynthName = String.valueOf(args.get(0));
                return null;
            case "print":
                if (args.isEmpty()) {
                    throw new RuntimeException(
                            "print requires one argument at line " + ctx.getStart().getLine());
                }
                interpreter.output.println(args.getFirst());
                return null;
            default:
                if (interpreter.routines.containsKey(functionName)) {
                    SymNoteParser.RoutineDeclContext routineCtx = interpreter.routines.get(functionName);
                    Environment previousEnv = interpreter.env;
                    interpreter.env = new Environment(previousEnv.getGlobal());

                    try {
                        int expectedParams = routineCtx.parameters() == null ? 0
                                : routineCtx.parameters().param().size();
                        if (args.size() != expectedParams) {
                            throw new RuntimeException("Routine '" + functionName + "' expected " + expectedParams
                                    + " arguments but got " + args.size() + " at line " + ctx.getStart().getLine());
                        }

                        // Bind arguments to parameters
                        if (routineCtx.parameters() != null) {
                            for (int i = 0; i < routineCtx.parameters().param().size(); i++) {
                                String paramName = routineCtx.parameters().param(i).ID().getText();
                                String paramType = routineCtx.parameters().param(i).type().getText();
                                Object argValue = args.get(i);

                                interpreter.checkType(paramType, argValue,
                                        "argument '" + paramName + "' in routine '" + functionName + "'",
                                        ctx.getStart().getLine());

                                if (paramType.equals("int")) {
                                    interpreter.env.define(paramName, new Variable(paramType, ((Integer) argValue).intValue()));
                                } else if (paramType.equals("float")) {
                                    interpreter.env.define(paramName, new Variable(paramType, ((Number) argValue).floatValue()));
                                } else {
                                    interpreter.env.define(paramName, new Variable(paramType, argValue));
                                }
                            }
                        }

                        // Execute routine block
                        interpreter.visit(routineCtx.blockRoutine());

                        // If we get here, no return statement was hit
                        if (!routineCtx.type().getText().equals("void")) {
                            throw new RuntimeException("Routine '" + functionName + "' missing return statement.");
                        }
                        return null;

                    } catch (ReturnException e) {
                        // Return statement was hit, catch the value
                        Object returnValue = e.getValue();
                        String expectedType = routineCtx.type().getText();
                        int line = ctx.getStart().getLine();

                        // Check if void returns a value
                        if (expectedType.equals("void")) {
                            if (returnValue != null) {
                                throw new RuntimeException(
                                        "Void routine '" + functionName + "' cannot return a value at line " + line);
                            }
                            return null;
                        }

                        // Check if a non-void routine returned nothing
                        if (returnValue == null) {
                            throw new RuntimeException("Routine '" + functionName + "' must return a value of type '"
                                    + expectedType + "' at line " + line);
                        }


                        // Cast return value to the expected type
                        if (expectedType.equals("float")) {
                            interpreter.checkType("float", returnValue, "return value of routine '" + functionName + "'", line);
                            return ((Number) returnValue).floatValue();
                        }

                        // Check if the type matches exactly (strict check - no implicit casting)
                        interpreter.checkTypeStrict(expectedType, returnValue, "return value of " + functionName, line);

                        return returnValue;
                    } finally {
                        interpreter.env = previousEnv; // Restore scope
                    }
                }

                // Otherwise, it must be a Track
                SymNoteParser.TrackDeclContext trackCtx = (SymNoteParser.TrackDeclContext) interpreter.env
                        .getTrack(functionName);
                if (trackCtx == null) {
                    throw new RuntimeException(
                            "Undefined function or track '" + functionName + "' at line " + ctx.getStart().getLine());
                }

                Environment previousEnv = interpreter.env;
                String previousSynth = interpreter.currentSynthName;
                interpreter.env = new Environment(previousEnv.getGlobal());

                try {
                    int expectedParams = trackCtx.parameters() == null ? 0 : trackCtx.parameters().param().size();
                    if (args.size() != expectedParams) {
                        throw new RuntimeException("Track '" + functionName + "' expected " + expectedParams
                                + " argument(s) but got " + args.size() + " at line " + ctx.getStart().getLine());
                    }

                    if (trackCtx.parameters() != null) {
                        for (int i = 0; i < trackCtx.parameters().param().size(); i++) {
                            String paramName = trackCtx.parameters().param(i).ID().getText();
                            String paramType = trackCtx.parameters().param(i).type().getText();
                            Object argValue = args.get(i);

                            interpreter.checkType(paramType, argValue,
                                    "argument '" + paramName + "' in track '" + functionName + "'",
                                    ctx.getStart().getLine());

                            if (paramType.equals("int")) {
                                interpreter.env.define(paramName, new Variable(paramType, ((Integer) argValue).intValue()));
                            } else if (paramType.equals("float")) {
                                interpreter.env.define(paramName, new Variable(paramType, ((Number) argValue).floatValue()));
                            } else {
                                interpreter.env.define(paramName, new Variable(paramType, argValue));
                            }
                        }
                    }

                    interpreter.visit(trackCtx.blockLVL2());
                } finally {
                    interpreter.currentSynthName = previousSynth;
                    interpreter.env = previousEnv;
                }

                return null;
        }
    }

    public Object executeParallel(SymNoteParser.ParallelStmtContext ctx) {
        long startTick = interpreter.currentTick;
        long maxTick = startTick;
        Environment baseEnv = interpreter.env;
        String baseSynth = interpreter.currentSynthName;
        float baseBpm = interpreter.bpm;

        for (SymNoteParser.ParallelEntryContext entryCtx : ctx.parallelEntry()) {
            interpreter.currentTick = startTick;
            interpreter.env = new Environment(baseEnv);
            interpreter.currentSynthName = baseSynth;
            interpreter.bpm = baseBpm;

            interpreter.visit(entryCtx.callExpr());
            maxTick = Math.max(maxTick, interpreter.currentTick);
        }

        interpreter.env = baseEnv;
        interpreter.currentSynthName = baseSynth;
        interpreter.bpm = baseBpm;
        interpreter.currentTick = maxTick;
        return null;
    }
}