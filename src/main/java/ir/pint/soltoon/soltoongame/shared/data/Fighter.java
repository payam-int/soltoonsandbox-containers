package ir.pint.soltoon.soltoongame.shared.data;

import ir.pint.soltoon.soltoongame.shared.data.map.GameObjectType;

/**
 * Created by amirkasra on 9/30/2017 AD.
 */
public abstract class Fighter extends Agent {
    final public GameObjectType type;

    public Fighter(GameObjectType type) {
        this.type=type;
    }
}
