package environment;

import java.util.HashMap;
import java.util.Map;
import org.antlr.v4.runtime.tree.ParseTree;

public class Environment {
    private final Map<String, Object> variables = new HashMap<>();
    private final Map<String, ParseTree> trackDeclarations = new HashMap<>();
    private Environment parent;

    public Environment() {
        this.parent = null;
    }
    
    public Environment(Environment parent) {
        this.parent = parent;
    }

    public void define(String name, Object value) {
        variables.put(name, value);
    }

    public void assign(String name, Object value) {
        if (variables.containsKey(name)) {
            variables.put(name, value);
            return;
        }
        if (parent != null) {
            parent.assign(name, value);
            return;
        }
        throw new RuntimeException("Undefined variable '" + name + "'.");
    }

    public Object get(String name) {
        if (variables.containsKey(name)) {
            return variables.get(name);
        }
        if (parent != null) {
            return parent.get(name);
        }
        throw new RuntimeException("Undefined variable '" + name + "'.");
    }

    public void defineTrack(String name, ParseTree ctx) {
        trackDeclarations.put(name, ctx);
    }

    public ParseTree getTrack(String name) {
        if (trackDeclarations.containsKey(name)) {
            return trackDeclarations.get(name);
        }
        if (parent != null) {
            return parent.getTrack(name);
        }
        throw new RuntimeException("Undefined track '" + name + "'.");
    }
}
