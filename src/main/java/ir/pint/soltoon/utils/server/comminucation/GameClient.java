package ir.pint.soltoon.utils.server.comminucation;

import ir.pint.soltoon.soltoongame.shared.communication.query.QueryInitialize;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class GameClient {
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private GameInputStream gameInputStream;
    private String key;
    private long id;
    private int DEFAULT_TIMEOUT;

    public GameClient(Socket socket, ObjectOutputStream objectOutputStream, ObjectInputStream gameInputStream, String key) {
        this.socket = socket;
        this.objectOutputStream = objectOutputStream;
        this.gameInputStream = new GameInputStream(socket, gameInputStream);
        this.key = key;
    }

    public GameClient(Socket socket, ObjectOutputStream objectOutputStream, ObjectInputStream gameInputStream) {
        this.socket = socket;
        this.objectOutputStream = objectOutputStream;
        this.gameInputStream = new GameInputStream(socket, gameInputStream);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    public GameInputStream getGameInputStream() {
        return gameInputStream;
    }

    public void setGameInputStream(GameInputStream gameInputStream) {
        this.gameInputStream = gameInputStream;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public void send(Object object) throws IOException {
        objectOutputStream.reset();
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
    }

    public Object receive() throws IOException {
        try {
            return gameInputStream.readObject();
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public Object receive(int clientRecieveTime) throws SocketException {
        try {
            return gameInputStream.readObject(clientRecieveTime);
        } catch (ClassNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }
}
