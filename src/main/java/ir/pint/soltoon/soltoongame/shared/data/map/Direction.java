package ir.pint.soltoon.soltoongame.shared.data.map;

import ir.pint.soltoon.utils.shared.facades.json.Secure;

/**
 * Created by amirkasra on 9/29/2017 AD.
 */
@Secure
public enum Direction {
    right,left,up,down;

    public int dx() {
        switch (this) {
            case down:
                return 0;
            case left:
                return -1;
            case right:
                return 1;
            case up:
                return 0;
        }
        return 0;
    }

    public int dy() {
        switch (this) {
            case down:
                return -1;
            case left:
                return 0;
            case right:
                return 0;
            case up:
                return 1;
        }
        return 0;
    }
}
