package ir.pint.soltoon.utils.shared.comminucation;

import ir.pint.soltoon.utils.shared.data.ConnectionRequest;
import ir.pint.soltoon.utils.shared.data.ConnectionResult;

import java.net.ServerSocket;
import java.net.Socket;

public class ComClient {
    private Comminucation comminucation;
    private ConnectionRequest connectionRequest;

    private ComClient(Comminucation comminucation, ConnectionRequest connectionRequest) {
        this.comminucation = comminucation;
        this.connectionRequest = connectionRequest;
    }

    public static Comminucation connect(ComRemoteConfig clientConfig, int timeout) {
        try {
            ServerSocket serverSocket = new ServerSocket(clientConfig.getPort());
            serverSocket.setReuseAddress(true);


            while (true) {
                Socket server = serverSocket.accept();

                server.setSoTimeout(timeout);

                ComInputStream comminucationObjectInputStream = new ComInputStream(server, server.getInputStream());
                ComOutputStream comminucationObjectOutputStream = new ComOutputStream(server.getOutputStream());

                ConnectionRequest connectionRequest = (ConnectionRequest) comminucationObjectInputStream.readObject();

                ConnectionResult connectionResult;
                if (clientConfig.getPassword().equals(connectionRequest.getPassword())) {
                    connectionResult = new ConnectionResult(true);
                    comminucationObjectOutputStream.writeObject(connectionResult);

                    Comminucation comminucation = new Comminucation(server, comminucationObjectInputStream, comminucationObjectOutputStream);
                    return comminucation;
                } else {
                    connectionResult = new ConnectionResult(false);
                    comminucationObjectOutputStream.writeObject(connectionResult);
                    server.close();
                }
            }
        } catch (Exception e) {
            return null;
        }
    }


}
