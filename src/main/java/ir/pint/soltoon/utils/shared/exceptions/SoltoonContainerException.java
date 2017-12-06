package ir.pint.soltoon.utils.shared.exceptions;

// @todo add exceptions
public class SoltoonContainerException extends Exception {
    public SoltoonContainerException() {
    }

    public SoltoonContainerException(String s) {
        super(s);
    }

    public SoltoonContainerException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public SoltoonContainerException(Throwable throwable) {
        super(throwable);
    }

    public SoltoonContainerException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
