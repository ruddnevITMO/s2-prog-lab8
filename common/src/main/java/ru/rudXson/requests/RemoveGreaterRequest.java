package ru.rudXson.requests;

import ru.rudXson.datatype.Flat;

public class RemoveGreaterRequest extends Request {
    public final Flat flat;

    public RemoveGreaterRequest(Flat flat) {
        super("remove_greater");
        this.flat = flat;
    }
}
