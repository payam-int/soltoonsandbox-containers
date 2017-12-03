package ir.pint.soltoon.utils.clients.proxy;

import java.lang.reflect.Method;
import java.util.concurrent.Semaphore;

public class MethodInvoke {
    private Object proxy;
    private Method method;
    private Object[] args;
    private long timeout = -1;
    private Semaphore lock;
    private ProxyReturnStorage proxyReturnStorage;

    public MethodInvoke(Object proxy, Method method, Object[] args, ProxyReturnStorage proxyReturnStorage, long timeout, Semaphore lock) {
        this.proxy = proxy;
        this.method = method;
        this.args = args;
        this.timeout = timeout;
        this.lock = lock;
        this.proxyReturnStorage = proxyReturnStorage;
    }

    public Object getProxy() {
        return proxy;
    }

    public void setProxy(Object proxy) {
        this.proxy = proxy;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public Semaphore getLock() {
        return lock;
    }

    public void setLock(Semaphore lock) {
        this.lock = lock;
    }

    public ProxyReturnStorage getProxyReturnStorage() {
        return proxyReturnStorage;
    }

    public void setProxyReturnStorage(ProxyReturnStorage proxyReturnStorage) {
        this.proxyReturnStorage = proxyReturnStorage;
    }
}