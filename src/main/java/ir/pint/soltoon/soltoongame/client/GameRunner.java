package ir.pint.soltoon.soltoongame.client;

import ir.pint.soltoon.soltoongame.shared.GameConfig;
import ir.pint.soltoon.utils.clients.proxy.ThreadProxy;
import ir.pint.soltoon.utils.clients.proxy.TimeLimitConfig;
import ir.pint.soltoon.soltoongame.shared.communication.command.Command;
import ir.pint.soltoon.soltoongame.shared.communication.command.CommandInitialize;
import ir.pint.soltoon.soltoongame.shared.communication.query.InvalidQueryException;
import ir.pint.soltoon.soltoongame.shared.communication.query.Query;
import ir.pint.soltoon.soltoongame.shared.communication.query.QueryInitialize;
import ir.pint.soltoon.soltoongame.shared.communication.result.FailureResultException;
import ir.pint.soltoon.soltoongame.shared.communication.result.Result;
import ir.pint.soltoon.soltoongame.shared.communication.result.Status;
import ir.pint.soltoon.soltoongame.shared.data.Agent;
import ir.pint.soltoon.soltoongame.shared.data.Player;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;

import java.util.Map;
import java.util.Random;
// @todo rewrite this shit
public class GameRunner {
//    private static final int MAX_NULL_QUERIES = 5;
//    private TimeLimitConfig timeLimitConfig;
//    private SoltoonInterface ai;
//    private GameSocket gameSocket;
//    private ClientCommunicator clientCommunicator;
//    private Class<? extends Player> firstPlayerClass;
//
//    public GameRunner(Class<SoltoonGame> soltoonGame, Class<? extends Player> myGhoul) throws Exception {
//        this.timeLimitConfig = new TimeLimitConfig(10000, 1000);
//        this.ai = ThreadProxy.createBean(soltoonGame, SoltoonInterface.class, timeLimitConfig);
//        this.gameSocket = GameSocket.create("127.0.0.1", 8585);
//        this.clientCommunicator = new ClientCommunicator(gameSocket);
//        this.firstPlayerClass = myGhoul;
//    }
//
    public static void run(Class<? extends Player> myGhoul) {
//        try {
//            GameRunner gameRunner = new GameRunner(SoltoonGame.class, myGhoul);
//            gameRunner.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.exit(-1);
//        }
    }
//
//    private void start() throws Exception {
//        initialize();
//
//        timeLimitConfig.setTimeLimit(1000);
//        timeLimitConfig.setinitExtraTimeLimit(500);
//
//        int nullQueries = -1;
//        for (Query query = null; nullQueries < MAX_NULL_QUERIES; query = clientCommunicator.receiveQuery()) {
//            if (query == null) {
//                nullQueries++;
//                continue;
//            }
//
//            if (query instanceof QueryExit)
//                break;
//
//            Command command = ai.handleQuery(query);
//            Result result = clientCommunicator.sendCommand(command);
//        }
//        ResultStorage.save();
//    }
//
//    private void initialize() throws Exception {
//        Query initQuery = clientCommunicator.receiveQuery();
//
//        if (!(initQuery instanceof QueryInitialize)) {
//            throw new InvalidQueryException();
//        }
//
//        timeLimitConfig.setTimeLimit(GameConfig.CLIENT_ROUND_TIME);
//        timeLimitConfig.setExtraTimeLimit(GameConfig.CLIENT_ROUND_EXTRA);
//
//        Map<Long, Agent> id2ai = ai.getId2ai();
//
//        Long firstPlayerId = Math.abs(new Random().nextLong());
//        Result result = clientCommunicator.sendCommand(new CommandInitialize(firstPlayerId));
//
//        if (result.status == Status.FAILURE) {
//            throw new FailureResultException();
//        }
//
//        Player player = (Player) firstPlayerClass.getConstructor(Long.class).newInstance(firstPlayerId);
//
//        id2ai.put(firstPlayerId, player);
//
//    }
}
