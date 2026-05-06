package environment;

import java.util.HashMap;
import java.util.Map;
import org.antlr.v4.runtime.tree.ParseTree;

public class Environment {
    private final Map<String, Variable> variables = new HashMap<>();
    private final Map<String, ParseTree> trackDeclarations = new HashMap<>();
    private Environment parent;

    public Environment() {
        this.parent = null;
    }
    
    public Environment(Environment parent) {
        this.parent = parent;
    }

    public Environment getGlobal() {
        Environment current = this;
        while (current.parent != null) {
            current = current.parent;
        }
        return current;
    }

    public void define(String name, Variable variable) {
        variables.put(name, variable);
    }

    public void assign(String name, Object value) {
        if (variables.containsKey(name)) {
            variables.put(name, new Variable(variables.get(name).type, value));
            return;
        }
        if (parent != null) {
            parent.assign(name, value);
            return;
        }
        throw new RuntimeException("Undefined variable '" + name + "'.");
    }

    public Variable get(String name) {
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

    public boolean hasTrack(String name) {
        if (trackDeclarations.containsKey(name)) {
            return true;
        }
        if (parent != null) {
            return parent.hasTrack(name);
        }
        return false;
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
