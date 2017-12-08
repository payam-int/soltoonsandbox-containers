package ir.pint.soltoon.utils.shared.facades.json;

import org.junit.Before;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class SecureJsonTest {

    @Secure
    private static class SecureChildClass {
    }

    @Secure
    private static class SecureClass {
        String string = "test";
        int integer = 1;
        Integer integerClass = 10;
        Integer nullobject = null;
        Object illegalObject = new Object();
        List<String> list = Arrays.asList("first", "second");
        Map<String, String> map;
        SecureClass secureClass = null;

        {
            map = new Hashtable<>();
            map.put("first", "firstvalue");
            map.put("second", "secondvalue");
        }


        public SecureClass() {
            this(true);
        }

        public SecureClass(boolean b) {
            if (b) {
                secureClass = new SecureClass(false);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SecureClass)) return false;

            SecureClass that = (SecureClass) o;

            if (integer != that.integer) return false;
            if (!string.equals(that.string)) return false;
            if (!integerClass.equals(that.integerClass)) return false;
            if (nullobject != null ? !nullobject.equals(that.nullobject) : that.nullobject != null) return false;
            if (!list.equals(that.list)) return false;
            if (!map.equals(that.map)) return false;
            return secureClass != null ? secureClass.equals(that.secureClass) : that.secureClass == null;
        }


        @Override
        public String toString() {
            return "SecureClass{" +
                    "string='" + string + '\'' +
                    ", integer=" + integer +
                    ", integerClass=" + integerClass +
                    ", nullobject=" + nullobject +
                    ", illegalObject=" + illegalObject +
                    ", list=" + list +
                    ", map=" + map +
                    ", secureClass=" + secureClass +
                    '}';
        }
    }

    private static class InsecureClass {

    }

    private static class SecureByInterfaceClass implements SecureInterface {

    }

    private static class SecureBySuperClass extends SecureClass {

    }

    @Secure
    private static interface SecureInterface {

    }


    private static interface SecureByParentInterface extends SecureInterface {

    }

    private SecureClass testSecureClass;

    @Before
    public void setUp() throws Exception {
        testSecureClass = new SecureClass();
    }


    @org.junit.Test
    public void isSecure() throws Exception {
        System.out.println("IsSecure Time:");

        long t = System.nanoTime();
        assertEquals(true, SecureJson.isSecure(SecureClass.class));
        System.out.println("Operation: " + (System.nanoTime() - t));
        t = System.nanoTime();
        assertEquals(true, SecureJson.isSecure(SecureChildClass.class));
        System.out.println("Operation: " + (System.nanoTime() - t));
        t = System.nanoTime();
        assertEquals(false, SecureJson.isSecure(InsecureClass.class));
        System.out.println("Operation: " + (System.nanoTime() - t));
        t = System.nanoTime();
        assertEquals(true, SecureJson.isSecure(SecureByInterfaceClass.class));
        System.out.println("Operation: " + (System.nanoTime() - t));
        t = System.nanoTime();
        assertEquals(true, SecureJson.isSecure(SecureBySuperClass.class));
        System.out.println("Operation: " + (System.nanoTime() - t));
        t = System.nanoTime();
        assertEquals(true, SecureJson.isSecure(SecureByParentInterface.class));
        System.out.println("Operation: " + (System.nanoTime() - t));
        t = System.nanoTime();
        assertEquals(true, SecureJson.isSecure(SecureClass.class));
        System.out.println("(R) Operation: " + (System.nanoTime() - t));
        t = System.nanoTime();
        assertEquals(true, SecureJson.isSecure(SecureClass.class));
        System.out.println("(R) Operation: " + (System.nanoTime() - t));
        t = System.nanoTime();
        assertEquals(true, SecureJson.isSecure(SecureByParentInterface.class));
        System.out.println("(R) Operation: " + (System.nanoTime() - t));
        t = System.nanoTime();
    }

    @org.junit.Test
    public void encode() throws Exception {
        assertEquals("{\"string\":\"test\",\"integer\":1,\"integerClass\":10,\"nullobject\":null,\"list\":[\"first\",\"second\"],\"map\":{\"second\":\"secondvalue\",\"first\":\"firstvalue\"},\"secureClass\":{\"string\":\"test\",\"integer\":1,\"integerClass\":10,\"nullobject\":null,\"list\":[\"first\",\"second\"],\"map\":{\"second\":\"secondvalue\",\"first\":\"firstvalue\"},\"secureClass\":null,\"_class\":\"ir.pint.soltoon.utils.shared.facades.json.SecureJsonTest$SecureClass\"},\"_class\":\"ir.pint.soltoon.utils.shared.facades.json.SecureJsonTest$SecureClass\"}", SecureJson.encode(testSecureClass));
    }

    @org.junit.Test
    public void decode() throws Exception {
        SecureClass decode = SecureJson.decode("{\"string\":\"test\",\"integer\":1,\"integerClass\":10,\"nullobject\":null,\"list\":[\"first\",\"second\"],\"map\":{\"second\":\"secondvalue\",\"first\":\"firstvalue\"},\"secureClass\":{\"string\":\"test\",\"integer\":1,\"integerClass\":10,\"nullobject\":null,\"list\":[\"first\",\"second\"],\"map\":{\"second\":\"secondvalue\",\"first\":\"firstvalue\"},\"secureClass\":null,\"_class\":\"ir.pint.soltoon.utils.shared.facades.json.SecureJsonTest$SecureClass\"},\"_class\":\"ir.pint.soltoon.utils.shared.facades.json.SecureJsonTest$SecureClass\"}", SecureClass.class);

        System.out.println(testSecureClass);
        System.out.println(decode);

        assertTrue(decode.equals(testSecureClass));
    }

    @Secure
    public static class Data {
        private String text = "hello";

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Data)) return false;

            Data data = (Data) o;

            return text != null ? text.equals(data.text) : data.text == null;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "text='" + text + '\'' +
                    '}';
        }
    }
}