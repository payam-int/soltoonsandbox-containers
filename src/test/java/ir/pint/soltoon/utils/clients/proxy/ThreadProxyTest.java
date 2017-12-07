package ir.pint.soltoon.utils.clients.proxy;

import ir.pint.soltoon.utils.clients.exceptions.ProxyTimeoutException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Semaphore;

public class ThreadProxyTest {
    interface BeanInterface extends TimeAwareProxyInterface {
        boolean calledFromAnotherThread(Thread thead);

        boolean sleep(long time, long l);

        void emptyCall();
    }

    static class Bean implements BeanInterface {
        private Semaphore lock;
        private ProxyReturnStorage proxyReturnStorage;

        public Bean(Semaphore lock) {
            this.lock = lock;
        }

        @Override
        public int getDurationType() {
            return 0;
        }

        @Override
        public void setRemainingTime(int remainingTime) {

        }

        @Override
        public void setDurationType(int durationType) {

        }

        @Override
        public void repair() {

        }

        @Override
        public void setProxyReturnStorage(ProxyReturnStorage proxyReturnStorage) {
            this.proxyReturnStorage = proxyReturnStorage;
        }

        @Override
        public void returnTemporary(Object returnObject) {
        }

        @Override
        public int getRemainingTime() {
            return 0;
        }

        @Override
        public boolean calledFromAnotherThread(Thread thead) {
            return thead.getId() != Thread.currentThread().getId();
        }

        @Override
        public boolean sleep(long time, long l) {
            returnTemporary(false);
            long s = System.currentTimeMillis();
            while (System.currentTimeMillis() - s < time)
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {

                }

            s = System.currentTimeMillis();

            while (System.currentTimeMillis() - s < l)
                try {
                    System.out.println("sleep -" + l);
                    Thread.sleep(l);
                } catch (InterruptedException e1) {

                }

            System.out.println("Thread not dead !");
            return true;
        }

        @Override
        public ProxyReturnStorage getProxyReturnStorage() {
            return proxyReturnStorage;
        }

        @Override
        public void emptyCall() {

        }
    }

    private BeanInterface bean;
    private BeanInterface proxiedBean;
    private Semaphore lock = new Semaphore(0);
    private ProxyTimeLimit proxyTimeLimit = new ProxyTimeLimit(0, 0);

    @Before
    public void setUp() throws Exception {
        bean = new Bean(lock);
        proxiedBean = TimeAwareBeanProxy.createBean(bean, BeanInterface.class, proxyTimeLimit);
    }

    @Test
    public void invokeInAnotherThread() throws Exception {
        Assert.assertFalse(bean.calledFromAnotherThread(Thread.currentThread()));
        Assert.assertTrue(proxiedBean.calledFromAnotherThread(Thread.currentThread()));
    }

    @Test(expected = ProxyTimeoutException.class)
    public void invokeWithTimeout() {
        proxyTimeLimit.setTimeoutPolicy(TimeoutPolicy.THROW_EXCEPTION);
        proxyTimeLimit.setTimeLimit(2000);
        proxyTimeLimit.setExtraTimeLimit(1000);
        proxiedBean.sleep(10000, 30000);
    }

    @Test(expected = ProxyTimeoutException.class)
    public void invokeWithTimeoutTest() {
        proxyTimeLimit.setTimeoutPolicy(TimeoutPolicy.THROW_EXCEPTION);
        proxyTimeLimit.setTimeLimit(1);
        proxyTimeLimit.setExtraTimeLimit(1);
        proxiedBean.sleep(10000, 10000);
    }

    @Test
    public void invokeWithoutTimeout() throws Exception {
        proxyTimeLimit.setTimeLimit(0);

        boolean sleep = proxiedBean.sleep(1000, 100);
        Assert.assertTrue(sleep);
    }
}