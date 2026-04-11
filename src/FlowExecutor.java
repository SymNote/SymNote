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
                args.add(interpreter.visit(exprCtx));
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
            default:
                SymNoteParser.TrackDeclContext trackCtx = (SymNoteParser.TrackDeclContext) interpreter.env
                        .getTrack(functionName);

                Environment previousEnv = interpreter.env;
                String previousSynth = interpreter.currentSynthName;
                interpreter.env = new Environment(previousEnv);

                try {
                    int expectedParams = trackCtx.parameters() == null ? 0 : trackCtx.parameters().param().size();
                    if (args.size() != expectedParams) {
                        throw new RuntimeException(
                                "Track '" + functionName + "' expected " + expectedParams + " argument(s) but got "
                                        + args.size() + " at line " + ctx.getStart().getLine());
                    }

                    if (trackCtx.parameters() != null) {
                        for (int i = 0; i < trackCtx.parameters().param().size(); i++) {
                            String paramName = trackCtx.parameters().param(i).ID().getText();
                            interpreter.env.define(paramName, new Variable("int", args.get(i)));
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