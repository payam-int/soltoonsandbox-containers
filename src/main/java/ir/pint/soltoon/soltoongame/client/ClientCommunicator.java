package ir.pint.soltoon.soltoongame.client;

//import ir.pint.soltoon.utils.clients.comminucation.GameSocket;
import ir.pint.soltoon.soltoongame.shared.communication.command.Command;
import ir.pint.soltoon.soltoongame.shared.communication.query.Query;
import ir.pint.soltoon.soltoongame.shared.communication.result.Result;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// @todo  

public class ClientCommunicator {
//
//    private GameSocket gameSocket;
//    private ObjectInputStream in;
//    private ObjectOutputStream out;
//
//    public ClientCommunicator(GameSocket gameSocket) {
//        this.gameSocket = gameSocket;
//        this.in = gameSocket.getObjectInputStream();
//        this.out = gameSocket.getObjectOutputStream();
//    }
//
//    public Result sendCommand(Command command) {
//        Result result = null;
//        try {
//            out.reset();
//            out.writeObject(command);
//            out.flush();
//            result = (Result) in.readObject();
//        } catch (Exception e) {
//            ResultStorage.addException(e);
//        }
//        return result;
//    }
//
//    public Query receiveQuery() {
//        Query query = null;
//        try {
//            query = (Query) in.readObject();
//        } catch (Exception e) {
//            ResultStorage.addException(e);
//        }
//        return query;
//    }

}
