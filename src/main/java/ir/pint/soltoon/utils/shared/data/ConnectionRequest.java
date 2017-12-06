package ir.pint.soltoon.utils.shared.data;

import ir.pint.soltoon.utils.shared.facades.json.Secure;

@Secure
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
