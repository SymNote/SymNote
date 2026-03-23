package audio;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiUnavailableException;

public class AudioEngine {
    private javax.sound.midi.Synthesizer midiSynth;
    private MidiChannel[] channels;
    private int nextChannel = 0;

    public AudioEngine() {
        try {
            midiSynth = MidiSystem.getSynthesizer();
            midiSynth.open();
            channels = midiSynth.getChannels();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
            System.err.println("Could not initialize MIDI Synthesizer.");
        }
    }

    public Synthesizer createSynthesizer(String name) {
        if (channels == null || channels.length == 0) return null;

        if (nextChannel >= channels.length) {
            nextChannel = 0;
        }
        
        // Skip channel 9 (0-indexed) because it is reserved for percussion in General MIDI
        if (nextChannel == 9) {
            nextChannel++;
        }

        int program = 0;
        switch (name.toLowerCase()) {
            case "piano": program = 0; break;
            case "bass": program = 32; break;
            case "sawtooth": program = 81; break;
            case "square": program = 80; break;
            case "guitar": program = 24; break;
            case "strings": program = 48; break;
            default:
                try {
                    program = Integer.parseInt(name);
                } catch (NumberFormatException e) {
                    System.err.println("Unknown synth name: " + name + ", defaulting to Piano");
                }
        }

        MidiChannel chan = channels[nextChannel++];
        return new MidiSynthesizer(chan, program, name);
    }
}
