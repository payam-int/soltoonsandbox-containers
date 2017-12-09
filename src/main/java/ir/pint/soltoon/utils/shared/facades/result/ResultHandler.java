package ir.pint.soltoon.utils.shared.facades.result;

public interface ResultHandler {
    void addEvent(EventLog eventLog);
    void addMeta(MetaLog metaLog);
    void putMisc(String key, Object value);
    void addException(Exception exception);
    boolean flush();
}
