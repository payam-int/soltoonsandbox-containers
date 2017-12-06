package ir.pint.soltoon.soltoongame.shared.communication;

import java.io.Serializable;

/**
 * Created by amirkasra on 9/28/2017 AD.
 */
public abstract class Message implements Serializable {
    private long request;

    public Message(long request) {
        this.request = request;
    }

    public long getRequest() {
        return request;
    }

    public void setRequest(long request) {
        this.request = request;
    }
}
