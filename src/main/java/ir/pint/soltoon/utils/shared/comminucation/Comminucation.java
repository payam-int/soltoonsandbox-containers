package ir.pint.soltoon.utils.shared.comminucation;

import java.io.IOException;
import java.net.Socket;

public class Comminucation {
    private Socket socket;
    private ComInputStream objectInputStream;
    private ComOutputStream objectOutputStream;

    public Comminucation(Socket socket, ComInputStream objectInputStream, ComOutputStream objectOutputStream) {
        this.socket = socket;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
    }

    public Socket getSocket() {
        return socket;
    }

    public ComInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public ComOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public void close() {
        try {
            getSocket().close();
        } catch (Exception e) {
            // ignore
        }
    }
}
