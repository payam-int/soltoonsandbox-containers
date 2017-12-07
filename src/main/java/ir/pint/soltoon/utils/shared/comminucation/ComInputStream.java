package ir.pint.soltoon.utils.shared.comminucation;

import java.io.*;
import java.util.List;

import ir.pint.soltoon.utils.shared.data.Data;
import ir.pint.soltoon.utils.shared.facades.json.SecureJson;

// @todo timelimit and size limit
public class ComInputStream extends DataInputStream implements ObjectInput {
    private byte[] objectSplitter = new byte[8];

    public ComInputStream(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    public Object readObject() throws IOException {
        int classNameSize = readInt();
        byte[] cname = new byte[classNameSize];
        readFully(cname);

        int inputSize = readInt();
        byte[] inp = new byte[inputSize];
        readFully(inp);

        readFully(objectSplitter);

        for (byte b : objectSplitter)
            if (b != 0) {
                close();
                throw new IOException();
            }

        String string = new String(inp);
        String className = new String(cname);
        Object decode = null;
        try {
            decode = SecureJson.decode(string, Class.forName(className));
        } catch (ClassNotFoundException e) {
            return null;
        }
        return decode;
    }

//    protected Object readObject(int timeout) throws IOException, ClassNotFoundException {
//        filteredInputStream.setRemainingTime(timeout * 1000000L);
//        Object o = readObject();
//        filteredInputStream.setRemainingTime(0);
//        return o;
//    }
}
