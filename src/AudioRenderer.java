import midi.SymNoteTimeline;

public interface AudioRenderer {
    void init(int bpm);

    void render(SymNoteTimeline timeline);

    void shutdown();
}
