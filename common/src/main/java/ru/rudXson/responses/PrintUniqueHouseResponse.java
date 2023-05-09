package ru.rudXson.responses;

import ru.rudXson.datatype.Flat;

import java.util.PriorityQueue;

public class PrintUniqueHouseResponse extends Response {
    public final PriorityQueue<Flat> flats;

    public PrintUniqueHouseResponse(PriorityQueue<Flat> flats, String error) {
        super("print_unique_house", error);
        this.flats = flats;
    }
}

