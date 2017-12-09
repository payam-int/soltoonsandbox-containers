package ir.pint.soltoon.utils.shared.facades.result;

import org.junit.Test;

import static org.junit.Assert.*;


public class ResultHandlerTest {
    public static class TestResultHandler implements ResultHandler {
        private int methods = 0;

        @Override
        public void addEvent(EventLog eventLog) {
            methods |= 1;
        }

        @Override
        public void addMeta(MetaLog metaLog) {
            methods |= 1 << 1;
        }

        @Override
        public void putMisc(String key, Object value) {
            methods |= 1 << 2;
        }

        @Override
        public void addException(Exception exception) {
            methods |= 1 << 3;
        }

        @Override
        public boolean flush() {
            return methods == (1 << 4) - 1;
        }
    }

    @Test
    public void resultHandler() throws Exception {
        TestResultHandler testResultHandler = new TestResultHandler();
        ResultStorage.addResultHandler(testResultHandler);

        ResultStorage.addEvent(new EventLog() {
            @Override
            public long getCreateTimestamp() {
                return 0;
            }

            @Override
            public void setCreateTimestamp(long timestamp) {

            }
        });

        ResultStorage.addException(new Exception());

        ResultStorage.putMisc("Some", "Value");
        ResultStorage.addMeta(new MetaLog() {

        });

        assertTrue(testResultHandler.flush());
    }
}
