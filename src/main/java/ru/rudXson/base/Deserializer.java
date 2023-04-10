package ru.rudXson.base;

import java.io.*;
import java.util.PriorityQueue;

import com.google.gson.*;
import ru.rudXson.datatype.Flat;

public class Deserializer {

    public static PriorityQueue<Flat> deserialize(String fileName) throws IOException {
//        Gson gson = new GsonBuilder().registerTypeAdapter(MyEnum[].class, new MyEnumArrayDeserializer().create();
        Gson gson = new Gson();
        PriorityQueue<Flat> queue = new PriorityQueue<>();

        try (FileReader reader = new FileReader(fileName)) {
            Flat[] flats = gson.fromJson(reader, Flat[].class);
            for (Flat flat : flats) {
                queue.offer(flat);
            }
        } catch (IOException e) {
            throw new IOException("Failed to deserialize the priority queue from file: " + fileName, e);
        }

        return queue;
    }

}