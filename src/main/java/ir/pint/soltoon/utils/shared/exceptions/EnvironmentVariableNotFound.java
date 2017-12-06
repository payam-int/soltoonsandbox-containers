package ir.pint.soltoon.utils.shared.exceptions;

public class EnvironmentVariableNotFound extends Exception {
    private String variableName;

    public EnvironmentVariableNotFound(String variableName) {
        this.variableName = variableName;
    }

    public EnvironmentVariableNotFound(String s, String variableName) {
        super(s);
        this.variableName = variableName;
    }

    public EnvironmentVariableNotFound(String s, Throwable throwable, String variableName) {
        super(s, throwable);
        this.variableName = variableName;
    }

    public EnvironmentVariableNotFound(Throwable throwable, String variableName) {
        super(throwable);
        this.variableName = variableName;
    }

    public EnvironmentVariableNotFound(String s, Throwable throwable, boolean b, boolean b1, String variableName) {
        super(s, throwable, b, b1);
        this.variableName = variableName;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }
}
