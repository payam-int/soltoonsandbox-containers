package ir.pint.soltoon.soltoongame.shared.data.map;

import ir.pint.soltoon.utils.shared.facades.json.Secure;

/**
 * @author Amirkasra Jalaldoost, Payam Mohammadi
 */
@Secure
public enum GameObjectType {
    MUSKETEER, BOMBER, GIANT
    , TOWER, MORTAR, TESLA, INFERNO;

    public Integer getHP() {
        switch (this) {
            case MUSKETEER:
                return 100;
            case BOMBER:
                return 50;
            case GIANT:
                return 500;
            case TOWER:
                return 2000;
            case MORTAR:
                return 300;
            case TESLA:
                return 300;
            case INFERNO:
                return 300;
        }
        return null;
    }

    public Integer getReloadingTime() {
        switch (this) {
            case BOMBER:
                return 1;
            case GIANT:
                return 4;
            case TOWER:
                return 1;
            case MUSKETEER:
                return 2;
            case MORTAR:
                return 5;
            case TESLA:
                return 2;
            case INFERNO:
                return 0;
        }
        return null;
    }

    public Integer getRestingTime() {
        switch (this) {
            case BOMBER:
                return 0;
            case GIANT:
                return 2;
            case TOWER:
                return Integer.MAX_VALUE;
            case MUSKETEER:
                return 1;
            case MORTAR:
                return Integer.MAX_VALUE;
            case TESLA:
                return Integer.MAX_VALUE;
            case INFERNO:
                return Integer.MAX_VALUE;
        }
        return null;
    }

    public GameObject getFactory(Long id) {
        switch (this) {
            case MUSKETEER:
                return new Giant(id);
            case TOWER:
                return new Tower(id);
        }
        return null;
    }

    public Integer getCost() {
        switch (this) {
            case BOMBER:
                return 10;
            case GIANT:
                return 50;
            case TOWER:
                return 1000;
            case MUSKETEER:
                return 20;
            case MORTAR:
                return 200;
            case TESLA:
                return 150;
            case INFERNO:
                return 300;
        }
        return null;
    }

    public Integer getShootingRange() {
        switch (this) {
            case MUSKETEER:
                return 5;
            case BOMBER:
                return 3;
            case GIANT:
                return 2;
            case TOWER:
                return 8;
            case MORTAR:
                return 6;
            case TESLA:
                return 4;
            case INFERNO:
                return 5;
        }
        return null;
    }

    public Integer getShootingPower() {
        switch (this) {
            case TOWER:
               return  50;
            case MUSKETEER:
                return 5;
        }
        return null;
    }

    public Integer getPenalty() {
        return getCost()/2;
    }
}
