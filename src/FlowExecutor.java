import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import audio.Synthesizer;
import environment.Environment;

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
                interpreter.bpm = (Integer) args.get(0);
                System.out.println("[Audio] BPM set to " + interpreter.bpm);
                return null;
            case "load_synth":
                String synthName = args.get(0).toString();
                Synthesizer synth = interpreter.audioEngine.createSynthesizer(synthName);
                System.out.println("[Audio] Loaded MIDI Synth: " + synthName);
                return synth;
            case "use_synth":
                interpreter.currentSynth = (Synthesizer) args.get(0);
                return null;
            default:
                try {
                    SymNoteParser.TrackDeclContext trackCtx = (SymNoteParser.TrackDeclContext) interpreter.env
                            .getTrack(functionName);

                    // Create isolated scope for Track
                    Environment previousEnv = interpreter.env;
                    interpreter.env = new Environment(previousEnv);

                    if (trackCtx.parameters() != null) {
                        for (int i = 0; i < trackCtx.parameters().param().size(); i++) {
                            String paramName = trackCtx.parameters().param(i).ID().getText();
                            interpreter.env.define(paramName, args.get(i));
                        }
                    }

                    System.out.println("[Track] Started: " + functionName);
                    Synthesizer oldSynth = interpreter.currentSynth;

                    interpreter.visit(trackCtx.blockLVL2());

                    interpreter.currentSynth = oldSynth;
                    interpreter.env = previousEnv;
                    System.out.println("[Track] Completed: " + functionName);
                } catch (Exception e) {
                    System.err.println("Cannot run track '" + functionName + "'. " + e.getMessage());
                }
                return null;
        }
    }

    public Object executeParallel(SymNoteParser.ParallelStmtContext ctx) {
        ExecutorService executor = Executors.newFixedThreadPool(ctx.parallelEntry().size());

        for (SymNoteParser.ParallelEntryContext pCtx : ctx.parallelEntry()) {
            final Environment threadEnv = new Environment(interpreter.env);
            final Synthesizer threadSynth = interpreter.currentSynth;
            final int threadBpm = interpreter.bpm;

            executor.submit(() -> {
                SymNoteInterpreter threadVisitor = new SymNoteInterpreter();
                threadVisitor.env = threadEnv;
                threadVisitor.audioEngine = interpreter.audioEngine; // Share the global audio engine instance
                threadVisitor.bpm = threadBpm;
                threadVisitor.currentSynth = threadSynth;

                threadVisitor.visit(pCtx.callExpr());
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("[Audio] Track Execution Complete.");
        return null;
    }
}
