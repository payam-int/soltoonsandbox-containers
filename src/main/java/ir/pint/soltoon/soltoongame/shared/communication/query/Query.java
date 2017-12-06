package ir.pint.soltoon.soltoongame.shared.communication.query;

import ir.pint.soltoon.soltoongame.shared.communication.Message;

import java.io.Serializable;

/**
 * Created by amirkasra on 9/28/2017 AD.
 */
public abstract class Query extends Message implements Serializable {
    private long target;

    public Query(long target) {
        super(target);
    }

    public long getTarget() {
        return target;
    }

    public void setTarget(long target) {
        this.target = target;
    }
}
