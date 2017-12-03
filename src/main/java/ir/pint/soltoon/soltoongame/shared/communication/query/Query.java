package ir.pint.soltoon.soltoongame.shared.communication.query;

import ir.pint.soltoon.soltoongame.shared.communication.Message;

import java.io.Serializable;

/**
 * Created by amirkasra on 9/28/2017 AD.
 */
public abstract class Query extends Message implements Serializable{
    public Query(Long id) {
        super(id);
    }
}
