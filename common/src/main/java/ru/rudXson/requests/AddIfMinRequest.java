package ru.rudXson.requests;

import ru.rudXson.datatype.Flat;

public class AddIfMinRequest extends Request {
    public final Flat flat;
    public AddIfMinRequest(Flat flat) {
        super("add_if_min");
        this.flat = flat;
    }
}
