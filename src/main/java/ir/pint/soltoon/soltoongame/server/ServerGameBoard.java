package ir.pint.soltoon.soltoongame.server;

import ir.pint.soltoon.soltoongame.shared.data.map.Cell;
import ir.pint.soltoon.soltoongame.shared.data.map.GameBoard;
import ir.pint.soltoon.soltoongame.shared.data.map.GameObject;
import ir.pint.soltoon.utils.shared.facades.json.Secure;
import ir.pint.soltoon.utils.shared.facades.json.SecureConvert;

import java.util.ArrayList;

/**
 * Created by amirkasra on 9/30/2017 AD.
 */

@Secure
@SecureConvert(GameBoard.class)
public class ServerGameBoard extends GameBoard {
    private ArrayList<Long> recentlyKilledIDs;


    public ServerGameBoard(int h, int w) {
        super(h, w);
        recentlyKilledIDs = new ArrayList<>();

    }

    public void setMoneyPerTurn(Long id, int x) {
        this.playerMoneyPerTurn.put(id, x);
    }

    public void timePassedForCurrentPlayer() {
        GameObject o = getObjectByID(getMyID());
        if (o == null) {
            playerMoney.put(getMyID(), getMoneyByID(myID) + getMoneyPerTurn(myID));
        } else {
            o.remainingReloadingTimeMM();
            o.remainingRestingTimeMM();
        }
    }

    public GameObject ShootToCell(Cell target, Integer amount) {
        GameObject o = target.gameObject;
        if (o == null) return null;
        if (o.damageBy(amount)) {
            deleteObjectByID(o.id);
            recentlyKilledIDs.add(o.id);
            return o;
        }
        return null;
    }

    private void deleteObjectByID(Long id) {
        GameObject o = id2object.get(id);
        if (o == null) return;
        o.getCell().gameObject = null;
        id2object.remove(id);
        idOwner.remove(id);
        for (Long pid : playerOwnedIds.keySet())
            playerOwnedIds.get(pid).remove(o);
    }

    public void setMyID(Long myID) {
        playerIDs.add(myID);
        this.myID = myID;
    }

    public void decreaseMoneyByID(Long id, int value) {
        if (value < 0) return;
        if (getMoneyByID(id) - value < 0) value = getMoneyByID(id);
        playerMoney.put(id, getMoneyByID(id) - value);
    }

    public void increasePenaltyByID(Long id, int value) {
        if (value < 0) return;
        playerPenalty.put(id, getPenaltyByID(id) + value);
    }

    public static void giveCellToObject(Cell cell, GameObject o) {
        if (o.getCell() != null)
            o.getCell().gameObject = null;
        cell.gameObject = o;
        o.setCell(cell);
    }

    public void addObject(GameObject o) {
        id2object.put(o.id, o);
        idOwner.put(o.id, myID);
        //playerOwnedIds.get(myID).add(o.id);
    }
}
