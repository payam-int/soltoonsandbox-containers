package ir.pint.soltoon.soltoongame.server;

import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;

public class Runner {

    public static void main(String[] args) throws Exception {
        Runner.run();
    }

    private static void run() {


        Server server = Server.fromEnv();
        ServerManager serverManager = new ServerManager();
        serverManager.run();
        ResultStorage.save();
        System.out.println("Shutting down.");
        System.exit(0);
    }

}
