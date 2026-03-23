package audio;

public interface Synthesizer {
    void noteOn(int midiNote, int velocity);
    void noteOff(int midiNote);
    String getName();
}
