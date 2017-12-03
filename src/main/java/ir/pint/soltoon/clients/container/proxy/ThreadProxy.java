package ir.pint.soltoon.clients.container.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadProxy extends Thread implements InvocationHandler {
    private MethodInvoke methodInvoke = null;
    private TimeLimitConfig timeLimitConfig = null;
    private Semaphore lock;
    private TimeLimitedBean bean;
    private ProxyReturnStorage proxyReturnStorage;

    public ThreadProxy(TimeLimitedBean bean) {
        super("Proxy Thread");

        setDaemon(true);

        this.bean = bean;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        lock = new Semaphore(0);
        methodInvoke = new MethodInvoke(proxy, method, args, timeLimitConfig.getTimeLimit(), lock);
        this.interrupt();
        proxyReturnStorage = new ProxyReturnStorage();
        lock.acquire();

        try {
            lock.tryAcquire(timeLimitConfig.getTimeLimit(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            // ignore
        }

        if (proxyReturnStorage.getThrowableReturn() == null)
            return proxyReturnStorage.getInvokeReturn();
        else
            return null;
    }

    public void invokeInThread(MethodInvoke methodInvoke) {
        methodInvoke.getLock().release();

        bean.setProxyReturnStorage(proxyReturnStorage);

        Object returnObject = null;

        try {
            returnObject = methodInvoke.getMethod().invoke(bean, methodInvoke.getArgs());
        } catch (Throwable e) {
            e.printStackTrace();
            proxyReturnStorage.setThrowableReturn(e);
        }

        if (returnObject != null) {
            proxyReturnStorage.setInvokeReturn(returnObject);
        }

        methodInvoke.getLock().release();
    }

    @Override
    public void run() {
        while (true) {
            if (methodInvoke != null) {
                MethodInvoke invocation = this.methodInvoke;
                this.methodInvoke = null;
                invokeInThread(invocation);
            }
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class MethodInvoke {
        private Object proxy;
        private Method method;
        private Object[] args;
        private long timeout = -1;
        private Semaphore lock;

        public MethodInvoke(Object proxy, Method method, Object[] args, long timeout, Semaphore lock) {
            this.proxy = proxy;
            this.method = method;
            this.args = args;
            this.timeout = timeout;
            this.lock = lock;
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
    }
}
