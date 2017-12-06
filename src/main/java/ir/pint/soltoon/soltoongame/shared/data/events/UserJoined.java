package ir.pint.soltoon.soltoongame.shared.data.events;

import ir.pint.soltoon.utils.shared.facades.result.EventLog;

public class UserJoined implements EventLog {
    private long id;
    private String key;
    private String host;
    private long timestamp;

    public UserJoined(String host, String key, long timestamp, long id) {
        this.host = host;
        this.key = key;
        this.timestamp = timestamp;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public long getCreateTimestamp() {
        return timestamp;
    }

    @Override
    public void setCreateTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
