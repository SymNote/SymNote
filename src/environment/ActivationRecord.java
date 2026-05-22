package environment;

public class ActivationRecord {
    private final String routineName;
    private Environment environment;

    public ActivationRecord(String routineName, Environment environment) {
        this.routineName = routineName;
        this.environment = environment;
    }

    public String getRoutineName() {
        return routineName;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}