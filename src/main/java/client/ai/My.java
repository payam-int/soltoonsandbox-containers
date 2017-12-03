package client.ai;

import ir.pint.soltoon.soltoongame.shared.data.Player;
import ir.pint.soltoon.soltoongame.shared.data.action.Action;
import ir.pint.soltoon.soltoongame.shared.data.action.AddAgent;
import ir.pint.soltoon.soltoongame.shared.data.action.Nothing;
import ir.pint.soltoon.soltoongame.shared.data.map.GameBoard;
import ir.pint.soltoon.soltoongame.shared.data.map.GameObjectType;

public class My extends Player {

    public My(Long id) {
        super(id);
    }

    @Override
    public void init() {
    }

    @Override
    public void lastThingsToDo(GameBoard gameBoard) {

    }

    @Override
    public Action getAction(GameBoard gb) {
        if (gb.getMoneyByID(id) >= GameObjectType.MUSKETEER.getCost()) {
            if (gb.getCellByIndex(0, 0).gameObject == null) {
                return new AddAgent(new SampleGhoulAgent(GameObjectType.MUSKETEER), 0, 0);
            } else {
                return new Nothing();
            }
        } else {
            return new Nothing();
        }
    }
}
