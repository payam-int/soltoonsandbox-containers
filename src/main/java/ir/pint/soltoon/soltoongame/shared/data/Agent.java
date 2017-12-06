package ir.pint.soltoon.soltoongame.shared.data;

import ir.pint.soltoon.soltoongame.shared.data.action.Action;
import ir.pint.soltoon.soltoongame.shared.data.map.GameBoard;
import ir.pint.soltoon.utils.shared.facades.json.Secure;

import java.io.Serializable;
import java.util.Random;

@Secure
public abstract class Agent implements Serializable{
    public final Long id;

    protected Agent() {
        this.id = Math.abs(new Random().nextLong());
    }

    protected Agent(Long id) {
        this.id = id;
    }

    public abstract void init();
    public abstract void lastThingsToDo(GameBoard gameBoard);
    public abstract Action getAction(GameBoard gb);
}
