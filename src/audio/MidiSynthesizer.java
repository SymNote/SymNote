package audio;

import javax.sound.midi.MidiChannel;

public class MidiSynthesizer implements Synthesizer {
    private final MidiChannel channel;
    private final String name;

    public MidiSynthesizer(MidiChannel channel, int program, String name) {
        this.channel = channel;
        this.name = name;
        this.channel.programChange(program);
    }

    @Override
    public void noteOn(int midiNote, int velocity) {
        channel.noteOn(midiNote, velocity);
    }

    @Override
    public void noteOff(int midiNote) {
        channel.noteOff(midiNote);
    }
    
    @Override
    public String getName() {
        return name;
    }
}
