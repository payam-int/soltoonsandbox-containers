package ir.pint.soltoon.soltoongame.shared.communication.result;

import ir.pint.soltoon.soltoongame.shared.communication.Message;

import java.util.HashMap;

/**
 * Created by amirkasra on 9/28/2017 AD.
 */
public abstract class Result extends Message {
    public HashMap <String,Object> data;
    public Status status;

    public Result(Long request, Status status, HashMap data) {
        super(request);
        this.data = data;
        this.status=status;
    }

    public Result(long request, Status status) {
        super(request);
        this.status = status;
    }
}
