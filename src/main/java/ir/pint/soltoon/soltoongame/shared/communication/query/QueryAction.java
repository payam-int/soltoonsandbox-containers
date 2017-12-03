package ir.pint.soltoon.soltoongame.shared.communication.query;

import ir.pint.soltoon.soltoongame.shared.data.map.GameBoard;

/**
 * Created by amirkasra on 9/28/2017 AD.
 */
public class QueryAction extends Query {
    public GameBoard gameBoard;
    public QueryAction(Long id, GameBoard gameBoard) {
        super(id);
        this.gameBoard = gameBoard;
    }
}
