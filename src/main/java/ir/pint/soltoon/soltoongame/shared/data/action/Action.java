package ir.pint.soltoon.soltoongame.shared.data.action;

import ir.pint.soltoon.soltoongame.server.ServerGameBoard;
import ir.pint.soltoon.utils.shared.facades.json.Secure;
import ir.pint.soltoon.utils.shared.facades.result.EventLog;

import java.io.Serializable;

@Secure
public abstract class Action implements Serializable, EventLog {
    private String name;
    private long creator;
    private long createTimestamp;

    public Action(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreator() {
        return creator;
    }

    public void setCreator(long creator) {
        this.creator = creator;
    }

    public long getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(long createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public abstract boolean execute(ServerGameBoard gb, long playerId);
}
