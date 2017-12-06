package ir.pint.soltoon.utils.shared.comminucation;

import ir.pint.soltoon.utils.shared.facades.json.SecureJson;

import java.io.*;

public class ComOutputStream extends DataOutputStream implements ObjectOutput {
    private byte[] objectSplitter = new byte[8];

    public ComOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    public void writeObject(Object o) throws IOException {
        String encode = SecureJson.encode(o);
        byte[] bytes = encode.getBytes();

        writeInt(bytes.length);
        write(bytes);
        write(objectSplitter);
    }
}
