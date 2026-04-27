public class StdoutCollector implements OutputCollector {
    @Override
    public void println(Object value) {
        System.out.println(value);
    }
}
