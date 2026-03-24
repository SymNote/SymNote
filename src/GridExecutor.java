import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.tree.ParseTree;

public class GridExecutor {
    private final SymNoteInterpreter interpreter;
    private final List<Integer> activeNotes = new ArrayList<>();

    public GridExecutor(SymNoteInterpreter interpreter) {
        this.interpreter = interpreter;
    }

    private int noteToMidi(String noteStr) {
        String[] noteNames = { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };
        String note = noteStr.substring(0, 1).toUpperCase();
        int modifier = 0;
        int octaveIndex = 1;

        if (noteStr.contains("#")) {
            modifier = 1;
            octaveIndex = 2;
        } else if (noteStr.contains("b")) {
            modifier = -1;
            octaveIndex = 2;
        }

        int octave = Integer.parseInt(noteStr.substring(octaveIndex));
        int noteIndex = -1;
        for (int i = 0; i < noteNames.length; i++) {
            if (noteNames[i].equals(note)) {
                noteIndex = i;
                break;
            }
        }
        return (octave + 1) * 12 + noteIndex + modifier;
    }

    public Object executeGridStmt(SymNoteParser.GridStmtContext ctx) {
        String resStr = ctx.RESOLUTION().getText();
        String[] parts = resStr.split("/");
        float num = Float.parseFloat(parts[0]);
        float den = Float.parseFloat(parts[1]);

        float beatsToWait = (num / den) * 4.0f;
        long waitMs = (long) (beatsToWait * (60000.0f / interpreter.bpm));

        if (ctx.gridSequence() != null) {
            executeGridSequence(ctx.gridSequence(), waitMs);
        }
        return null;
    }

    private void executeGridSequence(ParseTree node, long waitMs) {
        if (node instanceof SymNoteParser.GridSequenceContext) {
            SymNoteParser.GridSequenceContext ctx = (SymNoteParser.GridSequenceContext) node;
            if (ctx.gridPlayable() != null) {
                playPlayable(ctx.gridPlayable(), waitMs);
                if (ctx.gridTailPlayable() != null)
                    executeGridSequence(ctx.gridTailPlayable(), waitMs);
            } else {
                stopAllNotes();
                sleep(waitMs);
                if (ctx.gridTailNoHold() != null)
                    executeGridSequence(ctx.gridTailNoHold(), waitMs);
            }
        } else if (node instanceof SymNoteParser.GridTailPlayableContext) {
            SymNoteParser.GridTailPlayableContext ctx = (SymNoteParser.GridTailPlayableContext) node;
            if (ctx.gridPlayable() != null) {
                playPlayable(ctx.gridPlayable(), waitMs);
                if (ctx.gridTailPlayable() != null)
                    executeGridSequence(ctx.gridTailPlayable(), waitMs);
            } else if (ctx.getText().startsWith("~")) {
                sleep(waitMs);
                if (ctx.gridTailPlayable() != null)
                    executeGridSequence(ctx.gridTailPlayable(), waitMs);
            } else {
                stopAllNotes();
                sleep(waitMs);
                if (ctx.gridTailNoHold() != null)
                    executeGridSequence(ctx.gridTailNoHold(), waitMs);
            }
        } else if (node instanceof SymNoteParser.GridTailNoHoldContext) {
            SymNoteParser.GridTailNoHoldContext ctx = (SymNoteParser.GridTailNoHoldContext) node;
            if (ctx.gridPlayable() != null) {
                playPlayable(ctx.gridPlayable(), waitMs);
                if (ctx.gridTailPlayable() != null)
                    executeGridSequence(ctx.gridTailPlayable(), waitMs);
            } else {
                stopAllNotes();
                sleep(waitMs);
                if (ctx.gridTailNoHold() != null)
                    executeGridSequence(ctx.gridTailNoHold(), waitMs);
            }
        }
    }

    private void playPlayable(SymNoteParser.GridPlayableContext ctx, long waitMs) {
        stopAllNotes();

        int velocity = 100;
        if (ctx.gridVolModifier() != null) {
            String valueStr = ctx.gridVolModifier().gridVolValue().getText();
            if (ctx.gridVolModifier().gridVolValue().ID() != null) {
                valueStr = interpreter.env.get(valueStr).toString();
            }
            float volume = Float.parseFloat(valueStr);
            if (volume <= 1.0f && valueStr.contains(".")) {
                velocity = (int) (volume * 127);
            } else {
                velocity = (int) volume;
            }
        }

        String sName = (interpreter.currentSynth != null) ? interpreter.currentSynth.getName() : "None";

        if (ctx.noteElement() != null) {
            System.out.print("[" + sName + "] Playing Note: " + ctx.noteElement().getText());
            int midiNote = noteToMidi(ctx.noteElement().getText());
            activeNotes.add(midiNote);
            if (interpreter.currentSynth != null)
                interpreter.currentSynth.noteOn(midiNote, velocity);
        } else if (ctx.gridChord() != null) {
            System.out.print("[" + sName + "] Playing Chord: [");
            for (SymNoteParser.NoteElementContext nCtx : ctx.gridChord().noteElement()) {
                System.out.print(nCtx.getText() + " ");
                int midiNote = noteToMidi(nCtx.getText());
                activeNotes.add(midiNote);
                if (interpreter.currentSynth != null)
                    interpreter.currentSynth.noteOn(midiNote, velocity);
            }
            System.out.print("]");
        }
        System.out.println("  (" + waitMs + "ms)");

        sleep(waitMs);
    }

    private void stopAllNotes() {
        if (interpreter.currentSynth != null) {
            for (int note : activeNotes) {
                interpreter.currentSynth.noteOff(note);
            }
        }
        activeNotes.clear();
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
