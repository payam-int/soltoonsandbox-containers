package ir.pint.soltoon.utils.clients.proxy;

public final class ProxyReturnStorage {
    private Object invokeReturn;
    private Throwable throwableReturn;

    public ProxyReturnStorage() {
    }

    public Object getInvokeReturn() {
        return invokeReturn;
    }

    public void setInvokeReturn(Object invokeReturn) {
        this.invokeReturn = invokeReturn;
    }

    public Throwable getThrowableReturn() {
        return throwableReturn;
    }

    public void setThrowableReturn(Throwable throwableReturn) {
        this.throwableReturn = throwableReturn;
    }
}
