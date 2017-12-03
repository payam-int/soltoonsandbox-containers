package ir.pint.soltoon.soltoongame.shared.data.action;

import ir.pint.soltoon.soltoongame.server.CoreGameBoard;
import ir.pint.soltoon.soltoongame.shared.data.map.Cell;
import ir.pint.soltoon.soltoongame.shared.data.map.Direction;
import ir.pint.soltoon.soltoongame.shared.data.map.GameObject;

public final class Move extends Action {
    public final Direction direction;

    public Move(Direction direction) {
        this.direction = direction;
    }

    @Override
    public boolean execute(CoreGameBoard gb) {
        GameObject o = gb.getObjectByID(gb.getMyID());
        if (o==null) return true; //age yevaght GameObject nabud
        if (o.getRemainingRestingTime()!=0) return true;
        Cell currentCell = o.getCell();
        Cell newCell = gb.getCellByIndex(direction.dx() + currentCell.getX(), direction.dy() + currentCell.getY());
        if (newCell.gameObject != null)
            return true;
        else CoreGameBoard.giveCellToObject(newCell, currentCell.gameObject);
        o.resetRestingTime();

        System.out.println(o.id + "-move-" + newCell.getX() + ","+ newCell.getY());
        return false;
    }
}
