package ir.pint.soltoon.utils.shared.facades.json;

import com.google.gson.*;
import io.gsonfire.GsonFireBuilder;
import io.gsonfire.PostProcessor;
import io.gsonfire.TypeSelector;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// @todo write test and complete trustedclasses
public class SecureJson {
    private static final Gson gson;

    private static final Set<Class> trustedClasses = new HashSet<>();
    private static final Set<Class> untrustedClasses = new HashSet<>();

    static {
        GsonFireBuilder gsonFireBuilder = new GsonFireBuilder()
                .registerPostProcessor(Object.class, new PostProcessor<Object>() {
                    @Override
                    public void postDeserialize(Object secureTransfered, JsonElement jsonElement, Gson gson) {

                    }

                    @Override
                    public void postSerialize(JsonElement jsonElement, Object secureTransfered, Gson gson) {
                        if (secureTransfered.getClass().isAnnotationPresent(Secure.class)) {
                            if (secureTransfered.getClass().isAnnotationPresent(SecureConvert.class)) {
                                Class secureas = secureTransfered.getClass().getAnnotationsByType(SecureConvert.class)[0].value();
                                jsonElement.getAsJsonObject().addProperty("_class", secureas.getCanonicalName());
                            } else {
                                jsonElement.getAsJsonObject().addProperty("_class", secureTransfered.getClass().getCanonicalName());
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

        trustedClasses.addAll(Arrays.asList(String.class, Boolean.class, Character.class, Long.class, Double.class, Integer.class, Byte.class, Float.class, Short.class));
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
            if (isSecure(declaredClass.getSuperclass())) {
                trustedClasses.add(declaredClass);
            } else {
                untrustedClasses.add(declaredClass);
            }
        }
        return declaredClass.isPrimitive() || declaredClass.isArray() || declaredClass.isAnnotationPresent(Secure.class) || trustedClasses.contains(declaredClass);
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
