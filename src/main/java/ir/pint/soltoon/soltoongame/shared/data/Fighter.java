package ir.pint.soltoon.soltoongame.shared.data;

import ir.pint.soltoon.soltoongame.shared.data.map.GameObjectType;
import ir.pint.soltoon.utils.shared.facades.json.Secure;

/**
 * Created by amirkasra on 9/30/2017 AD.
 */
@Secure
public abstract class Fighter extends Agent {
    final public GameObjectType type;

    public Fighter(GameObjectType type) {
        this.type=type;
    }
}
