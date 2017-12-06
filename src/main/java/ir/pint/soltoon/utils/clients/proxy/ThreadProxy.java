package ir.pint.soltoon.utils.clients.proxy;

import ir.pint.soltoon.utils.clients.ai.Game;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

// @todo refactor
public class ThreadProxy implements InvocationHandler {
    private TimeLimitConfig timeLimitConfig;
    private Semaphore lock;
    private TimeLimitedBean bean;
    private ThreadProxyHandler threadProxyHandler;

    public ThreadProxy(TimeLimitedBean bean, TimeLimitConfig timeLimitConfig) {
        this.bean = bean;
        this.timeLimitConfig = timeLimitConfig;
        assignThreadProxyHandler();
    }

    public static <F extends TimeLimitedBean> F createBean(Class beanClass, Class iface, TimeLimitConfig timeLimitConfig) throws IllegalAccessException, InstantiationException {
        F bean = (F) beanClass.newInstance();

        F beanProxied = (F) Proxy.newProxyInstance(
                ThreadProxy.class.getClassLoader(),
                new Class[]{iface},
                new ThreadProxy(bean, timeLimitConfig)
        );

        return beanProxied;
    }

    private void assignThreadProxyHandler() {
        this.threadProxyHandler = new ThreadProxyHandler(bean);
        this.threadProxyHandler.start();
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        lock = new Semaphore(0);

        ProxyReturnStorage proxyReturnStorage = new ProxyReturnStorage();
        threadProxyHandler.invoke(new MethodInvoke(proxy, method, args, proxyReturnStorage, timeLimitConfig.getTimeLimit(), lock));

        lock.acquire();

        boolean acquired = false;


        acquired = lock.tryAcquire(timeLimitConfig.getTimeLimit(), TimeUnit.MILLISECONDS);

        if (!acquired) {
            threadProxyHandler.interrupt();
            acquired = lock.tryAcquire(timeLimitConfig.getExtraTimeLimit(), TimeUnit.MILLISECONDS);

            if (!acquired) {
                threadProxyHandler.stop();
                assignThreadProxyHandler();
            } else {
            }
        }

        if (proxyReturnStorage.getThrowableReturn() == null)
            return proxyReturnStorage.getInvokeReturn();
        else
            throw proxyReturnStorage.getThrowableReturn();
    }

}
