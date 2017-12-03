package ir.pint.soltoon.soltoongame.shared.communication.command;

import ir.pint.soltoon.soltoongame.shared.communication.Message;

/**
 * Created by amirkasra on 9/28/2017 AD.
 */
public abstract class Command extends Message {
    public Command(Long id) {
        super(id);
    }
}
