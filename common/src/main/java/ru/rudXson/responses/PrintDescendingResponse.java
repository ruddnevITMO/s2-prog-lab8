package ru.rudXson.responses;

import ru.rudXson.datatype.Flat;

import java.util.List;


public class PrintDescendingResponse extends Response {
    public final List<Flat> flats;

    public PrintDescendingResponse(List<Flat> flats, String error) {
        super("print_descending", error);
        this.flats = flats;
    }
}
