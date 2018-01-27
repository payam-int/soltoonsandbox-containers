package ir.pint.soltoon.utils.shared.facades.reflection;

import java.lang.reflect.Method;

public class PrivateCall {
    public static <T> T call(Object object, String method, Object... params) {
        if (object == null)
            return null;

        Class<?> oClass = object.getClass();
        Method m = null;
        try {


            for (Class currentClass = oClass; currentClass != null && m == null; currentClass = currentClass.getSuperclass())
                try {
                    m = currentClass.getDeclaredMethod(method, getTypes(params));
                } catch (Exception e) {

                }

            if (m == null) {
                Method:
                for (Method met : oClass.getDeclaredMethods()) {
                    Class<?>[] parameterTypes = met.getParameterTypes();
                    if (!met.getName().equals(method) || met.getParameterCount() != params.length && (parameterTypes.length == 0 || !parameterTypes[parameterTypes.length - 1].isArray()))
                        continue;

                    int i = -1;
                    for (Class c : parameterTypes) {
                        i++;
                        if (i >= params.length)
                            break;
                        if (params[i] == null)
                            continue;

                        if (!c.isAssignableFrom(params[i].getClass()))
                            continue Method;
                    }

                    m = met;
                    break;
                }
            }

            if (m == null)
                return null;

            m.setAccessible(true);

            if (params.length < m.getParameterCount()) {
                Object[] newParams = new Object[m.getParameterCount()];
                for (int i = 0; i < params.length; i++) {
                    newParams[i] = params[i];
                }
                params = newParams;
            }

            return ((T) m.invoke(object, params));

        } catch (Exception e) {
            e.printStackTrace();
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
