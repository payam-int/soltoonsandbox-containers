package ir.pint.soltoon.soltoongame.shared.data;

import ir.pint.soltoon.utils.shared.facades.json.Secure;

/**
 * Created by amirkasra on 9/30/2017 AD.
 */

@Secure

public abstract class Player extends Agent {
    public Player(Long id) {
        super(id);
    }
}
