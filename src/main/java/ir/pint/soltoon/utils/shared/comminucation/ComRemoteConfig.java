package ir.pint.soltoon.utils.shared.comminucation;

import ir.pint.soltoon.utils.shared.facades.json.Secure;

@Secure
public class ComRemoteConfig {
    private String password;
    private int port;

    public ComRemoteConfig() {
    }

    public ComRemoteConfig(String password, int port) {
        this.password = password;
        this.port = port;
    }

    public static ComRemoteConfig fromEnv() {
        try {
            int port = Integer.parseInt(System.getenv("PORT"));
            String password = System.getenv("PASSWORD").trim();

            return new ComRemoteConfig(password, port);
        } catch (Exception e) {
            return null;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
