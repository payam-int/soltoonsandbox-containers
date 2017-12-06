package ir.pint.soltoon.soltoongame.shared.communication.query;

import ir.pint.soltoon.soltoongame.shared.data.map.GameBoard;

/**
 * Created by amirkasra on 10/1/2017 AD.
 */
public class QueryFinalize extends QueryAction {
    public QueryFinalize(long target, GameBoard gameBoard) {
        super(target, gameBoard);
    }

}
