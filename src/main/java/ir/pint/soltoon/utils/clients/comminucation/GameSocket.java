package ir.pint.soltoon.utils.clients.comminucation;


import ir.pint.soltoon.utils.shared.data.ConnectionResult;
import ir.pint.soltoon.utils.shared.exceptions.ConnectionNotEstablishedException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


// @todo EnvServerConf, ServerConf, DefaultConnectionConfig
public class GameSocket {

    private String host;
    private int port;
    private String key;

    private Socket socket;

    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;


    private GameSocket(String host, int port, String key) {
        this.host = host;
        this.port = port;
        this.key = key;
    }

    public static GameSocket createFromEnv() throws Exception {
        String host = System.getenv("CLIENT_HOST");
        int port = Integer.parseInt(System.getenv("CLIENT_Port"));
        String key = System.getenv("CLIENT_KEY");
        GameSocket gameSocket = new GameSocket(host, port, key);
        gameSocket.init();

        return gameSocket;
    }


    public static GameSocket create(String host, int port) throws Exception {
        return create(host, port, null);
    }

    public static GameSocket create(String host, int port, String key) throws Exception {

        GameSocket gameSocket = new GameSocket(host, port, key);
        gameSocket.init();

        return gameSocket;
    }

    private void init() throws Exception {
        socket = new Socket(host, port);
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        if (key != null)
            objectOutputStream.writeObject(key);

        Object o = objectInputStream.readObject();
        if (o instanceof String) {
            objectOutputStream.writeObject("hi");
            return;
        }

        ConnectionResult result = (ConnectionResult) o;

        if (!result.isEstablished())
            throw new ConnectionNotEstablishedException();
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }
}
