package ir.pint.soltoon.utils.shared.facades.json;

import com.google.gson.*;
import ir.pint.gsonfire.GsonFireBuilder;
import ir.pint.gsonfire.PostProcessor;
import ir.pint.gsonfire.TypeSelector;

import java.lang.reflect.Method;
import java.util.Map;

public class DeSerializer {
    private static final Gson gson;

    static {

        GsonFireBuilder gsonFireBuilder = new GsonFireBuilder()
                .registerPostProcessor(Object.class, new PostProcessor<Object>() {
                    @Override
                    public void postDeserialize(Object deserializedObject, JsonElement jsonElement, Gson gson) {
                        if (deserializedObject != null) {
                            try {
                                Method afterDeserialze = deserializedObject.getClass().getDeclaredMethod("afterDeserialize");
                                if (afterDeserialze != null) {
                                    if (!afterDeserialze.isAccessible())
                                        afterDeserialze.setAccessible(true);

                                    afterDeserialze.invoke(deserializedObject);
                                }
                            } catch (Exception e) {
                            }
                        }
                    }

                    @Override
                    public void postSerialize(JsonElement jsonElement, Object objectToSerialize, Gson gson) {
                        if (objectToSerialize == null)
                            return;

                        if (jsonElement.isJsonObject() && !objectToSerialize.getClass().isAnnotationPresent(NoTypeInSerialization.class) && !Map.class.isAssignableFrom(objectToSerialize.getClass())) {
                            Class objectClass = objectToSerialize.getClass();
                            jsonElement.getAsJsonObject().addProperty("_class", objectClass.getName());
                        }

                    }
                }).registerTypeSelector(Object.class, new TypeSelector<Object>() {
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
                                if (requestedClass.isAnnotationPresent(SerializeAs.class)) {
                                    SerializeAs serializeAs = requestedClass.getAnnotationsByType(SerializeAs.class)[0];
                                    return serializeAs.value();
                                }
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
    }

    public static String serialize(Object object) {
        return gson.toJson(object);
    }

    public static <T> T deserialize(String encode, Class<T> exampleObjectClass) {
        return gson.fromJson(encode, exampleObjectClass);
    }

    private static class JsonExclutionStrategy implements ExclusionStrategy {
        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            Class<?> declaredClass = fieldAttributes.getDeclaredClass();
            if (declaredClass.isAnnotationPresent(InsecureForDeserialization.class)) {
                return true;
            }

            if (fieldAttributes.getDeclaringClass().isAnnotationPresent(SerializeAs.class)) {
                Class serialClass = fieldAttributes.getDeclaringClass().getAnnotation(SerializeAs.class).value();
                try {
                    serialClass.getField(fieldAttributes.getName());
                } catch (NoSuchFieldException e) {
                    return true;
                }
            }

            return false;
        }

        @Override
        public boolean shouldSkipClass(Class<?> declaredClass) {
            if (declaredClass.isAnnotationPresent(InsecureForDeserialization.class)) {
                return true;
            }
            return false;
        }
    }
}
