package ru.rudXson.responses;

import ru.rudXson.datatype.Flat;

import java.util.HashSet;
import java.util.PriorityQueue;

public class PrintUniqueHouseResponse extends Response {
    HashSet<String> uniqueHouses;

    public PrintUniqueHouseResponse(HashSet<String> uniqueHouses, String error) {
        super("print_unique_house", error);
        this.uniqueHouses = uniqueHouses;
    }
}

