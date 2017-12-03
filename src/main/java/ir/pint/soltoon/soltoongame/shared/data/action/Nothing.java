package ir.pint.soltoon.soltoongame.shared.data.action;

import ir.pint.soltoon.soltoongame.server.CoreGameBoard;
import ir.pint.soltoon.utils.shared.facades.ResultStorage;

public final class Nothing extends Action {

    public Nothing() {
        super("Nothing");
    }

    @Override
    public boolean execute(CoreGameBoard gb, long playerId) {
        this.setCreator(playerId);
        ResultStorage.addEvent(this);
        return false;
    }
}
