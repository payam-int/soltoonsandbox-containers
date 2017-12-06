package ir.pint.soltoon.soltoongame.shared.data.action;

import ir.pint.soltoon.soltoongame.server.ServerGameBoard;
import ir.pint.soltoon.soltoongame.shared.data.map.*;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;

public final class Shoot extends Action{
    public final int x,y;

    public Shoot(int x,int y) {
        super("Shoot");
        this.x=x;
        this.y=y;
    }

    @Override
    public boolean execute(ServerGameBoard gb, long playerId) {
        GameObject o = gb.getObjectByID(gb.getMyID());
        GameObjectType type = o.getClass().getAnnotation(CorrespondingAttributes.class).value();
        Cell target = gb.getCellByIndex(x,y);
        if (target==null) return true;
        if (target.getDistance(o.getCell()) > type.getShootingRange()) return true;
        o.resetReloadingTime();
//        System.out.println(o.id + "-shoot-"+ x + ","+y);
        this.setCreator(playerId);
        ResultStorage.addEvent(this);

        GameObject dead = gb.ShootToCell(target,type.getShootingPower());
        if (dead!=null) {
//            System.out.println(dead.id +"-killed");
            gb.increasePenaltyByID(gb.getownerByID(dead.id), dead.type.getPenalty());
//            System.out.println(gb.getMyID()+"-penalty-"+dead.type.getPenalty());
        }
        return false;
    }
}
