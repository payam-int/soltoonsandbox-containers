package ir.pint.soltoon.utils.shared.facades.json;

import com.google.gson.*;
import ir.pint.gsonfire.GsonFireBuilder;
import ir.pint.gsonfire.PostProcessor;
import ir.pint.gsonfire.TypeSelector;

import java.lang.reflect.Method;
import java.util.*;

public class SecureJson {
    private static final Gson gson;

    private static final Set<Class> trustedClasses = new HashSet<>();
    private static final Set<Class> untrustedClasses = new HashSet<>();

    static {
        GsonFireBuilder gsonFireBuilder = new GsonFireBuilder()
                .registerPostProcessor(Object.class, new PostProcessor<Object>() {
                    @Override
                    public void postDeserialize(Object secureTransfered, JsonElement jsonElement, Gson gson) {
                        if (secureTransfered != null && secureTransfered.getClass().isAnnotationPresent(Secure.class)) {
                            try {
                                Method init = secureTransfered.getClass().getMethod("init");
                                if (init != null) {
                                    init.invoke(secureTransfered);
                                }
                            } catch (Exception e) {
                                // ignore
                            }
                        }
                    }

                    @Override
                    public void postSerialize(JsonElement jsonElement, Object secureTransfered, Gson gson) {
                        if (secureTransfered == null)
                            return;

                        if (jsonElement.isJsonObject() && SecureJson.haveSpecificObject(secureTransfered.getClass())) {
                            if (secureTransfered.getClass().isAnnotationPresent(SecureConvert.class)) {
                                Class secureas = secureTransfered.getClass().getAnnotationsByType(SecureConvert.class)[0].value();
                                jsonElement.getAsJsonObject().addProperty("_class", secureas.getName());
                            } else {
                                jsonElement.getAsJsonObject().addProperty("_class", secureTransfered.getClass().getName());
                            }

                        }
                    }
                })
                .registerTypeSelector(Object.class, new TypeSelector<Object>() {
                    @Override
                    public Class<?> getClassForElement(JsonElement jsonElement) {
                        if (!jsonElement.isJsonObject())
                            return null;

                        JsonObject jsonObject = jsonElement.getAsJsonObject();

                        if (jsonObject.has("_class")) {
                            String className = jsonObject.get("_class").getAsString();

                            Class<?> requestedClass;
                            try {
                                requestedClass = Class.forName(className);
                                if (isSecure(requestedClass))
                                    return requestedClass;
                            } catch (ClassNotFoundException e) {
                                // ignore
                            }
                        }
                        return null;
                    }

                });
        gson = gsonFireBuilder.createGsonBuilder()
                .setExclusionStrategies(new JsonExclutionStrategy())
                .serializeNulls()
                .create();

        trustedClasses.addAll(Arrays.asList(String.class, Boolean.class, Character.class, Long.class, Double.class, Integer.class, Byte.class, Float.class, Short.class, Map.class, List.class));
    }

    public static boolean haveSpecificObject(Class<?> aClass) {
        if (aClass == null)
            return false;

        return aClass.isAnnotationPresent(Secure.class) || haveSpecificObject(aClass.getInterfaces()) || haveSpecificObject(aClass.getSuperclass());
    }

    public static boolean haveSpecificObject(Class<?>[] classes) {
        if (classes == null)
            return false;

        for (Class c : classes)
            if (haveSpecificObject(c))
                return true;

        return false;
    }

    public static String encode(Object object) {
        return gson.toJson(object);
    }

    public static <T> T decode(String encode, Class<T> exampleObjectClass) {
        return gson.fromJson(encode, exampleObjectClass);
    }

    public static boolean isSecure(Class declaredClass) {
        if (declaredClass == null)
            return false;

        boolean secure = declaredClass.isPrimitive() || declaredClass.isArray() || declaredClass.isAnnotationPresent(Secure.class) || trustedClasses.contains(declaredClass);

        if (!secure && !untrustedClasses.contains(declaredClass)) {
            if (isSecure(declaredClass.getSuperclass()) || isSecure(declaredClass.getInterfaces())) {
                trustedClasses.add(declaredClass);
            } else {
                untrustedClasses.add(declaredClass);
            }
        } else if (secure) {
            return true;
        }
        return trustedClasses.contains(declaredClass);
    }

    public static boolean isSecure(Class[] classes) {
        for (Class c : classes)
            if (isSecure(c))
                return true;

        return false;
    }

    private static class JsonExclutionStrategy implements ExclusionStrategy {
        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            Class<?> declaredClass = fieldAttributes.getDeclaredClass();
            if (!SecureJson.isSecure(declaredClass)) {
                System.out.println("#: " + declaredClass);
            }
            return !SecureJson.isSecure(declaredClass);
        }

        @Override
        public boolean shouldSkipClass(Class<?> declaredClass) {
            return !SecureJson.isSecure(declaredClass);
        }
    }
}
