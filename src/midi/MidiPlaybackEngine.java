package midi;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

public class MidiPlaybackEngine {
    private static final long SYNTH_WARMUP_MS = 600L;
    private static final long STARTUP_BUFFER_MS = 500L;

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

        warmUpSynth(channelBySynth);

        if (STARTUP_BUFFER_MS > 0) {
            Thread.sleep(STARTUP_BUFFER_MS);
        }
    }

    private void warmUpSynth(Map<String, Integer> channelBySynth) throws Exception {
        Set<Integer> channels = new HashSet<>(channelBySynth.values());
        if (channels.isEmpty()) {
            channels.add(0);
        }

        for (int channel : channels) {
            receiver.send(shortMessage(ShortMessage.NOTE_ON, channel, 60, 1), -1);
            Thread.sleep(15L);
            receiver.send(shortMessage(ShortMessage.NOTE_OFF, channel, 60, 0), -1);
        }
        Thread.sleep(SYNTH_WARMUP_MS);
    }

    public void play(List<MidiAction> actions, float bpm, int ticksPerBeat) throws Exception {
        long previousTick = 0L;
        long currentClockNs = System.nanoTime();

        for (MidiAction action : actions) {
            long deltaTicks = Math.max(0L, action.tick - previousTick);
            long waitNs = ticksToNanos(deltaTicks, ticksPerBeat, bpm);
            currentClockNs = sleepUntil(currentClockNs + waitNs);

            if (action.actionType == MidiAction.Type.NOTE_ON) {
                receiver.send(shortMessage(ShortMessage.NOTE_ON, action.channel, action.pitch, action.velocity), -1);
                System.out.println("[NOW PLAYING] tick=" + action.tick
                        + " beat=" + formatBeat(action.tick, ticksPerBeat)
                        + " synth='" + action.synthName + "'"
                        + " note=" + midiToNoteName(action.pitch)
                        + " velocity=" + action.velocity
                        + " durationTicks=" + action.durationTicks);
            } else {
                receiver.send(shortMessage(ShortMessage.NOTE_OFF, action.channel, action.pitch, 0), -1);
            }

            previousTick = action.tick;
            currentClockNs = System.nanoTime();
        }

        System.out.println("[MIDI] playback finished");
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

    private long sleepUntil(long targetNs) throws Exception {
        while (true) {
            long remainingNs = targetNs - System.nanoTime();
            if (remainingNs <= 0) {
                return System.nanoTime();
            }
            long sleepMs = remainingNs / 1_000_000L;
            int sleepNanos = (int) (remainingNs % 1_000_000L);
            Thread.sleep(sleepMs, sleepNanos);
        }
    }

    private long ticksToNanos(long ticks, int ticksPerBeat, float bpm) {
        if (ticks <= 0) {
            return 0L;
        }
        double nanosPerBeat = 60_000_000_000.0 / Math.max(1, bpm);
        double beats = (double) ticks / Math.max(1, ticksPerBeat);
        return Math.max(0L, Math.round(beats * nanosPerBeat));
    }

    private String formatBeat(long tick, int ticksPerBeat) {
        double beat = (double) tick / Math.max(1, ticksPerBeat);
        return String.format("%.3f", beat);
    }

    private String midiToNoteName(int midiPitch) {
        String[] names = { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };
        int clamped = Math.max(0, Math.min(127, midiPitch));
        int octave = (clamped / 12) - 1;
        String note = names[clamped % 12];
        return note + octave + "(" + clamped + ")";
    }
}
