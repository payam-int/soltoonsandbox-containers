package ir.pint.soltoon.utils.clients.ai;

import ir.pint.soltoon.utils.clients.proxy.ProxyReturnStorage;
import ir.pint.soltoon.utils.clients.proxy.TimeLimitedBean;

public abstract class Game implements GameInterface, TimeLimitedBean {
    private ProxyReturnStorage proxyReturnStorage;

    public void restart() {
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
}
