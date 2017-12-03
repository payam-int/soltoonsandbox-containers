package ir.pint.soltoon.soltoongame.shared.data.map;

import java.io.Serializable;

public abstract class GameObject implements Serializable{
    public final Long id;
    private Cell cell=null;
    private int remainingRestingTime=0;
    private int remainingReloadingTime=0;
    private int hp;
    public final GameObjectType type;

    public GameObject(Long id) {
        this.id = id;
        type = this.getClass().getDeclaredAnnotation(CorrespondingAttributes.class).value();
        hp = type.getHP();
        remainingReloadingTime = type.getReloadingTime();
        remainingRestingTime = type.getRestingTime();
    }

    public int getRemainingRestingTime() { return remainingRestingTime; }
    public void remainingRestingTimeMM() {
        remainingRestingTime--;
        if (remainingRestingTime<0) remainingRestingTime=0;
    }
    public void resetRestingTime() {
        remainingReloadingTime = type.getRestingTime();
    }

    public int getRemainingReloadingTime() { return remainingReloadingTime; }
    public void remainingReloadingTimeMM() {
        remainingReloadingTime--;
        if (remainingReloadingTime<0) remainingReloadingTime=0;
    }
    public void resetReloadingTime() {
        remainingReloadingTime = type.getReloadingTime();
    }

    public Cell getCell() { return cell;}
    public void setCell(Cell cell) { this.cell=cell; }

    public int getHp() {
        return hp;
    }

    public boolean damageBy(int amount) {
        if (amount<0) return false;
        hp-=amount;
        return hp<=0;
    }
}
