package ir.pint.soltoon.utils.shared.facades.result;

public class DefaultEventLog implements EventLog {
    private long createTimestamp;

    @Override
    public long getCreateTimestamp() {
        return createTimestamp;
    }

    @Override
    public void setCreateTimestamp(long createTimestamp) {
        this.createTimestamp = createTimestamp;
    }
}
