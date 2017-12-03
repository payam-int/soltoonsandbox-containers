package ir.pint.soltoon.clients.container.proxy;

public class TimeLimitConfig {
    private long timeLimit = -1;

    public TimeLimitConfig(long timeLimit) {
        this.timeLimit = timeLimit;
    }

    public long getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(long timeLimit) {
        this.timeLimit = timeLimit;
    }
}
