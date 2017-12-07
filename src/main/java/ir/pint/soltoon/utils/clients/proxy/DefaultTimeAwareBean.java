package ir.pint.soltoon.utils.clients.proxy;

public abstract class DefaultTimeAwareBean implements TimeAwareProxyInterface, TimeAwareBean {
    private ProxyReturnStorage proxyReturnStorage;
    private long lifetime;
    private int durationType;

    private Object durationTypeLock = new Object();

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
        synchronized (durationTypeLock) {
            if (lifetime == -2)
                return -2;

            long cur = System.currentTimeMillis();
            if (cur > lifetime)
                return -1;
            else
                return (int) (lifetime - cur);
        }
    }

    @Override
    public void setRemainingTime(int remainingTime) {
        synchronized (durationTypeLock) {
            if (remainingTime == 0)
                lifetime = -2;
            else
                lifetime = System.currentTimeMillis() + remainingTime;
        }
    }

    @Override
    public int getDurationType() {
        synchronized (durationTypeLock) {
            return durationType;
        }
    }

    public void setDurationType(int durationType) {
        synchronized (durationTypeLock) {
            this.durationType = durationType;
        }
    }

}
