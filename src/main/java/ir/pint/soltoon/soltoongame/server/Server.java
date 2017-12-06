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
import java.util.List;

// @todo GameConfig.NUMBER_OF_PLAYERS and Min Env vars
public class Server implements Runnable {

    public HashMap<Long, Comminucation> comminucationById;

    private ComServer comServer;
    private List<Long> players;
    private ServerGameBoard gameBoard;
    private List<Long> gameObjectIDs;

    private Server(ArrayList<ComRemoteInfo> remoteInfo) {
        this.comServer = new ComServer(remoteInfo);
        comminucationById = new HashMap<>();
    }


    
    @Override
    public void run() {

    }
}
