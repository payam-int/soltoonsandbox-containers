package ir.pint.soltoon.utils.clients.proxy;


public interface TimeAwareBean {
    int LEGAL_TIME = 0, EXTRA_TIME = 1;

    void repair();

    void setProxyReturnStorage(ProxyReturnStorage proxyReturnStorage);

    ProxyReturnStorage getProxyReturnStorage();

    int getRemainingTime();
    void setRemainingTime(int remainingTime);

    int getDurationType();

    void setDurationType(int durationType);

    default void returnTemporary(Object returnObject) {
        getProxyReturnStorage().setInvokeReturn(returnObject);
    }
}
