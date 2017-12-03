package ir.pint.soltoon.utils.server.comminucation;

import ir.pint.soltoon.soltoongame.shared.communication.Comminucation;
import ir.pint.soltoon.utils.server.exceptions.ClientNotFoundException;
import ir.pint.soltoon.utils.shared.data.ConnectionResult;
import ir.pint.soltoon.utils.shared.exceptions.SoltoonContainerException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameSocketServer {
    private ServerSocket serverSocket;
    private Map<String, Integer> keys;

    public GameSocketServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.serverSocket.setReuseAddress(true);
        this.serverSocket.setSoTimeout(Comminucation.SERVER_ACCEPT_TIMEOUT);
        this.keys = null;
    }

    public GameSocketServer(int port, Map<String, Integer> keys) throws IOException {
        this(port);
        this.keys = keys;
    }

    public static GameSocketServer createFromEnv() throws Exception {
        int serverPort = Integer.parseInt(System.getenv("SERVER_PORT"));

        String keysString = System.getenv("CLIENT_KEYS");

        boolean secure = false;
        ConcurrentHashMap<String, Integer> keysTable = null;
        if (keysString != null) {
            secure = true;

            String[] keys = keysString.split(",");
            keysTable = new ConcurrentHashMap<String, Integer>();
            for (String k : keys)
                keysTable.put(k, 1);
        }


        if (secure)
            return new GameSocketServer(serverPort, keysTable);
        else
            return new GameSocketServer(serverPort);
    }

    public GameClient getClient() throws IOException, ClassNotFoundException, SoltoonContainerException {
        Socket accept = serverSocket.accept();

        ObjectInputStream objectInputStream = new ObjectInputStream(accept.getInputStream());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(accept.getOutputStream());

        if (keys != null) {
            String key = null;
            try {
                key = (String) objectInputStream.readObject();
            } catch (Exception e) {
                // ignore
            }

            if (keys.getOrDefault(key, 0) > 0) {
                keys.put(key, keys.get(key) - 1);
                objectOutputStream.writeObject(new ConnectionResult(true));
                objectOutputStream.flush();
                return new GameClient(accept, objectOutputStream, objectInputStream, key);
            } else {
                throw new ClientNotFoundException(key, accept.getInetAddress().getHostAddress());
            }
        } else {
            objectOutputStream.writeObject(new ConnectionResult(true));
            objectOutputStream.flush();
            return new GameClient(accept, objectOutputStream, objectInputStream);
        }
    }



}
