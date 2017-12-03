package ir.pint.soltoon.soltoongame.server;

public class Runner {
    
    public static void main(String[] args) throws Exception {
        int port = 9998;
        // ToDo : port ro az args begir
        Server server = new Server(port);
        ServerManager serverManager = new ServerManager(server);

        serverManager.run();
    }
    
}
