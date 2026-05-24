public class Synth {
    private final String name;

    public Synth(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "synth(" + name + ")";
    }
}
