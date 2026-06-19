package midi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.sound.midi.Instrument;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Patch;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

public class MidiPlaybackEngine {

    private static final long TAIL_SILENCE_MS = 2500L;

    private Synthesizer synthesizer;
    private Receiver receiver;

    public void initAndWarmUp(Map<String, Integer> channelBySynth, Map<String, Integer> programBySynth)
            throws Exception {
        synthesizer = MidiSystem.getSynthesizer();
        synthesizer.open();
        receiver = synthesizer.getReceiver();

        for (Map.Entry<String, Integer> entry : channelBySynth.entrySet()) {
            String synthName = entry.getKey();
            int channel = entry.getValue();
            int program = programBySynth.getOrDefault(synthName, 0);
            receiver.send(shortMessage(ShortMessage.PROGRAM_CHANGE, channel, program, 0), -1);
            System.out.println("[MIDI] synth='" + synthName + "' on channel=" + channel + ", program=" + program);
        }

        warmUpSynth(channelBySynth, programBySynth);
    }

    private void warmUpSynth(Map<String, Integer> channelBySynth, Map<String, Integer> programBySynth)
            throws Exception {
        Soundbank sb = synthesizer.getDefaultSoundbank();
        if (sb != null) {
            Set<Integer> programs = new HashSet<>(programBySynth.values());
            if (programs.isEmpty()) programs.add(0);
            for (int program : programs) {
                Instrument instr = sb.getInstrument(new Patch(0, program));
                if (instr != null) {
                    synthesizer.loadInstrument(instr);
                    System.out.println("[MIDI] Loaded instrument program=" + program + " (" + instr.getName() + ")");
                } else {
                    System.out.println("[MIDI] Instrument not found for program=" + program);
                }
            }
        } else {
            System.out.println("[MIDI] No default soundbank — instrument preload skipped.");
        }

        Set<Integer> channels = new HashSet<>(channelBySynth.values());
        if (channels.isEmpty()) channels.add(0);
        for (int ch : channels) {
            receiver.send(shortMessage(ShortMessage.NOTE_ON, ch, 60, 1), -1);
        }

        System.out.print("[MIDI] Waiting for audio pipeline to start");
        long posA = synthesizer.getMicrosecondPosition();
        boolean audioStarted = false;
        for (int i = 0; i < 150; i++) { // 3 s max
            Thread.sleep(20);
            long posB = synthesizer.getMicrosecondPosition();
            if (posB > posA) {
                audioStarted = true;
                System.out.println(" ready (" + (i + 1) * 20 + "ms)");
                break;
            }
            if (i % 5 == 4) System.out.print(".");
        }
        if (!audioStarted) {
            System.out.println(" timeout — proceeding anyway");
        }

        long latencyMs = Math.max(100L, synthesizer.getLatency() / 1000L);
        long settleMs = Math.max(1000L, latencyMs * 3);
        System.out.println("[MIDI] Buffer latency=" + latencyMs + "ms, letting pipeline settle for " + settleMs + "ms...");
        Thread.sleep(settleMs);

        for (int ch : channels) {
            receiver.send(shortMessage(ShortMessage.NOTE_OFF, ch, 60, 0), -1);
        }
        Thread.sleep(60L);
    }

    public void play(List<MidiAction> actions, float bpm, int ticksPerBeat) throws Exception {
        if (actions.isEmpty()) {
            System.out.println("[MIDI] No actions to play.");
            return;
        }

        long startUs = synthesizer.getMicrosecondPosition();
        long lastOffsetUs = 0L;

        List<long[]>     displayOffsetMs  = new ArrayList<>();
        List<MidiAction> displayActions   = new ArrayList<>();

        for (MidiAction action : actions) {
            long offsetUs   = ticksToMicros(action.tick, ticksPerBeat, bpm);
            long timestampUs = startUs + offsetUs;

            if (action.actionType == MidiAction.Type.NOTE_ON) {
                receiver.send(
                        shortMessage(ShortMessage.NOTE_ON, action.channel, action.pitch, action.velocity),
                        timestampUs);
                displayOffsetMs.add(new long[]{ offsetUs / 1000L });
                displayActions.add(action);
            } else {
                receiver.send(
                        shortMessage(ShortMessage.NOTE_OFF, action.channel, action.pitch, 0),
                        timestampUs);
            }

            if (offsetUs > lastOffsetUs) lastOffsetUs = offsetUs;
        }

        long totalMs = lastOffsetUs / 1000L;
        System.out.println("[MIDI] Playing " + displayActions.size() + " notes over ~" + totalMs + "ms");

        long displayStartNs = System.nanoTime();
        Thread displayThread = new Thread(() -> {
            for (int i = 0; i < displayActions.size(); i++) {
                long targetMs = displayOffsetMs.get(i)[0];
                long elapsedMs = (System.nanoTime() - displayStartNs) / 1_000_000L;
                long sleepMs = targetMs - elapsedMs;
                if (sleepMs > 0) {
                    try { Thread.sleep(sleepMs); }
                    catch (InterruptedException e) { return; }
                }
                MidiAction a = displayActions.get(i);
                System.out.println("[NOW PLAYING] tick=" + a.tick
                        + " beat=" + formatBeat(a.tick, ticksPerBeat)
                        + " synth='" + a.synthName + "'"
                        + " note=" + midiToNoteName(a.pitch)
                        + " velocity=" + a.velocity
                        + " durationTicks=" + a.durationTicks);
            }
        });
        displayThread.setDaemon(true);
        displayThread.setName("symnote-display");
        displayThread.start();

        Thread.sleep(totalMs + TAIL_SILENCE_MS);
        displayThread.interrupt();
        System.out.println("[MIDI] Playback finished.");
    }

    public void shutdown() {
        if (receiver != null) {
            receiver.close();
            receiver = null;
        }
        if (synthesizer != null && synthesizer.isOpen()) {
            synthesizer.close();
            synthesizer = null;
        }
    }

    private ShortMessage shortMessage(int command, int channel, int data1, int data2) throws Exception {
        ShortMessage message = new ShortMessage();
        message.setMessage(command, channel, data1, data2);
        return message;
    }

    private long ticksToMicros(long ticks, int ticksPerBeat, float bpm) {
        if (ticks <= 0) return 0L;
        double microsPerBeat = 60_000_000.0 / Math.max(1, bpm);
        double beats = (double) ticks / Math.max(1, ticksPerBeat);
        return Math.max(0L, Math.round(beats * microsPerBeat));
    }

    private String formatBeat(long tick, int ticksPerBeat) {
        double beat = (double) tick / Math.max(1, ticksPerBeat);
        return String.format("%.3f", beat);
    }

    private String midiToNoteName(int midiPitch) {
        String[] names = { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };
        int clamped = Math.max(0, Math.min(127, midiPitch));
        int octave = (clamped / 12) - 1;
        return names[clamped % 12] + octave + "(" + clamped + ")";
    }
}
