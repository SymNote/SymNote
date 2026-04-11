package midi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MidiScheduler {
    private static final double STARTUP_PREROLL_BEATS = 1.0;

    private final SymNoteTimeline timeline;
    private final float bpm;
    private final Map<String, Integer> channelBySynth = new HashMap<>();
    private final Map<String, Integer> programBySynth = new HashMap<>();
    private long lastComputedTick = 0L;

    public MidiScheduler(SymNoteTimeline timeline, float bpm) {
        this.timeline = timeline;
        this.bpm = bpm;
    }

    public List<MidiAction> scheduleAll() {
        List<MusicalEvent> events = timeline.getSortedEvents();
        List<MidiAction> actions = new ArrayList<>();

        long preRollTicks = Math.max(1L, Math.round(timeline.getTicksPerBeat() * STARTUP_PREROLL_BEATS));

        System.out.println("[MIDI] BPM=" + bpm + ", events=" + events.size() + ", preRollTicks=" + preRollTicks);

        for (MusicalEvent event : events) {
            String synthName = event.getSynthName() == null ? "piano" : event.getSynthName();
            int channel = channelBySynth.computeIfAbsent(synthName, key -> allocateChannel(channelBySynth.size()));
            programBySynth.computeIfAbsent(synthName, this::programForSynth);

            long startTick = event.getAbsoluteTick() + preRollTicks;
            long endTick = Math.max(startTick + event.getDurationTicks(), startTick + 1L);
            int velocity = clampVelocity(event.getVelocity());

            actions.add(new MidiAction(
                    startTick, MidiAction.Type.NOTE_ON, channel, event.getPitch(),
                    velocity, synthName, event.getDurationTicks()));

            actions.add(new MidiAction(
                    endTick, MidiAction.Type.NOTE_OFF, channel, event.getPitch(),
                    0, synthName, event.getDurationTicks()));

            if (endTick > lastComputedTick) {
                lastComputedTick = endTick;
            }
        }

        // Sort actions by tick
        // If OFF action and ON action on the same tick process OFF action first
        actions.sort((a, b) -> {
            if (a.tick != b.tick) {
                return Long.compare(a.tick, b.tick);
            }
            return a.actionType.compareTo(b.actionType);
        });

        return actions;
    }

    public Map<String, Integer> getChannelBySynth() {
        return channelBySynth;
    }

    public Map<String, Integer> getProgramBySynth() {
        return programBySynth;
    }

    public long getLastComputedTick() {
        return lastComputedTick;
    }

    public double getEstimatedTotalSeconds() {
        double beats = (double) (lastComputedTick + 1L) / Math.max(1, timeline.getTicksPerBeat());
        return beats * 60.0 / Math.max(1, bpm);
    }

    private int allocateChannel(int synthIndex) {
        int[] channels = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, 12, 13, 14, 15 };
        return channels[synthIndex % channels.length];
    }

    private int clampVelocity(int velocity) {
        return Math.max(0, Math.min(127, velocity));
    }

    private int programForSynth(String synthName) {
        String key = synthName == null ? "piano"
                : synthName.trim().toLowerCase().replace("-", "").replace("_", "").replace(" ", "");
        switch (key) {
            case "piano":
                return 0;
            case "organ":
                return 18;
            case "bass":
                return 32;
            case "sawtooth":
                return 81;
            case "square":
                return 80;
            case "guitar":
                return 24;
            case "strings":
                return 48;
            case "pad":
                return 89;
            case "choir":
                return 52;
            case "trumpet":
                return 56;
            case "sax":
                return 65;
            case "flute":
                return 73;
            case "bell":
                return 14;
            case "pluck":
                return 45;
            default:
                try {
                    return Integer.parseInt(synthName);
                } catch (NumberFormatException ignored) {
                    return 0;
                }
        }
    }
}
