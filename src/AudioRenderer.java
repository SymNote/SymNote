import midi.SymNoteTimeline;

public interface AudioRenderer {
    void init(float bpm);

    void render(SymNoteTimeline timeline);

    void shutdown();
}
