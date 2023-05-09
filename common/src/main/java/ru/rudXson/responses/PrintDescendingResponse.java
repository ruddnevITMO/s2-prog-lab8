package ru.rudXson.responses;

import ru.rudXson.datatype.Flat;

import java.util.PriorityQueue;

public class PrintDescendingResponse extends Response {
    public final PriorityQueue<Flat> flats;

    public PrintDescendingResponse(PriorityQueue<Flat> flats, String error) {
        super("print_descending", error);
        this.flats = flats;
    }
}
