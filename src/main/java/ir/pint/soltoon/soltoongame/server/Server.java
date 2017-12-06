package ir.pint.soltoon.soltoongame.server;

import ir.pint.soltoon.soltoongame.shared.communication.command.Command;
import ir.pint.soltoon.soltoongame.shared.communication.query.Query;
import ir.pint.soltoon.soltoongame.shared.communication.result.Result;
import ir.pint.soltoon.utils.shared.comminucation.ComRemoteInfo;
import ir.pint.soltoon.utils.shared.comminucation.ComServer;
import ir.pint.soltoon.utils.shared.comminucation.Comminucation;
import ir.pint.soltoon.utils.shared.exceptions.NoComRemoteAvailable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
// @todo GameConfig.NUMBER_OF_PLAYERS and Min Env vars
public class Server {

    public HashMap<Long, Comminucation> comminucationById;
    private ComServer comServer;


    private Server(ArrayList<ComRemoteInfo> remoteInfo) {
        this.comServer = new ComServer(remoteInfo);
        comminucationById = new HashMap<>();
    }

    public Comminucation init() throws IOException, NoComRemoteAvailable {
        return comServer.connect();
    }

    public void closeClient(long uid) {
        Comminucation comminucation = comminucationById.get(uid);
        if (comminucation == null)
            return;

        comminucation.close();
    }

    public Command sendQuery(Query query, long client) throws IOException {
        Comminucation comminucation = comminucationById.get(client);
        comminucation.getObjectOutputStream().writeObject(query);
        return (Command) comminucation.getObjectInputStream().readObject();
    }

    public void sendResult(Result result, long client) throws IOException {
        Comminucation comminucation = comminucationById.get(client);
        comminucation.getObjectOutputStream().writeObject(result);
    }
}
