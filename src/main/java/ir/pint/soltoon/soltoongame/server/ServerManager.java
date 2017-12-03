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
import ir.pint.soltoon.utils.shared.facades.ResultStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by amirkasra on 9/30/2017 AD.
 */
public class ServerManager extends Thread {

    public final int NUMBER_OF_PLAYERS = 2;
    public final int HEIGHT = 10, WIDTH = 10;
    public final int STEPS = 10;

    private Server server;
    private List<Long> playerIDs;
    private CoreGameBoard gameBoard;
    private List<Long> gameObjectIDs;
    private Random random;

    public ServerManager(Server server) {

        this.server = server;
        this.random = new Random();
        this.playerIDs = new ArrayList<>();
        this.gameObjectIDs = new ArrayList<>();
        this.gameBoard = new CoreGameBoard(HEIGHT, WIDTH);

    }

    @Override
    public void run() {
        getClients();


        int step = 0;
        while (step++ < STEPS) {
            for (Long playerID : playerIDs) {
                while (true) {
                    gameBoard.setMyID(playerID);
                    gameBoard.setMoneyPerTurn(gameBoard.getMyID(), 5);
                    gameBoard.timePassedForCurrentPlayer();
                    gameBoard.inc();
                    Command command = null;
                    try {
                        command = server.sendQuery(new QueryAction(playerID, gameBoard));
                    } catch (IOException e) {
                        ResultStorage.addException(e);
                        break;
                    }

                    try {

                        if (command instanceof CommandAction) {

                            Action action = ((CommandAction) command).action;

                            if (action instanceof AddAgent || action instanceof Nothing) {
                                if (action.execute(gameBoard, playerID))
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
                        ResultStorage.addException(e);
                        try {
                            server.sendResult(new ResultAction(((CommandAction) command).id, Status.failure, new HashMap()));
                        } catch (Exception writeException) {
                            ResultStorage.addException(writeException);
                        }
                        break;
                    } catch (IOException e) {
                        ResultStorage.addException(e);
                    }
                }

//                gameBoard.print();
            }

            for (Long id : gameObjectIDs)
                queryGameObjectByID(id, new QueryAction(id, gameBoard));

            for (Long dead : gameBoard.recentlyKilledIDs)
                gameObjectIDs.remove(dead);


        }

        ResultStorage.save();
    }

    private void getClients() {
        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            Command ci = server.accept();
            if (ci == null) {
                i--;
                continue;
            }

            playerIDs.add(ci.id);
            try {
                if (ci instanceof CommandInitialize) {
                    server.sendResult(new ResultInitialize(ci.id, Status.success, new HashMap()));
                } else {
                    try {
                        throw new InvalidCommandException();
                    } catch (InvalidCommandException e) {
                        ResultStorage.addException(e);
                        server.sendResult(new ResultInitialize(ci.id, Status.failure, new HashMap()));
                    }
                }
            } catch (IOException e) {
                ResultStorage.addException(e);
            }
            System.out.println(i + " th player joined with id: " + ci.id);
        }
    }

    private void queryGameObjectByID(Long id, Query query) {
        if (gameBoard.getObjectByID(id) == null) return; //hamzaman dast nazanim !

        gameBoard.setMyID(id);
        gameBoard.timePassedForCurrentPlayer();
        Command command = null;
        try {
            command = server.sendQuery(query);
        } catch (IOException e) {
            ResultStorage.addException(e);
        }
        if (query instanceof QueryFinalize) {
            try {
                server.sendResult(new ResultAction(((CommandAction) command).id, Status.success, new HashMap()));
            } catch (IOException e) {
                ResultStorage.addException(e);
            }
            return;
        }

        try {
            if (command instanceof CommandAction) {
                Action action = ((CommandAction) command).action;
                if (action.execute(gameBoard, gameBoard.getownerByID(id)))
                    throw new InvalidCommandException();
                else {
                    try {
                        server.sendResult(new ResultAction(((CommandAction) command).id, Status.success, new HashMap()));
                    } catch (IOException e) {
                        ResultStorage.addException(e);
                    }
                    if (action instanceof Shoot) {
                        for (Long dead : gameBoard.recentlyKilledIDs)
                            queryGameObjectByID(dead, new QueryFinalize(dead, gameBoard));
                    }
                }
            } else
                throw new InvalidCommandException();
        } catch (InvalidCommandException e) {
            ResultStorage.addException(e);
            try {
                server.sendResult(new ResultAction(((CommandAction) command).id, Status.failure, new HashMap()));
            } catch (IOException e1) {
                ResultStorage.addException(e1);
            }
        }
    }
}
