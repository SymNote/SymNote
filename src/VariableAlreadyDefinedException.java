public class VariableAlreadyDefinedException extends RuntimeException {
    public VariableAlreadyDefinedException(String variableName, int line) {
        super("Variable '" + variableName + "' is already defined in this scope at line " + line);
    }
}