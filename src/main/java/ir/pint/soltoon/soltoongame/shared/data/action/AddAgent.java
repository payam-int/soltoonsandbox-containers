package ir.pint.soltoon.soltoongame.shared.data.action;

import ir.pint.soltoon.soltoongame.server.CoreGameBoard;
import ir.pint.soltoon.soltoongame.shared.data.Fighter;
import ir.pint.soltoon.soltoongame.shared.data.map.GameObject;
import ir.pint.soltoon.utils.shared.facades.ResultStorage;

public final class AddAgent extends Action{
    public final Fighter AI;
    public final int x,y;

    public AddAgent(Fighter AI, int x, int y) {
        super("AddAgent");
        this.AI=AI;
        this.x=x;
        this.y=y;
    }

    @Override
    public boolean execute(CoreGameBoard gb, long playerId) {
        if (gb.getObjectByID(gb.getMyID())!=null) return true; //GameObject nabashe yevaght
        if (gb.getCellByIndex(x,y).gameObject !=null) return true; // por nabashe yevaght
        if (gb.getMoneyByID(gb.getMyID()) - AI.type.getCost() < 0) {
            //System.out.println("ADD FAILED");
            return true;
        }
        boolean ok=true;
        /*for (Long pid : gb.getPlayerIDs())
            if (pid!=gb.getMyID())
              for (Long id : gb.idsByPlayerID(pid)) {
                    GameObject o = gb.getObjectByID(id);
                    if (gb.getCellByIndex(x,y).getDistance(o.getCell()) <= AI.type.getShootingRange())
                        ok=false;
              }*/
        if(!ok) return true;

        gb.decreaseMoneyByID(gb.getMyID(),AI.type.getCost());

        GameObject o = AI.type.getFactory(AI.id);
        CoreGameBoard.giveCellToObject(gb.getCellByIndex(x,y),o);
        gb.addObject(o);
//        System.out.println(AI.id+ "-appear-" + x +"," + y + "-" + AI.type + "-" + gb.getMyID());
        this.setCreator(playerId);
        ResultStorage.addEvent(this);
        return false;
    }
}
