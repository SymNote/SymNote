package midi;

public class MusicalEvent {
    private final long absoluteTick;
    private final int pitch;
    private final int velocity;
    private final long durationTicks;
    private final String synthName;

    public MusicalEvent(long absoluteTick, int pitch, int velocity, long durationTicks, String synthName) {
        this.absoluteTick = absoluteTick;
        this.pitch = pitch;
        this.velocity = velocity;
        this.durationTicks = durationTicks;
        this.synthName = synthName;
    }

    public long getAbsoluteTick() {
        return absoluteTick;
    }

    public int getPitch() {
        return pitch;
    }

    public int getVelocity() {
        return velocity;
    }

    public long getDurationTicks() {
        return durationTicks;
    }

    public String getSynthName() {
        return synthName;
    }
}
