package ir.pint.soltoon.utils.clients.proxy;

public abstract class DefaultTimeAwareBean implements TimeAwareProxyInterface, TimeAwareBean {
    public static final int TIME_ENDED = -2;
    public static final int UNLIMITED_TIME = -1;
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
                return TIME_ENDED;

            long cur = System.currentTimeMillis();
            if (cur > lifetime)
                return UNLIMITED_TIME;
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
