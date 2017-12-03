package ir.pint.soltoon.utils.shared.data;

public final class ConnectionResult {
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
