package ir.pint.soltoon.soltoongame.shared.communication.command;

import ir.pint.soltoon.soltoongame.shared.data.action.Action;

public class CommandAction extends Command{

    public Action action;
    public CommandAction(long request, Action action) {
        super(request);
        this.action=action;
    }

    public CommandAction(long request, long client, Action action) {
        super(request, client);
        this.action = action;
    }


}
