package ru.rudXson.base;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CustomDeserializer<T> implements JsonDeserializer<List<T>> {

    private final Class<T> clazz;

    public CustomDeserializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public List<T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<T> list = new ArrayList<>();
        int ignoredElements = 0;
        if (json.isJsonArray()) {
            JsonArray array = json.getAsJsonArray();
            for (JsonElement element : array) {
                try {
                    T obj = context.deserialize(element, clazz);
                    list.add(obj);
                } catch (Exception e) {
                    ignoredElements += 1;
                }
            }
            if (ignoredElements != 0) {
                System.out.print("Ignored " + ignoredElements + " element");
                if (ignoredElements == 1) {
                    System.out.print("s");
                }
                System.out.println(" from the collection. Beware!");
            }
        }
        return list;
    }
}