package ir.pint.soltoon.utils.clients.proxy;

import ir.pint.soltoon.utils.clients.exceptions.ProxyTimeoutException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

// @todo write facade for clients
public class TimeAwareBeanProxy implements InvocationHandler {
    private ProxyTimeLimit timeLimitConfig;
    private Semaphore lock;
    private TimeAwareBean bean;
    private ThreadProxyHandler threadProxyHandler;

    public TimeAwareBeanProxy(TimeAwareBean bean, ProxyTimeLimit timeLimitConfig) {
        this.bean = bean;
        this.timeLimitConfig = timeLimitConfig;
        assignThreadProxyHandler();
    }

    public static <F extends TimeAwareBean> F createBean(F bean, Class iface, ProxyTimeLimit timeLimitConfig) throws IllegalAccessException, InstantiationException {

        F beanProxied = (F) Proxy.newProxyInstance(
                TimeAwareBeanProxy.class.getClassLoader(),
                new Class[]{iface},
                new TimeAwareBeanProxy(bean, timeLimitConfig)
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

        bean.setDurationType(TimeAwareBean.LEGAL_TIME);

        lock.acquire();


        boolean acquired = false;


        bean.setRemainingTime((int) timeLimitConfig.getTimeLimit());
        if (timeLimitConfig.getTimeLimit() < 1) {
            lock.acquire();
            acquired = true;
        } else {
            acquired = lock.tryAcquire(timeLimitConfig.getTimeLimit(), TimeUnit.MILLISECONDS);
        }

        boolean forcestop = false;

        if (!acquired) {
            bean.setDurationType(TimeAwareBean.EXTRA_TIME);
            bean.setRemainingTime((int) timeLimitConfig.getExtraTimeLimit());
            threadProxyHandler.interrupt();
            if (timeLimitConfig.getExtraTimeLimit() < 1) {
                lock.acquire();
                acquired = true;
            } else {
                acquired = lock.tryAcquire(timeLimitConfig.getExtraTimeLimit(), TimeUnit.MILLISECONDS);
            }
            if (!acquired) {
                threadProxyHandler.stop();
                assignThreadProxyHandler();
                forcestop = true;
            } else {
            }
        }

        // @todo type checking
        if (proxyReturnStorage.getInvokeReturn() != null)
            return proxyReturnStorage.getInvokeReturn();
        else if (proxyReturnStorage.getThrowableReturn() != null)
            throw proxyReturnStorage.getThrowableReturn();
        else if (forcestop && timeLimitConfig.getTimeoutPolicy() == TimeoutPolicy.THROW_EXCEPTION)
            throw new ProxyTimeoutException();

        return null;
    }

}
