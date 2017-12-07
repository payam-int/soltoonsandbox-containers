package ir.pint.soltoon.utils.clients.proxy;


// @todo refactor and comment
public interface TimeAwareBean {
    void repair();

    void setProxyReturnStorage(ProxyReturnStorage proxyReturnStorage);

    ProxyReturnStorage getProxyReturnStorage();

    int getRemainingTime();

    default void returnTemporary(Object returnObject) {
        getProxyReturnStorage().setInvokeReturn(returnObject);
    }
}
