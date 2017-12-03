package ir.pint.soltoon.utils.clients.proxy;

public class ThreadProxyHandler extends Thread {
    private TimeLimitedBean bean;
    private MethodInvoke methodInvoke = null;
    private Object objectLock = new Object();

    public ThreadProxyHandler(TimeLimitedBean bean) {
        super("Proxy Thread");
        setDaemon(true);

        this.bean = bean;
    }

    public void invokeInThread(MethodInvoke methodInvoke) {
        methodInvoke.getLock().release();
        ProxyReturnStorage proxyReturnStorage = methodInvoke.getProxyReturnStorage();

        bean.setProxyReturnStorage(proxyReturnStorage);

        Object returnObject = null;

        try {
            returnObject = methodInvoke.getMethod().invoke(bean, methodInvoke.getArgs());
        } catch (Exception e) {
            // ignore
        }

        if (returnObject != null) {
            proxyReturnStorage.setInvokeReturn(returnObject);
        }

        methodInvoke.getLock().release();
    }

    @Override
    public void run() {
        while (true) {

            synchronized (objectLock) {
                if (methodInvoke != null) {
                    MethodInvoke invocation = this.methodInvoke;
                    this.methodInvoke = null;
                    invokeInThread(invocation);
                }
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

    public void invoke(MethodInvoke methodInvoke) {
        synchronized (objectLock) {
            this.methodInvoke = methodInvoke;
        }
        this.interrupt();
    }
}
