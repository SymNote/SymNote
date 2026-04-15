import java.util.ArrayList;
import java.util.List;

import gen.SymNoteParser;
import midi.MusicalEvent;
import org.antlr.v4.runtime.tree.ParseTree;

public class GridExecutor {
    private final SymNoteInterpreter interpreter;

    private static class PendingNote {
        private final long startTick;
        private final int pitch;
        private final int velocity;
        private final String synthName;

        private PendingNote(long startTick, int pitch, int velocity, String synthName) {
            this.startTick = startTick;
            this.pitch = pitch;
            this.velocity = velocity;
            this.synthName = synthName;
        }
    }

    public GridExecutor(SymNoteInterpreter interpreter) {
        this.interpreter = interpreter;
    }

    public Object executeGridStmt(SymNoteParser.GridStmtContext ctx) {
        long stepTicks = resolutionToTicks(ctx.RESOLUTION().getText());
        List<PendingNote> activeNotes = new ArrayList<>();

        if (ctx.gridSequence() != null) {
            processGridSequence(ctx.gridSequence(), stepTicks, activeNotes);
        }

        closeActiveNotes(activeNotes, interpreter.currentTick);
        return null;
    }

    private long resolutionToTicks(String resolutionText) {
        String[] parts = resolutionText.split("/");
        float numerator = Float.parseFloat(parts[0]);
        float denominator = Float.parseFloat(parts[1]);
        float beats = (numerator / denominator) * 4.0f;
        long ticks = Math.round(beats * interpreter.getTicksPerBeat());
        return Math.max(1, ticks);
    }

    private void processGridSequence(ParseTree node, long stepTicks, List<PendingNote> activeNotes) {
        if (node instanceof SymNoteParser.GridSequenceContext) {
            SymNoteParser.GridSequenceContext seqCtx = (SymNoteParser.GridSequenceContext) node;
            if (seqCtx.gridPlayable() != null) {
                processPlayable(seqCtx.gridPlayable(), stepTicks, activeNotes);
                if (seqCtx.gridTailPlayable() != null) {
                    processGridSequence(seqCtx.gridTailPlayable(), stepTicks, activeNotes);
                }
            } else {
                processRest(stepTicks, activeNotes);
                if (seqCtx.gridTailNoHold() != null) {
                    processGridSequence(seqCtx.gridTailNoHold(), stepTicks, activeNotes);
                }
            }
            return;
        }

        if (node instanceof SymNoteParser.GridTailPlayableContext) {
            SymNoteParser.GridTailPlayableContext tailPlayableCtx = (SymNoteParser.GridTailPlayableContext) node;
            if (tailPlayableCtx.gridPlayable() != null) {
                processPlayable(tailPlayableCtx.gridPlayable(), stepTicks, activeNotes);
                if (tailPlayableCtx.gridTailPlayable() != null) {
                    processGridSequence(tailPlayableCtx.gridTailPlayable(), stepTicks, activeNotes);
                }
            } else if (tailPlayableCtx.getText().startsWith("~")) {
                processSustain(stepTicks);
                if (tailPlayableCtx.gridTailPlayable() != null) {
                    processGridSequence(tailPlayableCtx.gridTailPlayable(), stepTicks, activeNotes);
                }
            } else {
                processRest(stepTicks, activeNotes);
                if (tailPlayableCtx.gridTailNoHold() != null) {
                    processGridSequence(tailPlayableCtx.gridTailNoHold(), stepTicks, activeNotes);
                }
            }
            return;
        }

        if (node instanceof SymNoteParser.GridTailNoHoldContext) {
            SymNoteParser.GridTailNoHoldContext tailNoHoldCtx = (SymNoteParser.GridTailNoHoldContext) node;
            if (tailNoHoldCtx.gridPlayable() != null) {
                processPlayable(tailNoHoldCtx.gridPlayable(), stepTicks, activeNotes);
                if (tailNoHoldCtx.gridTailPlayable() != null) {
                    processGridSequence(tailNoHoldCtx.gridTailPlayable(), stepTicks, activeNotes);
                }
            } else {
                processRest(stepTicks, activeNotes);
                if (tailNoHoldCtx.gridTailNoHold() != null) {
                    processGridSequence(tailNoHoldCtx.gridTailNoHold(), stepTicks, activeNotes);
                }
            }
        }
    }

    private void processPlayable(SymNoteParser.GridPlayableContext playableCtx, long stepTicks,
            List<PendingNote> activeNotes) {
        closeActiveNotes(activeNotes, interpreter.currentTick);

        int velocity = resolveVelocity(playableCtx);
        String synth = interpreter.currentSynthName == null ? "piano" : interpreter.currentSynthName;

        if (playableCtx.noteElement() != null) {
            int midiPitch = resolveMidiPitch(playableCtx.noteElement(), playableCtx.getStart().getLine());
            activeNotes.add(new PendingNote(interpreter.currentTick, midiPitch, velocity, synth));
        } else if (playableCtx.gridChord() != null) {
            for (SymNoteParser.NoteElementContext noteCtx : playableCtx.gridChord().noteElement()) {
                int midiPitch = resolveMidiPitch(noteCtx, noteCtx.getStart().getLine());
                activeNotes.add(new PendingNote(interpreter.currentTick, midiPitch, velocity, synth));
            }
        }

        interpreter.currentTick += stepTicks;
    }

