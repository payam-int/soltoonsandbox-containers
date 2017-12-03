package ir.pint.soltoon.clients.container.proxy;

public interface TimeLimitedBean {
    void restart(TimeLimitedBean previusObject);

    void setProxyReturnStorage(ProxyReturnStorage proxyReturnStorage);

    void returnTemporary(Object returnObject);

    Object getReturn();
}
