package ir.pint.soltoon.utils.shared.data;

import ir.pint.soltoon.utils.shared.facades.json.Secure;

import java.io.Serializable;

@Secure
public final class ConnectionResult implements Serializable {
    private Boolean established;

    public ConnectionResult(boolean established) {
        this.established = established;
    }

    public boolean isEstablished() {
        return established;
    }

    public void setEstablished(boolean established) {
        this.established = established;
    }
}
