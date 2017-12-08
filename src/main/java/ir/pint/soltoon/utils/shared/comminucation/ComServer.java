package ir.pint.soltoon.utils.shared.comminucation;

import ir.pint.soltoon.utils.shared.data.ConnectionRequest;
import ir.pint.soltoon.utils.shared.data.ConnectionResult;
import ir.pint.soltoon.utils.shared.exceptions.NoComRemoteAvailable;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ComServer {
    private Hashtable<ComRemoteInfo, Comminucation> comminucations;
    private List<ComRemoteInfo> remotes = new ArrayList<>();
    private ConcurrentLinkedDeque<ComRemoteInfo> connectionList;

    private ComServer(List<ComRemoteInfo> remotes) {
        this.remotes = remotes;
        this.connectionList = new ConcurrentLinkedDeque<>();
        connectionList.addAll(remotes);
    }

    public static ComServer initiate(List<ComRemoteInfo> clients) {
        ComServer server = new ComServer(clients);
        return server;
    }


    public static ComServer initiate(ComRemoteInfo remoteInfo) {
        return initiate(Arrays.asList(remoteInfo));
    }

    public static ComServer initiateFromEnv() {
        return initiate(ComRemoteInfo.createFromEnv());
    }

    public Comminucation connect() throws NoComRemoteAvailable {
        if (connectionList.size() == 0)
            throw new NoComRemoteAvailable();

        ComRemoteInfo comRemoteInfo = connectionList.pollLast();
        Comminucation connect = connect(comRemoteInfo);
        if (connect != null) {
            return connect;
        } else {
            connectionList.addFirst(comRemoteInfo);
            return null;
        }
    }

    private Comminucation connect(ComRemoteInfo remoteInfo) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(remoteInfo.getHost(), remoteInfo.getPort()));

            ComOutputStream comminucationObjectOutputStream = new ComOutputStream(socket.getOutputStream());
            ComInputStream comminucationObjectInputStream = new ComInputStream(socket, socket.getInputStream());

            ConnectionRequest connectionRequest = new ConnectionRequest(remoteInfo.getPassword());

            comminucationObjectOutputStream.writeObject(connectionRequest);

            ConnectionResult connectionResult = (ConnectionResult) comminucationObjectInputStream.readObject();
            if (connectionResult.isEstablished()) {
                return new Comminucation(socket, comminucationObjectInputStream, comminucationObjectOutputStream);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            // continue
        }
        return null;
    }

    public boolean connectAll(boolean waitForAll) throws NoComRemoteAvailable {
        return connectAll(waitForAll, 1);
    }

    public boolean connectAll(boolean waitForAll, int reties) throws NoComRemoteAvailable {
        if (comminucations == null)
            comminucations = new Hashtable<>();

        int connected = 0;

        for (int i = 0; i < reties; i++) {
            for (int j = connectionList.size(); j > 0; j--) {
                ComRemoteInfo remoteInfo = connectionList.peekLast();
                Comminucation com = connect();
                if (com != null) {
                    connected++;
                    comminucations.put(remoteInfo, com);
                }
            }
        }


        if (waitForAll)
            return connected > 0;
        else
            return connected == remotes.size();
    }

    public Hashtable<ComRemoteInfo, Comminucation> getComminucations() {
        return comminucations;
    }

    public List<ComRemoteInfo> getRemotes() {
        return remotes;
    }
}
