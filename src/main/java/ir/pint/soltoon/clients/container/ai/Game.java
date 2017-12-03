package ir.pint.soltoon.clients.container.ai;

import ir.pint.soltoon.clients.container.GameInterface;
import ir.pint.soltoon.clients.container.proxy.ProxyReturnStorage;
import ir.pint.soltoon.clients.container.proxy.TimeLimitedBean;

public abstract class Game implements GameInterface, TimeLimitedBean {
    private ProxyReturnStorage proxyReturnStorage;

    public void restart(TimeLimitedBean previusObject) {
        if (previusObject instanceof Game)
            restart(((Game) previusObject));
    }

    public abstract void restart(Game game);

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
