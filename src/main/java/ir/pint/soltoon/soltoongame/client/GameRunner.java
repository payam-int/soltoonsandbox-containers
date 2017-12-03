package ir.pint.soltoon.soltoongame.client;

import ir.pint.soltoon.utils.clients.comminucation.GameSocket;
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

import java.util.Map;
import java.util.Random;

public class GameRunner {
    private TimeLimitConfig timeLimitConfig;
    private SoltoonInterface ai;
    private GameSocket gameSocket;
    private ClientCommunicator clientCommunicator;
    private Class<? extends Player> firstPlayerClass;

    public GameRunner(Class<SoltoonGame> soltoonGame, Class<? extends Player> myGhoul) throws Exception {
        this.timeLimitConfig = new TimeLimitConfig(10000, 1000);
        this.ai = ThreadProxy.createBean(soltoonGame, SoltoonInterface.class, timeLimitConfig);
        this.gameSocket = GameSocket.create("127.0.0.1", 9998);
        this.clientCommunicator = new ClientCommunicator(gameSocket);
        this.firstPlayerClass = myGhoul;
    }

    public static void run(Class<? extends Player> myGhoul) {
        try {
            GameRunner gameRunner = new GameRunner(SoltoonGame.class, myGhoul);
            gameRunner.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void start() throws Exception {
        initialize();

        timeLimitConfig.setTimeLimit(1000);
        timeLimitConfig.setExtraTimeLimit(500);
        for (Query query; (query = clientCommunicator.receiveQuery()) != null; ) {
            Command command = ai.handleQuery(query);
            clientCommunicator.sendCommand(command);
            System.out.println(System.currentTimeMillis());
        }
    }

    private void initialize() throws Exception {
        Query initQuery = clientCommunicator.receiveQuery();

        if (!(initQuery instanceof QueryInitialize)) {
            throw new InvalidQueryException();
        }

        timeLimitConfig.setTimeLimit(Integer.MAX_VALUE);

        Map<Long, Agent> id2ai = ai.getId2ai();

        Long firstPlayerId = Math.abs(new Random().nextLong());
        Result result = clientCommunicator.sendCommand(new CommandInitialize(firstPlayerId));

        if (result.status == Status.failure) {
            throw new FailureResultException();
        }

        Player player = (Player) firstPlayerClass.getConstructor(Long.class).newInstance(firstPlayerId);

        id2ai.put(firstPlayerId, player);

    }
}
