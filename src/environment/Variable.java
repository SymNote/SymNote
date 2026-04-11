package environment;


public class Variable {
    public final String type;
    public Object value;

    public Variable(String type, Object value) {
        this.type = type;
        this.value = value;
    }
}