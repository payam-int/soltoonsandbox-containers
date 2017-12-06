package ir.pint.soltoon.soltoongame.server;

import ir.pint.soltoon.soltoongame.shared.GameConfig;
import ir.pint.soltoon.soltoongame.shared.communication.command.Command;
import ir.pint.soltoon.soltoongame.shared.communication.command.CommandAction;
import ir.pint.soltoon.soltoongame.shared.communication.command.InvalidCommandException;
import ir.pint.soltoon.soltoongame.shared.communication.query.Query;
import ir.pint.soltoon.soltoongame.shared.communication.query.QueryAction;
import ir.pint.soltoon.soltoongame.shared.communication.query.QueryFinalize;
import ir.pint.soltoon.soltoongame.shared.communication.result.ResultAction;
import ir.pint.soltoon.soltoongame.shared.communication.result.Status;
import ir.pint.soltoon.soltoongame.shared.data.action.*;
import ir.pint.soltoon.utils.shared.comminucation.Comminucation;
import ir.pint.soltoon.utils.shared.exceptions.NoComRemoteAvailable;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by amirkasra on 9/30/2017 AD.
 *
 * @todo rewrite server x10000
 * @todo wait between two retries sleep
 */
public class ServerManager extends Thread {

    private Server server;
    private List<Long> players;
    private ServerGameBoard gameBoard;
    private List<Long> gameObjectIDs;

    public ServerManager() {

        this.server = server; // @todo
        this.players = new ArrayList<>();
        this.gameObjectIDs = new ArrayList<>();
        this.gameBoard = new ServerGameBoard(GameConfig.HEIGHT, GameConfig.WIDTH);

    }

    @Override
    public void run() {
        initializeClients();


        int step = 0;
        while (step++ < GameConfig.STEPS) {
            doTurn();
        }
    }


    private void initializeClients() throws NoComRemoteAvailable {
        for (int i = 0; i < GameConfig.NUMBER_OF_PLAYERS; ) {

            Comminucation connect = null;
            try {
                connect = server.connect();
            } catch (IOException e) {

            }

            if (connect == null)
                continue;

            server.send

            if (commandInitialize == null) {
                i--;
                continue;
            }

            players.add(commandInitialize.getClient());

//            System.out.println(i + " th player joined with id: " + ci.id);
        }
    }

    private void doTurn() {
        for (Long playerID : players) {
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
                                server.sendResult(new ResultAction(((CommandAction) command).id, Status.SUCCESS, new HashMap()));
                                if (action instanceof AddAgent) {
                                    // Registering GameObject
                                    gameObjectIDs.add(((AddAgent) action).AI.id);
                                    server.comminucationById.put(((AddAgent) action).AI.id, server.comminucationById.get(((CommandAction) command).id));
                                } else if (action instanceof Nothing) break;
                            }
                        } else throw new InvalidCommandException();

                    } else throw new InvalidCommandException();
                } catch (InvalidCommandException e) {
                    ResultStorage.addException(e);
                    try {
                        server.sendResult(new ResultAction(((CommandAction) command).id, Status.FAILURE, new HashMap()));
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
                server.sendResult(new ResultAction(((CommandAction) command).id, Status.SUCCESS, new HashMap()));
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
                        server.sendResult(new ResultAction(((CommandAction) command).id, Status.SUCCESS, new HashMap()));
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
                server.sendResult(new ResultAction(((CommandAction) command).id, Status.FAILURE, new HashMap()));
            } catch (IOException e1) {
                ResultStorage.addException(e1);
            }
        }
    }
}
