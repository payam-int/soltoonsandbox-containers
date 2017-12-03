package ir.pint.soltoon.utils.shared.data;

import java.io.Serializable;

public final class ConnectionResult implements Serializable {
    private boolean established;

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
