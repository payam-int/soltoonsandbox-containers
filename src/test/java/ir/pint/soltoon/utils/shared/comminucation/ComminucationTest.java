package ir.pint.soltoon.utils.shared.comminucation;

import ir.pint.soltoon.utils.shared.data.Data;
import ir.pint.soltoon.utils.shared.exceptions.NoComRemoteAvailable;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.Semaphore;

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

        ComInputStream comInputStream = new ComInputStream(null, pipedInputStream);
        ComOutputStream comOutputStream = new ComOutputStream(pipedOutputStream);

        Data data = new Data();
        comOutputStream.writeObject(data);
        Data data1 = (Data) comInputStream.readObject();
        Assert.assertEquals(data, data1);
    }

    @Test
    public void comConnection() throws Exception {


        final String password = "hello-test";
        final Data data = new Data();


        class Client implements Runnable {
            public boolean ok = false;
            @Override
            public void run() {
                ComRemoteConfig comRemoteConfig = new ComRemoteConfig(password, 8585);
                Comminucation connect = ComClient.connect(comRemoteConfig, 1000);
                System.out.println(connect);
                try {
                    Data data1 = (Data) connect.getObjectInputStream().readObject();
                    if (data1.equals(data)) {
                        ok = true;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                connect.close();


            }
        }


        Runnable client = new Client();
        Runnable server = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ComRemoteInfo remoteInfo = new ComRemoteInfo("localhost", 8585, password);
                ComServer comServer = ComServer.initiate(remoteInfo);
                Comminucation connect = null;
                try {
                    connect = comServer.connect();
                } catch (NoComRemoteAvailable noComRemoteAvailable) {
                    //ignore
                }

                try {
                    connect.getObjectOutputStream().writeObject(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                connect.close();
            }
        };

        Thread c = new Thread(client);
        Thread s = new Thread(server);
        c.start();
        s.start();
        c.join();
        Assert.assertTrue(((Client) client).ok);

    }

    @Test
    public void socketTimeout() throws Exception {


        final String password = "hello-test";
        final Data data = new Data();

        class Client implements Runnable {

            public boolean ok = false;

            @Override
            public void run() {
                ComRemoteConfig comRemoteConfig = new ComRemoteConfig(password, 8585);
                Comminucation connect = ComClient.connect(comRemoteConfig, 1000);
                try {
                    try {
                        Data data1 = (Data) connect.getObjectInputStream().readObject(1000);
                    } catch (SocketTimeoutException e) {
                        this.ok = true;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        }
        Runnable client = new Client();

        Runnable server = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ComRemoteInfo remoteInfo = new ComRemoteInfo("localhost", 8585, password);
                ComServer comServer = ComServer.initiate(remoteInfo);
                Comminucation connect = null;
                try {
                    connect = comServer.connect();
                } catch (NoComRemoteAvailable noComRemoteAvailable) {
                    //ignore
                }

                long s = System.currentTimeMillis();

                while (System.currentTimeMillis() - s < 5000) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    connect.getObjectOutputStream().writeObject(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                connect.close();
            }
        };

        Thread c = new Thread(client);
        Thread s = new Thread(server);
        c.start();
        s.start();
        c.join();

        Assert.assertTrue(((Client) client).ok);


    }
}
