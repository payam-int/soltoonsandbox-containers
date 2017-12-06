package ir.pint.soltoon.soltoongame.shared.data.action;

import ir.pint.soltoon.soltoongame.server.ServerGameBoard;
import ir.pint.soltoon.soltoongame.shared.data.map.Cell;
import ir.pint.soltoon.soltoongame.shared.data.map.Direction;
import ir.pint.soltoon.soltoongame.shared.data.map.GameObject;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;

public final class Move extends Action {
    public final Direction direction;

    public Move(Direction direction) {
        super("Move");
        this.direction = direction;
    }

    @Override
    public boolean execute(ServerGameBoard gb, long playerId) {
        GameObject o = gb.getObjectByID(gb.getMyID());
        if (o==null) return true; //age yevaght GameObject nabud
        if (o.getRemainingRestingTime()!=0) return true;
        Cell currentCell = o.getCell();
        Cell newCell = gb.getCellByIndex(direction.dx() + currentCell.getX(), direction.dy() + currentCell.getY());
        if (newCell.gameObject != null)
            return true;
        else ServerGameBoard.giveCellToObject(newCell, currentCell.gameObject);
        o.resetRestingTime();

//        System.out.println(o.id + "-move-" + newCell.getX() + ","+ newCell.getY());
        this.setCreator(playerId);
        ResultStorage.addEvent(this);
        return false;
    }
}
