import java.util.List;
import midi.MidiAction;
import midi.MidiPlaybackEngine;
import midi.MidiScheduler;
import midi.SymNoteTimeline;

public class JavaMidiRenderer implements AudioRenderer {
    private float bpm = 120;
    private MidiPlaybackEngine engine;

    @Override
    public void init(float bpm) {
        this.bpm = bpm;
    }

    @Override
    public void render(SymNoteTimeline timeline) {
        if (timeline.isEmpty()) {
            System.out.println("[MIDI] No events to play.");
            return;
        }

        try {
            MidiScheduler scheduler = new MidiScheduler(timeline, bpm);
            List<MidiAction> actions = scheduler.scheduleAll();

            engine = new MidiPlaybackEngine();
            engine.initAndWarmUp(scheduler.getChannelBySynth(), scheduler.getProgramBySynth());

            System.out.println("[MIDI] Playing " + timeline.getSortedEvents().size() + " events.");
            engine.play(actions, bpm, timeline.getTicksPerBeat());

        } catch (Exception e) {
            throw new RuntimeException("MIDI render failed: " + e.getMessage(), e);
        }
    }

    @Override
    public void shutdown() {
        if (engine != null) {
            engine.shutdown();
        }
    }
}
