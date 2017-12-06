package ir.pint.soltoon.utils.shared.comminucation;

import java.util.ArrayList;

public class ComRemoteInfo {
    private String host;
    private int port;
    private String password;

    public ComRemoteInfo() {
    }

    public ComRemoteInfo(String host, int port, String password) {
        this.host = host;
        this.port = port;
        this.password = password;
    }

    public static ArrayList<ComRemoteInfo> createFromEnv() {
        ArrayList<ComRemoteInfo> serverClientConfigs = new ArrayList<>();

        String[] clients = System.getenv("CLIENTS").split(",");
        for (String client : clients) {
            String[] tmp = client.split("/");
            String[] tmp1 = tmp[0].split(":");

            String host = tmp1[0].trim();
            int port = Integer.parseInt(tmp1[1]);
            String password = tmp[1].trim();

            serverClientConfigs.add(new ComRemoteInfo(host, port, password));
        }

        return serverClientConfigs;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
