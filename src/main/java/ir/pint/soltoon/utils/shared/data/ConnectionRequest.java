package ir.pint.soltoon.utils.shared.data;

public final class ConnectionRequest {
    private String password;

    public ConnectionRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
