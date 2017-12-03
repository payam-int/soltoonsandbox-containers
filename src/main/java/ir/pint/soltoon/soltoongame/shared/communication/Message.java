package ir.pint.soltoon.soltoongame.shared.communication;

import java.io.Serializable;

/**
 * Created by amirkasra on 9/28/2017 AD.
 */
public abstract class Message implements Serializable {
    public Long id;
    
    public Message(Long id) {
        this.id = id;
    }
}
