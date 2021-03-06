package ir.pint.soltoon.utils.shared.facades.result;

import com.google.gson.Gson;
import ir.pint.soltoon.utils.shared.exceptions.EnvironmentVariableNotFound;
import ir.pint.soltoon.utils.shared.facades.json.DeSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

public class ResultStorage {
    private static Queue<Exception> exceptions = new ConcurrentLinkedQueue<>();
    private static Queue<EventLog> events = new ConcurrentLinkedQueue<>();
    private static Queue<MetaLog> metas = new ConcurrentLinkedQueue<>();
    private static ConcurrentMap<String, Object> misc = new ConcurrentHashMap();
    private static OutputStream outputStream;
    private static boolean closeStream = false;
    private static Integer resultCode = 0;
    private static ArrayList<ResultHandler> resultHandlers = new ArrayList<>();

    static {
        init();
    }

    private static void init() {
        try {
            initFromEnv();
        } catch (EnvironmentVariableNotFound environmentVariableNotFound) {
            init(System.out);
        }
    }

    public static void init(OutputStream outputStream) {
        ResultStorage.outputStream = outputStream;
    }

    public static void initFromEnv() throws EnvironmentVariableNotFound {
        String resultStorage = System.getenv("RESULT_STORAGE");
        if (resultStorage == null)
            throw new EnvironmentVariableNotFound("RESULT_STORAGE");

        File storageFile = new File(resultStorage);
        try {
            if (!storageFile.exists())
                storageFile.createNewFile();

            if (storageFile.canWrite()) {
                ResultStorage.outputStream = new FileOutputStream(storageFile);
                closeStream = true;
            }
        } catch (IOException e) {
            // ignore
        }
    }

    public static void addException(Exception e) {
        synchronized (exceptions) {
            exceptions.add(e);
        }
        for (ResultHandler resultHandler : resultHandlers)
            resultHandler.addException(e);
    }

    public static void addEvent(EventLog eventLog) {
        eventLog.setCreateTimestamp(System.currentTimeMillis());
        synchronized (events) {
            events.add(eventLog);
        }

        for (ResultHandler resultHandler : resultHandlers)
            resultHandler.addEvent(eventLog);
    }


    public static void addMeta(MetaLog metaLog) {
        synchronized (metas) {
            metas.add(metaLog);
        }
        for (ResultHandler resultHandler : resultHandlers)
            resultHandler.addMeta(metaLog);
    }

    public static void putMisc(String key, Object value) {
        synchronized (misc) {
            misc.put(key, value);
        }
        for (ResultHandler resultHandler : resultHandlers)
            resultHandler.putMisc(key, value);

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

    public static void addResultHandler(ResultHandler resultHandler) {
        resultHandlers.add(resultHandler);
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

    public static boolean flush() {
        String s = serialze();
        boolean result = true;

        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        try {
            bufferedOutputStream.write(s.getBytes());
            bufferedOutputStream.flush();
            if (closeStream)
                bufferedOutputStream.close();
        } catch (IOException e) {

        }


        for (ResultHandler resultHandler : resultHandlers)
            result &= resultHandler.flush();

        return result;
    }

    public static String serialze() {
        ResultObject resultObject = new ResultObject(exceptions, events, metas, misc, resultCode);
        return DeSerializer.serialize(resultObject);
    }

    public static void setOutputStream(OutputStream outputStream) {
        ResultStorage.outputStream = outputStream;
    }
}
