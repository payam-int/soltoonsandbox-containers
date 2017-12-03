package ir.pint.soltoon.soltoongame.shared.data.action;

import ir.pint.soltoon.soltoongame.server.CoreGameBoard;

import java.io.Serializable;

public abstract class Action implements Serializable{
    public abstract boolean execute(CoreGameBoard gb);
}
