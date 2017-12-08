package ir.pint.soltoon.utils.shared.comminucation;

import ir.pint.soltoon.utils.shared.facades.json.SecureJson;

import java.io.*;

public class ComOutputStream extends DataOutputStream implements ObjectOutput {
    private byte[] objectSplitter = new byte[8];

    public static boolean DEBUG = false;

    public ComOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    public void writeObject(Object o) throws IOException {
        String encode = SecureJson.encode(o);
        if (DEBUG)
            System.out.println("O# " + encode);

        String classn = o.getClass().getName();
        byte[] cname = classn.getBytes();
        byte[] encodedBytes = encode.getBytes();

        writeInt(cname.length);
        write(cname);
        writeInt(encodedBytes.length);
        write(encodedBytes);
        write(objectSplitter);
    }
}
