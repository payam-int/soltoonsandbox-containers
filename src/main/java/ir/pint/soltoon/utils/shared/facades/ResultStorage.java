package ir.pint.soltoon.utils.shared.facades;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

public class ResultStorage {
    private static Queue<Exception> exceptions = new ConcurrentLinkedQueue<>();
    private static Queue<EventLog> events = new ConcurrentLinkedQueue<>();
    private static Queue<MetaLog> metas = new ConcurrentLinkedQueue<>();
    private static ConcurrentMap<String, Object> misc = new ConcurrentHashMap();
    private static OutputStream outputStream = System.out;
    private static Integer resultCode = 0;


    public static void init(OutputStream outputStream) {
        ResultStorage.outputStream = outputStream;
    }

    public static void addException(Exception e) {
        exceptions.add(e);
    }

    public static void addEvent(EventLog eventLog) {
        eventLog.setCreateTimestamp(System.currentTimeMillis());
        events.add(eventLog);
    }


    public static void addMeta(MetaLog metaLog) {
        metas.add(metaLog);
    }

    public static void putMisc(String key, Object value) {
        misc.put(key, value);
    }

    public static Object getMisc(String key, Object defaultValue) {
        return misc.getOrDefault(key, defaultValue);
    }

    public static Object getMisc(String key) {
        return misc.get(key);
    }

    public static Integer getResultCode() {
        return resultCode;
    }

    public static void setResultCode(Integer resultCode) {
        ResultStorage.resultCode = resultCode;
    }

    private static class ResultObject {
        private Integer resultCode;
        private Queue<EventLog> events;
        private Queue<MetaLog> metas;
        private ConcurrentMap<String, Object> misc;
        private Queue<Exception> exceptions;

        public ResultObject(Queue<Exception> exceptions, Queue<EventLog> events, Queue<MetaLog> metas, ConcurrentMap<String, Object> misc, Integer resultCode) {
            this.exceptions = exceptions;
            this.events = events;
            this.metas = metas;
            this.misc = misc;
            this.resultCode = resultCode;
        }

        public Queue<Exception> getExceptions() {
            return exceptions;
        }

        public Queue<EventLog> getEvents() {
            return events;
        }

        public Queue<MetaLog> getMetas() {
            return metas;
        }

        public ConcurrentMap<String, Object> getMisc() {
            return misc;
        }

        public Integer getResultCode() {
            return resultCode;
        }
    }

    public static void save()  {
        ObjectMapper objectMapper = new ObjectMapper();
        ResultObject resultObject = new ResultObject(exceptions, events, metas, misc, resultCode);
        String s = null;
        try {
            s = objectMapper.writeValueAsString(resultObject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        try {
            bufferedOutputStream.write(s.getBytes());
            bufferedOutputStream.flush();
        } catch (IOException e) {
            System.exit(-2);
        }
    }
}
