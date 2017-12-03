package ir.pint.soltoon.utils.server.comminucation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class GameInputStream {
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private int timeout = -1;

    public GameInputStream(Socket socket) throws IOException {
        this.socket = socket;
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    public GameInputStream(Socket socket, ObjectInputStream objectInputStream) {
        this.socket = socket;
        this.objectInputStream = objectInputStream;
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public Object readObject() throws IOException, ClassNotFoundException {
        return readObject(0);
    }

    public Object readObject(int timeout) throws IOException, ClassNotFoundException {
        if (timeout != this.timeout) {
            socket.setSoTimeout(timeout);
            this.timeout = timeout;
        }

        try {
            return objectInputStream.readObject();
        } catch (IOException e) {
            return null;
        }
    }
}
