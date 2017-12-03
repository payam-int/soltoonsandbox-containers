package ir.pint.soltoon.soltoongame.shared.data.action;

import ir.pint.soltoon.soltoongame.server.CoreGameBoard;
import ir.pint.soltoon.soltoongame.shared.data.map.*;

public final class Shoot extends Action{
    public final int x,y;

    public Shoot(int x,int y) {
        this.x=x;
        this.y=y;
    }

    @Override
    public boolean execute(CoreGameBoard gb) {
        GameObject o = gb.getObjectByID(gb.getMyID());
        GameObjectType type = o.getClass().getAnnotation(CorrespondingAttributes.class).value();
        Cell target = gb.getCellByIndex(x,y);
        if (target==null) return true;
        if (target.getDistance(o.getCell()) > type.getShootingRange()) return true;
        o.resetReloadingTime();
        System.out.println(o.id + "-shoot-"+ x + ","+y);

        GameObject dead = gb.ShootToCell(target,type.getShootingPower());
        if (dead!=null) {
            System.out.println(dead.id +"-killed");
            gb.increasePenaltyByID(gb.getownerByID(dead.id), dead.type.getPenalty());
            System.out.println(gb.getMyID()+"-penalty-"+dead.type.getPenalty());
        }
        return false;
    }
}
