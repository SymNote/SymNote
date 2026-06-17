package environment;

import java.util.HashMap;
import java.util.Map;
import org.antlr.v4.runtime.tree.ParseTree;

public class Environment {
    private final Map<String, Variable> variables = new HashMap<>();
    private final Map<String, ParseTree> trackDeclarations = new HashMap<>();
    private final Environment parent;

    public Environment() {
        this.parent = null;
    }
    
    public Environment(Environment parent) {
        this.parent = parent;
    }

    public Environment getParent() {
        return this.parent;
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
        throw new RuntimeException("Cannot assign to undeclared variable '" + name + "'.");
    }

    public Variable get(String name) {
        if (variables.containsKey(name)) {
            return variables.get(name);
        }
        if (parent != null) {
            return parent.get(name);
        }
        throw new RuntimeException("Variable '" + name + "' is not defined in this scope.");
    }

    /** Returns the names of all variables defined directly in this frame (not parent frames). */
    public java.util.Set<String> variableNames() {
        return variables.keySet();
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

    // Resolves a variable from a parent scope at a specific shadowing depth.
   
    public Variable getParentVariable(String name, int depth) {
        Environment current = this;
        for (int i = 0; i < depth; i++) {
            if (current.parent == null) {
                throw new RuntimeException("No parent scope available at depth " + depth + ".");
            }
            current = current.parent;
        }
        return current.get(name);
    }

    //Assigns a value to a variable in a parent scope at a specific shadowing depth

    public void assignParentVariable(String name, Object value, int depth) {
        Environment current = this;
        for (int i = 0; i < depth; i++) {
            if (current.parent == null) {
                throw new RuntimeException("No parent scope available at depth " + depth + ".");
            }
            current = current.parent;
        }
        current.assign(name, value);
    }

    public boolean isDefined(String name) {
        if (variables.containsKey(name)) {
            return true;
        }
        if (parent != null) {
            return parent.isDefined(name);
        }
        return false;
    }
}
