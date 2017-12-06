package ir.pint.soltoon.soltoongame.client;

import ir.pint.soltoon.soltoongame.shared.communication.result.Result;
import ir.pint.soltoon.utils.clients.ai.GameInterface;
import ir.pint.soltoon.soltoongame.shared.communication.command.Command;
import ir.pint.soltoon.soltoongame.shared.communication.query.Query;
import ir.pint.soltoon.soltoongame.shared.data.Agent;

import java.util.Map;

public interface SoltoonInterface extends GameInterface {
    Command handleQuery(Query query);
    Map<Long, Agent> getId2ai();
    void setResult(Result result, Query query, Command command);
}
