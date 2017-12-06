package ir.pint.soltoon.soltoongame.shared.data.map;

import ir.pint.soltoon.utils.shared.facades.json.Secure;

import java.io.Serializable;

@Secure
public final class Cell implements Serializable{

    private int x, y;

    public GameObject gameObject =null;

    public Cell(int x,int y) {
        this.x=x;
        this.y=y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDistance(Cell destination) {
        int difX = this.getX() - destination.getX();
        int difY = this.getY() - destination.getY();
        //ToDo kodum behtare ?
        //return (int)(Math.ceil(Math.sqrt(Math.pow(difX, 2) + Math.pow(difY, 2))));
        return Math.abs(difX)+Math.abs(difY);
    }

    @Override
    public String toString() {
        if (gameObject instanceof Giant) return "G";
        if (gameObject instanceof Tower) return "T";
        return ".";
    }
}

