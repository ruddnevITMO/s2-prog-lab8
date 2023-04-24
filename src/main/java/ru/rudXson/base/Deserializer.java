package ru.rudXson.base;

import java.io.*;
import java.util.List;
import java.util.PriorityQueue;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import ru.rudXson.datatype.Flat;

public class Deserializer {

    public static PriorityQueue<Flat> deserialize(String fileName) throws IOException {
        PriorityQueue<Flat> queue = new PriorityQueue<>();
        Gson gson = new GsonBuilder().registerTypeAdapter(new TypeToken<List<Flat>>(){}.getType(), new CustomDeserializer<>(Flat.class)).create();

        try (FileReader reader = new FileReader(fileName)) {
            List<Flat> flats = gson.fromJson(reader, new TypeToken<List<Flat>>(){}.getType());

            for (Flat flat : flats) {
                queue.offer(flat);
            }
        } catch (IOException e) {
            throw new IOException("Failed to deserialize the priority queue from file: " + fileName, e);
        }

        return queue;
    }

}