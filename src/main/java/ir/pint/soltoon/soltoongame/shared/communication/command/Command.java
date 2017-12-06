package ir.pint.soltoon.soltoongame.shared.communication.command;

import ir.pint.soltoon.soltoongame.shared.communication.Message;

/**
 * Created by amirkasra on 9/28/2017 AD.
 */
public abstract class Command extends Message {
    public static long DEFAULT_CLIENT = -1;

    private long client;

    public Command(long request) {
        super(request);
        this.client = DEFAULT_CLIENT;
    }

    public Command(long request, long client) {
        super(request);
        this.client = client;
    }

    public long getClient() {
        return client;
    }

    public void setClient(long client) {
        this.client = client;
    }
}
