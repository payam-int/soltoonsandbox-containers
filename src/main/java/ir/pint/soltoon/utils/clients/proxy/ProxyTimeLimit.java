package ir.pint.soltoon.utils.clients.proxy;

public class ProxyTimeLimit {
    private long timeLimit = 0;
    private long extraTimeLimit = 0;
    private TimeoutPolicy timeoutPolicy = TimeoutPolicy.NULL_RETURN;


    public ProxyTimeLimit(long timeLimit, long extraTimeLimit) {
        this.timeLimit = timeLimit;
        this.extraTimeLimit = extraTimeLimit;
    }

    public ProxyTimeLimit(long timeLimit, long extraTimeLimit, TimeoutPolicy timeoutPolicy) {
        this.timeLimit = timeLimit;
        this.extraTimeLimit = extraTimeLimit;
        this.timeoutPolicy = timeoutPolicy;
    }

    public ProxyTimeLimit() {
    }

    public long getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(long timeLimit) {
        this.timeLimit = timeLimit;
    }

    public long getExtraTimeLimit() {
        return extraTimeLimit;
    }

    public void setExtraTimeLimit(long extraTimeLimit) {
        this.extraTimeLimit = extraTimeLimit;
    }

    public TimeoutPolicy getTimeoutPolicy() {
        return timeoutPolicy;
    }

    public void setTimeoutPolicy(TimeoutPolicy timeoutPolicy) {
        this.timeoutPolicy = timeoutPolicy;
    }
}
