package ir.pint.soltoon.utils.shared.facades.reflection;

import java.lang.reflect.Method;

public class PrivateCall {
    public static <T> T call(Object object, String method, Object... params) {
        if (object == null)
            return null;

        Class<?> oClass = object.getClass();
        try {
            Method m = oClass.getDeclaredMethod(method, getTypes(params));
            m.setAccessible(true);
            return ((T) m.invoke(object, params));

        } catch (Exception e) {
            return null;
        }
    }

    private static Class<?>[] getTypes(Object[] params) {
        Class<?>[] classes = new Class<?>[params.length];
        for (int i = 0; i < params.length; i++) {
            if (params[i] != null) {
                classes[i] = params[i].getClass();
            }
        }
        return classes;
    }
}
