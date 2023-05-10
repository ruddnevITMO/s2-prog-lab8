package ru.rudXson.responses;

import ru.rudXson.datatype.Flat;


public class PrintDescendingResponse extends Response {
    public final Flat[] flats;

    public PrintDescendingResponse(Flat[] flats, String error) {
        super("print_descending", error);
        this.flats = flats;
    }
}
