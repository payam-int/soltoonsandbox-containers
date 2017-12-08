package ir.pint.soltoon.utils.shared.comminucation;

import java.io.*;
import java.net.Socket;

import ir.pint.soltoon.utils.shared.facades.json.SecureJson;

// @todo changable inputSize limit
public class ComInputStream extends DataInputStream implements ObjectInput {
    private byte[] objectSplitter = new byte[8];
    private Socket socket;
    private int timeout = -1;
    public static boolean DEBUG = false;

    private long maxInputSize = 1024 * 1024 * 10;

    public ComInputStream(Socket socket, InputStream inputStream) {
        super(inputStream);
        this.socket = socket;
    }

    @Override
    public Object readObject() throws IOException {
        if (timeout != 0 && socket != null)
            socket.setSoTimeout(0);
        timeout = 0;

        return readObject0();
    }

    private Object readObject0() throws IOException {
        int classNameSize = readInt();
        byte[] cname = new byte[classNameSize];

        readFully(cname);

        int inputSize = readInt();

        if (inputSize > maxInputSize)
            return null;

        byte[] inp = new byte[inputSize];
        readFully(inp);

        readFully(objectSplitter);

        for (byte b : objectSplitter)
            if (b != 0) {
                close();
                throw new IOException();
            }

        String string = new String(inp);
        if (DEBUG)
            System.out.println("I# " + string);

        String className = new String(cname);
        Object decode = null;
        try {
            decode = SecureJson.decode(string, Class.forName(className));
        } catch (ClassNotFoundException e) {
            return null;
        }

        return decode;
    }


    public Object readObject(int timeout) throws IOException {


        if (this.timeout != timeout && socket != null) {
            socket.setSoTimeout(timeout);

        }
        this.timeout = timeout;


        return readObject0();

    }
}
