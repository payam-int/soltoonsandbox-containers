package ir.pint.soltoon.utils.clients.proxy;

public abstract class DefaultTimeAwareBean implements TimeAwareProxyInterface, TimeAwareBean {
    private ProxyReturnStorage proxyReturnStorage;
    private long legaltime;
    private long extratime;

    public void repair() {
    }

    public void setProxyReturnStorage(ProxyReturnStorage proxyReturnStorage) {
        this.proxyReturnStorage = proxyReturnStorage;
    }

    public void returnTemporary(Object returnObject) {
        this.proxyReturnStorage.setInvokeReturn(returnObject);
    }

    public Object getReturn() {
        return this.proxyReturnStorage.getInvokeReturn();
    }

    @Override
    public ProxyReturnStorage getProxyReturnStorage() {
        return null;
    }

    @Override
    public int getRemainingTime() {
        long cur = System.currentTimeMillis();
        if (cur > legaltime && cur > extratime)
            return -1;
        else if (cur <= legaltime)
            return (int) (legaltime - cur);
        else if (cur <= extratime)
            return (int) (extratime - legaltime);
        else
            return -2;
    }
}
