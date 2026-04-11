package midi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SymNoteTimeline {
    private final int ticksPerBeat;
    private final List<MusicalEvent> events = new ArrayList<>();

    public SymNoteTimeline(int ticksPerBeat) {
        this.ticksPerBeat = ticksPerBeat;
    }

    public int getTicksPerBeat() {
        return ticksPerBeat;
    }

    public void addEvent(MusicalEvent event) {
        events.add(event);
    }

    public List<MusicalEvent> getSortedEvents() {
        List<MusicalEvent> sorted = new ArrayList<>(events);
        sorted.sort(Comparator.comparingLong(MusicalEvent::getAbsoluteTick));
        return sorted;
    }

    public boolean isEmpty() {
        return events.isEmpty();
    }
}
