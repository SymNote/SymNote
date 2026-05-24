import environment.Environment;
import environment.Variable;
import gen.SymNoteParser;
import environment.ActivationRecord;

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
                    throw new RuntimeException(
                            "Cannot pass void value as argument at line " + ctx.getStart().getLine());
                }
                args.add(argVal);
            }
        }

        switch (functionName) {
            case "set_bpm":
                if (args.isEmpty()) {
                    throw new RuntimeException(
                            "set_bpm() requires one numeric argument at line " + ctx.getStart().getLine());
                }
                Object bpmArg = args.getFirst();
                if (!(bpmArg instanceof Number)) {
                    throw new RuntimeException(
                            "set_bpm() requires an int or float, but got "
                                    + ErrorHelper.typeName(bpmArg) + " (value: '" + bpmArg + "') at line "
                                    + ctx.getStart().getLine());
                }
                float bpmVal = ((Number) bpmArg).floatValue();
                if (bpmVal <= 0) {
                    throw new RuntimeException(
                            "set_bpm() requires a positive tempo, but got " + bpmVal
                                    + " at line " + ctx.getStart().getLine()
                                    + ". Typical values are between 40 and 300.");
                }
                interpreter.bpm = bpmVal;
                return null;
            case "load_synth":
                if (args.isEmpty()) {
                    throw new RuntimeException(
                            "load_synth() requires one string argument (the instrument name) at line "
                                    + ctx.getStart().getLine());
                }
                if (!(args.getFirst() instanceof String)) {
                    throw new RuntimeException(
                            "load_synth() requires a string instrument name, but got "
                                    + ErrorHelper.typeName(args.getFirst()) + " at line "
                                    + ctx.getStart().getLine()
                                    + ". Example: load_synth(\"piano\")");
                }
                String synthName = String.valueOf(args.getFirst());
                String key = synthName.trim().toLowerCase().replace("-", "").replace("_", "").replace(" ", "");
                java.util.List<String> validSynths = java.util.Arrays.asList(
                    "piano", "organ", "bass", "sawtooth", "square", "guitar", "strings", 
                    "pad", "choir", "trumpet", "sax", "flute", "bell", "pluck"
                );
                if (!validSynths.contains(key)) {
                    throw new RuntimeException(
                            "Unknown synth name '" + synthName + "' at line " 
                            + ctx.getStart().getLine() 
                            + ". See documentation for supported instruments.");
                }
                return new Synth(synthName);
            case "use_synth":
                if (args.isEmpty()) {
                    throw new RuntimeException(
                            "use_synth() requires one argument (a synth variable) at line "
                                    + ctx.getStart().getLine());
                }
                if (!(args.getFirst() instanceof Synth)) {
                    throw new RuntimeException(
                            "use_synth() requires a synth variable, but got "
                                    + ErrorHelper.typeName(args.getFirst()) + " at line "
                                    + ctx.getStart().getLine()
                                    + ". Did you forget to assign the result of load_synth()?");
                }
                interpreter.currentSynthName = ((Synth) args.getFirst()).getName();
                return null;
            case "print":
                if (args.isEmpty()) {
                    throw new RuntimeException(
                            "print() requires one argument at line " + ctx.getStart().getLine());
                }
                interpreter.output.println(args.getFirst());
                return null;
            default:
                if (interpreter.routines.containsKey(functionName)) {
                    SymNoteParser.RoutineDeclContext routineCtx = interpreter.routines.get(functionName);

                    Environment routineEnv = new Environment(interpreter.globalEnv);

                    ActivationRecord frame = new ActivationRecord(functionName, routineEnv);
                    interpreter.callStack.push(frame, ctx.getStart().getLine());

                    Environment callerEnv = interpreter.env;
                    interpreter.env = routineEnv;

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
                                    interpreter.env.define(paramName,
                                            new Variable(paramType, ((Integer) argValue).intValue()));
                                } else if (paramType.equals("float")) {
                                    interpreter.env.define(paramName,
                                            new Variable(paramType, ((Number) argValue).floatValue()));
                                } else {
                                    interpreter.env.define(paramName, new Variable(paramType, argValue));
                                }
                            }
                        }

                        // Execute routine block
                        try {
                            interpreter.visit(routineCtx.blockRoutine());
                        } catch (ReturnException re) {
                            throw re; // let the outer catch (ReturnException) handle it
                        } catch (RuntimeException e) {
                            // Attach call-stack trace if not already present
                            String msg = e.getMessage();
                            String trace = ErrorHelper.formatCallStack(interpreter.callStack);
                            if (msg != null && !trace.isEmpty() && !msg.contains("Call stack:")) {
                                throw new RuntimeException(msg + trace);
                            }
                            throw e;
                        }

                        // If we get here, no return statement was hit
                        if (!routineCtx.type().getText().equals("void")) {
                            throw new RuntimeException(
                                    "Routine '" + functionName + "' declared return type '"
                                            + routineCtx.type().getText() + "' but has no return statement"
                                            + " (defined at line " + routineCtx.getStart().getLine() + ")");
                        }
                        return null;

                    } catch (ReturnException e) {
                        Object returnValue = e.getValue();
                        String expectedType = routineCtx.type().getText();
                        int line = ctx.getStart().getLine();

                        if (expectedType.equals("void")) {
                            if (returnValue != null) {
                                throw new RuntimeException(
                                        "Void routine '" + functionName
                                                + "' cannot return a value — remove 'return <value>' at line " + line);
                            }
                            return null;
                        }

                        if (returnValue == null) {
                            throw new RuntimeException(
                                    "Routine '" + functionName + "' must return a value of type '"
                                            + expectedType + "', but returned nothing at line " + line);
                        }

                        // Cast return value to the expected type
                        if (expectedType.equals("float")) {
                            interpreter.checkType("float", returnValue,
                                    "return value of routine '" + functionName + "'", line);
                            return ((Number) returnValue).floatValue();
                        }

                        // Check if the type matches exactly (strict check - no implicit casting)
                        interpreter.checkTypeStrict(expectedType, returnValue, "return value of " + functionName, line);

                        return returnValue;
                    } catch (RuntimeException e) {
                        String msg = e.getMessage();
                        String trace = ErrorHelper.formatCallStack(interpreter.callStack);
                        if (msg != null && !trace.isEmpty() && !msg.contains("Call stack:")) {
                            throw new RuntimeException(msg + trace);
                        }
                        throw e;
                    } finally {
                        interpreter.callStack.pop();
                        interpreter.env = callerEnv;
                    }
                }

                // Otherwise, it must be a Track
                SymNoteParser.TrackDeclContext trackCtx;
                try {
                    trackCtx = (SymNoteParser.TrackDeclContext) interpreter.env.getTrack(functionName);
                } catch (RuntimeException e) {
                    // Neither a routine nor a track, give a suggestion
                    String hint = ErrorHelper.suggest(functionName, interpreter.env, interpreter.routines);
                    throw new RuntimeException(
                            "Undefined routine or track '" + functionName + "' at line "
                                    + ctx.getStart().getLine() + hint);
                }

                Environment trackEnv = new Environment(interpreter.globalEnv);
                ActivationRecord trackFrame = new ActivationRecord(functionName, trackEnv);
                interpreter.callStack.push(trackFrame, ctx.getStart().getLine());

                Environment previousEnv = interpreter.env;
                String previousSynth = interpreter.currentSynthName;
                interpreter.env = trackEnv;

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
                                interpreter.env.define(paramName,
                                        new Variable(paramType, ((Integer) argValue).intValue()));
                            } else if (paramType.equals("float")) {
                                interpreter.env.define(paramName,
                                        new Variable(paramType, ((Number) argValue).floatValue()));
                            } else {
                                interpreter.env.define(paramName, new Variable(paramType, argValue));
                            }
                        }
                    }

                    interpreter.visit(trackCtx.blockTrack());
                } finally {
                    interpreter.callStack.pop();
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

    // For iteration
    public Object executeParallel(SymNoteParser.ParallelIterationStmtContext ctx) {
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