package ir.pint.soltoon.utils.shared.comminucation;

import ir.pint.soltoon.utils.shared.data.Data;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

public class ComminucationTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void comStreams() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);

        ComInputStream comInputStream = new ComInputStream(pipedInputStream);
        ComOutputStream comOutputStream = new ComOutputStream(pipedOutputStream);

        Data data = new Data();
        comOutputStream.writeObject(data);
        Data data1 = (Data) comInputStream.readObject();
        Assert.assertEquals(data, data1);
    }
}
