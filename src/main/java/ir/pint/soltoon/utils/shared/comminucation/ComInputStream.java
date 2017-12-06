package ir.pint.soltoon.utils.shared.comminucation;

import java.io.*;

import ir.pint.soltoon.utils.shared.facades.json.SecureJson;

// @todo timelimit and size limit
public class ComInputStream extends DataInputStream implements ObjectInput {
    private byte[] objectSplitter = new byte[8];

    public ComInputStream(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    public Object readObject() throws IOException {
        int inputSize = readInt();
        byte[] bytes = new byte[inputSize];
        readFully(bytes);

        readFully(objectSplitter);

        for (byte b : objectSplitter)
            if (b != 0) {
                close();
                throw new IOException();
            }

        String string = new String(bytes);

        Object decode = SecureJson.decode(string, Object.class);
        return decode;

    }

//    protected Object readObject(int timeout) throws IOException, ClassNotFoundException {
//        filteredInputStream.setRemainingTime(timeout * 1000000L);
//        Object o = readObject();
//        filteredInputStream.setRemainingTime(0);
//        return o;
//    }
}
