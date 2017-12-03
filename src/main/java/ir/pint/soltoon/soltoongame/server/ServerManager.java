package ir.pint.soltoon.soltoongame.server;

import ir.pint.soltoon.soltoongame.shared.communication.command.Command;
import ir.pint.soltoon.soltoongame.shared.communication.command.CommandAction;
import ir.pint.soltoon.soltoongame.shared.communication.command.CommandInitialize;
import ir.pint.soltoon.soltoongame.shared.communication.command.InvalidCommandException;
import ir.pint.soltoon.soltoongame.shared.communication.query.Query;
import ir.pint.soltoon.soltoongame.shared.communication.query.QueryAction;
import ir.pint.soltoon.soltoongame.shared.communication.query.QueryFinalize;
import ir.pint.soltoon.soltoongame.shared.communication.result.ResultAction;
import ir.pint.soltoon.soltoongame.shared.communication.result.ResultInitialize;
import ir.pint.soltoon.soltoongame.shared.communication.result.Status;
import ir.pint.soltoon.soltoongame.shared.data.action.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by amirkasra on 9/30/2017 AD.
 */
public class ServerManager extends Thread {

    public final int NUMBER_OF_PLAYERS = 2;
    public final int HEIGHT = 10, WIDTH = 10;
    public final int STEPS = 20;

    Server server;
    ArrayList<Long> playerIDs;
    CoreGameBoard gb;
    ArrayList<Long> gameObjectIDs;

    public ServerManager(Server server) {
        this.server = server;
        Random random = new Random();
        playerIDs = new ArrayList<>();
        gameObjectIDs = new ArrayList<>();
        gb = new CoreGameBoard(HEIGHT, WIDTH);
        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            Command ci = server.accept();
            playerIDs.add(ci.id);
            if (ci instanceof CommandInitialize) {
                server.sendResult(new ResultInitialize(ci.id, Status.success, new HashMap()));
            } else try {
                throw new InvalidCommandException();
            } catch (InvalidCommandException e) {
                e.printStackTrace();
                server.sendResult(new ResultInitialize(ci.id, Status.failure, new HashMap()));
            }
            System.out.println(i + " th player joined with id: " + ci.id);
        }
    }

    @Override
    public void run() {
        int step = 0;
        while (step++ < STEPS) {
            for (Long playerID : playerIDs) {
                while (true) {
                    gb.setMyID(playerID);
                    gb.setMoneyPerTurn(gb.getMyID(), 5);
                    gb.timePassedForCurrentPlayer();
                    Command command = server.sendQuery(new QueryAction(playerID, gb));

                    try {

                        if (command instanceof CommandAction) {

                            Action action = ((CommandAction) command).action;

                            if (action instanceof AddAgent || action instanceof Nothing) {
                                if (action.execute(gb))
                                    throw new InvalidCommandException();
                                else {
                                    server.sendResult(new ResultAction(((CommandAction) command).id, Status.success, new HashMap()));
                                    if (action instanceof AddAgent) {
                                        // Registering GameObject
                                        gameObjectIDs.add(((AddAgent) action).AI.id);
                                        server.id2client.put(((AddAgent) action).AI.id, server.id2client.get(((CommandAction) command).id));
                                    } else if (action instanceof Nothing) break;
                                }
                            } else throw new InvalidCommandException();

                        } else throw new InvalidCommandException();
                    } catch (InvalidCommandException e) {
                        e.printStackTrace();
                        server.sendResult(new ResultAction(((CommandAction) command).id, Status.failure, new HashMap()));
                        break;
                    }
                }

                gb.print();
            }

            for (Long id : gameObjectIDs)
                queryGameObjectByID(id, new QueryAction(id, gb));

            for (Long dead : gb.recentlyKilledIDs)
                gameObjectIDs.remove(dead);
        }
    }

    private void queryGameObjectByID(Long id, Query query) {
        if (gb.getObjectByID(id) == null) return; //hamzaman dast nazanim !

        gb.setMyID(id);
        gb.timePassedForCurrentPlayer();
        Command command = server.sendQuery(query);
        if (query instanceof QueryFinalize) {
            server.sendResult(new ResultAction(((CommandAction) command).id, Status.success, new HashMap()));
            return;
        }

        try {
            if (command instanceof CommandAction) {
                Action action = ((CommandAction) command).action;
                if (action.execute(gb))
                    throw new InvalidCommandException();
                else {
                    server.sendResult(new ResultAction(((CommandAction) command).id, Status.success, new HashMap()));
                    if (action instanceof Shoot) {
                        for (Long dead : gb.recentlyKilledIDs)
                            queryGameObjectByID(dead, new QueryFinalize(dead, gb));
                    }
                }
            } else
                throw new InvalidCommandException();
        } catch (InvalidCommandException e) {
            e.printStackTrace();
            server.sendResult(new ResultAction(((CommandAction) command).id, Status.failure, new HashMap()));
        }
    }
}
