package ir.pint.soltoon.soltoongame.client;

import ir.pint.soltoon.utils.clients.ai.Game;
import ir.pint.soltoon.soltoongame.shared.communication.command.Command;
import ir.pint.soltoon.soltoongame.shared.communication.command.CommandAction;
import ir.pint.soltoon.soltoongame.shared.communication.command.CommandFinalize;
import ir.pint.soltoon.soltoongame.shared.communication.query.Query;
import ir.pint.soltoon.soltoongame.shared.communication.query.QueryAction;
import ir.pint.soltoon.soltoongame.shared.communication.query.QueryFinalize;
import ir.pint.soltoon.soltoongame.shared.data.Agent;
import ir.pint.soltoon.soltoongame.shared.data.action.Action;
import ir.pint.soltoon.soltoongame.shared.data.action.AddAgent;

import java.util.Hashtable;
import java.util.Map;

public class SoltoonGame extends Game implements SoltoonInterface {
    private Map<Long, Agent> id2ai = new Hashtable<>();

    @Override
    public Command handleQuery(Query query) {
        if (query instanceof QueryAction) {
            Action action = id2ai.get(query.id).getAction(((QueryAction) query).gameBoard);
            if (action instanceof AddAgent) {
                id2ai.put(((AddAgent) action).AI.id, ((AddAgent) action).AI);
            }

            return (new CommandAction(query.id, action));
        } else if (query instanceof QueryFinalize) {
            id2ai.get(query.id).lastThingsToDo(((QueryFinalize) query).gameBoard);
            id2ai.remove(query.id);
            return (new CommandFinalize(query.id));
        }
        return null;
    }

    public Map<Long, Agent> getId2ai() {
        return id2ai;
    }
}
