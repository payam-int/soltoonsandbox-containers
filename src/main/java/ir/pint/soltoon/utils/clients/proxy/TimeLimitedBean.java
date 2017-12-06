package ir.pint.soltoon.utils.clients.proxy;


// @todo refactor and comment
public interface TimeLimitedBean {
    void restart();

    void setProxyReturnStorage(ProxyReturnStorage proxyReturnStorage);

    void returnTemporary(Object returnObject);

    Object getReturn();

    int getRemainingTime();
}