    private void processSustain(long stepTicks) {
        interpreter.currentTick += stepTicks;
    }

    private void processRest(long stepTicks, List<PendingNote> activeNotes) {
        closeActiveNotes(activeNotes, interpreter.currentTick);
        interpreter.currentTick += stepTicks;
    }

    private void closeActiveNotes(List<PendingNote> activeNotes, long endTick) {
        for (PendingNote pendingNote : activeNotes) {
            long duration = Math.max(1, endTick - pendingNote.startTick);
            interpreter.getTimeline().addEvent(
                    new MusicalEvent(pendingNote.startTick, pendingNote.pitch, pendingNote.velocity, duration,
                            pendingNote.synthName));
        }
        activeNotes.clear();
    }

    private int resolveVelocity(SymNoteParser.GridPlayableContext playableCtx) {
        int velocity = 100;
        if (playableCtx.gridVolModifier() == null) {
            return velocity;
        }

        SymNoteParser.GridVolValueContext volValueCtx = playableCtx.gridVolModifier().gridVolValue();
        if (volValueCtx.ID() != null) {
            String variableName = volValueCtx.ID().getText();
            interpreter.validateVariableDeclared(variableName, volValueCtx.getStart().getLine());
            Object value = interpreter.env.get(variableName).value;
            return normalizeVelocity(value, volValueCtx.getStart().getLine());
        }

        if (volValueCtx.FLOAT() != null) {
            return normalizeVelocity(Float.parseFloat(volValueCtx.FLOAT().getText()), volValueCtx.getStart().getLine());
        }

        return normalizeVelocity(Integer.parseInt(volValueCtx.INT().getText()), volValueCtx.getStart().getLine());
    }

    private int normalizeVelocity(Object value, int line) {
        if (value instanceof Integer) {
            return clampVelocity((Integer) value);
        }

        if (value instanceof Float) {
            float floatValue = (Float) value;
            if (floatValue >= 0.0f && floatValue <= 1.0f) {
                return clampVelocity(Math.round(floatValue * 127.0f));
            }
            return clampVelocity(Math.round(floatValue));
        }

        if (value instanceof String) {
            String text = (String) value;
            if (text.contains(".")) {
                float parsed = Float.parseFloat(text);
                if (parsed >= 0.0f && parsed <= 1.0f) {
                    return clampVelocity(Math.round(parsed * 127.0f));
                }
                return clampVelocity(Math.round(parsed));
            }
            return clampVelocity(Integer.parseInt(text));
        }

        throw new RuntimeException("Invalid velocity value at line " + line);
    }

    private int clampVelocity(int velocity) {
        return Math.max(0, Math.min(127, velocity));
    }

    private int resolveMidiPitch(SymNoteParser.NoteElementContext noteCtx, int line) {
        String noteText;
        if (noteCtx.NOTE() != null) {
            noteText = noteCtx.NOTE().getText();
        } else {
            String variableName = noteCtx.ID().getText();
            interpreter.validateVariableDeclared(variableName, line);
            Object value = interpreter.env.get(variableName).value;
            noteText = String.valueOf(value);
        }

        return noteToMidi(noteText, line);
    }

    private int noteToMidi(String noteText, int line) {
        if (noteText == null || noteText.length() < 2) {
            throw new RuntimeException("Invalid note '" + noteText + "' at line " + line);
        }

        String normalized = noteText.trim();
        char noteChar = Character.toUpperCase(normalized.charAt(0));
        int noteIndex;

        switch (noteChar) {
            case 'C':
                noteIndex = 0;
                break;
            case 'D':
                noteIndex = 2;
                break;
            case 'E':
                noteIndex = 4;
                break;
            case 'F':
                noteIndex = 5;
                break;
            case 'G':
                noteIndex = 7;
                break;
            case 'A':
                noteIndex = 9;
                break;
            case 'B':
                noteIndex = 11;
                break;
            default:
                throw new RuntimeException("Invalid note letter in '" + noteText + "' at line " + line);
        }

        int offset = 0;
        int octaveStart = 1;
        if (normalized.length() > 2) {
            char accidental = normalized.charAt(1);
            if (accidental == '#') {
                offset = 1;
                octaveStart = 2;
            } else if (accidental == 'b' || accidental == 'B') {
                offset = -1;
                octaveStart = 2;
            }
        }

        int octave;
        try {
            octave = Integer.parseInt(normalized.substring(octaveStart));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid octave in note '" + noteText + "' at line " + line);
        }

        int midiPitch = (octave + 1) * 12 + noteIndex + offset;
        if (midiPitch < 0 || midiPitch > 127) {
            throw new RuntimeException("MIDI pitch out of range for note '" + noteText + "' at line " + line);
        }
        return midiPitch;
    }
}