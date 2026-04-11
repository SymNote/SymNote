package midi;

public class MidiAction {
    public enum Type {
        NOTE_OFF,
        NOTE_ON
    }

    public final long tick;
    public final Type actionType;
    public final int channel;
    public final int pitch;
    public final int velocity;
    public final String synthName;
    public final long durationTicks;

    public MidiAction(long tick, Type actionType, int channel, int pitch, int velocity, String synthName,
            long durationTicks) {
        this.tick = tick;
        this.actionType = actionType;
        this.channel = channel;
        this.pitch = pitch;
        this.velocity = velocity;
        this.synthName = synthName;
        this.durationTicks = durationTicks;
    }
}
