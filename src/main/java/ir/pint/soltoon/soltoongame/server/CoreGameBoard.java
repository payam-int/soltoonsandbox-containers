package ir.pint.soltoon.soltoongame.server;

import ir.pint.soltoon.soltoongame.shared.data.map.Cell;
import ir.pint.soltoon.soltoongame.shared.data.map.GameBoard;
import ir.pint.soltoon.soltoongame.shared.data.map.GameObject;

import java.util.ArrayList;

/**
 * Created by amirkasra on 9/30/2017 AD.
 */
public class CoreGameBoard extends GameBoard {
    public ArrayList<Long> recentlyKilledIDs;


    public CoreGameBoard(int h, int w) {
        super(h, w);
        recentlyKilledIDs = new ArrayList<>();

    }

    public void setMoneyPerTurn(Long id,int x) {
        this.playerID2moneyPerTurn.put(id,x);
    }

    public void timePassedForCurrentPlayer() {
        GameObject o = getObjectByID(getMyID());
        if (o==null) {
            playerID2money.put(getMyID(),getMoneyByID(myID)+getMoneyPerTurn(myID));
        } else {
            o.remainingReloadingTimeMM();
            o.remainingRestingTimeMM();
        }
    }

    public void print() {
        for (int j=0;j<height;j++) {
            System.out.println();
            for (int i = 0; i < width; i++)
                System.out.print(getCellByIndex(i,j));
        }
    }

    public GameObject ShootToCell(Cell target, Integer amount) {
        GameObject o = target.gameObject;
        if (o==null) return null;
        if (o.damageBy(amount)) {
            deleteObjectByID(o.id);
            recentlyKilledIDs.add(o.id);
            return o;
        }
        return null;
    }

    private void deleteObjectByID(Long id) {
        GameObject o = id2object.get(id);
        if (o==null) return;
        o.getCell().gameObject =null;
        id2object.remove(id);
        id2owner.remove(id);
        for (Long pid: playerID2ids.keySet())
            playerID2ids.get(pid).remove(o);
    }

    public void setMyID(Long myID) {
        playerIDs.add(myID);
        this.myID =myID;
    }

    public void decreaseMoneyByID(Long id,int value) {
        if (value<0) return;
        if (getMoneyByID(id)-value<0) value = getMoneyByID(id);
        playerID2money.put(id,getMoneyByID(id)-value);
    }

    public void increasePenaltyByID(Long id,int value) {
        if (value<0) return;
        playerID2penalty.put(id,getPenaltyByID(id)+value);
    }

    public static void giveCellToObject(Cell cell, GameObject o) {
        if (o.getCell()!=null)
            o.getCell().gameObject =null;
        cell.gameObject = o;
        o.setCell(cell);
    }

    public void addObject(GameObject o) {
        id2object.put(o.id,o);
        id2owner.put(o.id,myID);
        //playerID2ids.get(myID).add(o.id);
    }
}
