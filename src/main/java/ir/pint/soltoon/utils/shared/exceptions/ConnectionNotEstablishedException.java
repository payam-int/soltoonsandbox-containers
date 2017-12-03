package ir.pint.soltoon.utils.shared.exceptions;

public class ConnectionNotEstablishedException extends SoltoonContainerException {
    public ConnectionNotEstablishedException() {
        super("Connection not established.");
    }
}
