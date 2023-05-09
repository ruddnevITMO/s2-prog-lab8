package ru.rudXson.responses;

import ru.rudXson.datatype.Flat;

import java.util.PriorityQueue;

public class PrintFieldDescendingTransportResponse extends Response {
    public final PriorityQueue<Flat> flats;

    public PrintFieldDescendingTransportResponse(PriorityQueue<Flat> flats, String error) {
        super("print_field_descending_transport", error);
        this.flats = flats;
    }
}
