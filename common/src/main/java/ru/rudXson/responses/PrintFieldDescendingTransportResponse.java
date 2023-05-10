package ru.rudXson.responses;

import ru.rudXson.datatype.Flat;


public class PrintFieldDescendingTransportResponse extends Response {
    public final Flat[] flats;

    public PrintFieldDescendingTransportResponse(Flat[] flats, String error) {
        super("print_field_descending_transport", error);
        this.flats = flats;
    }
}
