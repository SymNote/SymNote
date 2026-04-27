import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListOutputCollector implements OutputCollector {
    private final List<String> lines = new ArrayList<>();

    @Override
    public void println(Object value) {
        lines.add(value == null ? "null" : value.toString());
    }

    public List<String> getLines() {
        return Collections.unmodifiableList(lines);
    }
}
