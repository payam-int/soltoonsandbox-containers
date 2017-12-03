package ir.pint.soltoon.soltoongame.server;

import ir.pint.soltoon.soltoongame.shared.communication.Comminucation;
import ir.pint.soltoon.soltoongame.shared.communication.command.Command;
import ir.pint.soltoon.soltoongame.shared.communication.command.CommandInitialize;
import ir.pint.soltoon.soltoongame.shared.communication.query.Query;
import ir.pint.soltoon.soltoongame.shared.communication.query.QueryInitialize;
import ir.pint.soltoon.soltoongame.shared.communication.result.Result;
import ir.pint.soltoon.utils.clients.comminucation.GameSocket;
import ir.pint.soltoon.utils.server.comminucation.GameClient;
import ir.pint.soltoon.utils.server.comminucation.GameSocketServer;
import ir.pint.soltoon.utils.shared.exceptions.SoltoonContainerException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    private GameSocketServer gameSocketServer;
    public HashMap<Long, GameClient> id2client;

    Server(int port) {
        try {
            this.gameSocketServer = new GameSocketServer(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        id2client = new HashMap<>();
    }

    public CommandInitialize accept() {
        GameClient client;
        try {

            client = gameSocketServer.getClient();
            client.send(new QueryInitialize(0l));
            CommandInitialize c = (CommandInitialize) client.receive(Comminucation.CLIENT_RECIEVE_TIME);
            if (c != null)
                id2client.put(c.id, client);

            return c;

        } catch (Exception e) {
            return null;
        }
    }

    public Command sendQuery(Query query) throws IOException {
        GameClient ch = id2client.get(query.id);
        ch.send(query);
        return (Command) ch.receive(Comminucation.CLIENT_RECIEVE_TIME);
    }

    public void sendResult(Result result) throws IOException {
        id2client.get(result.id).send(result);
    }
}
