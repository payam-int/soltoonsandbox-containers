package ir.pint.soltoon.utils.clients.proxy;

public interface TimeLimitedBean {
    void restart();

    void setProxyReturnStorage(ProxyReturnStorage proxyReturnStorage);

    void returnTemporary(Object returnObject);

    Object getReturn();
}
