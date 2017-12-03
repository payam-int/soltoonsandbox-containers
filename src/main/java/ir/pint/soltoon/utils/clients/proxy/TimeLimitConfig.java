package ir.pint.soltoon.utils.clients.proxy;

public class TimeLimitConfig {
    private long timeLimit;
    private long extraTimeLimit;

    public TimeLimitConfig(long timeLimit, long extraTimeLimit) {
        this.timeLimit = timeLimit;
        this.extraTimeLimit = extraTimeLimit;
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
}
