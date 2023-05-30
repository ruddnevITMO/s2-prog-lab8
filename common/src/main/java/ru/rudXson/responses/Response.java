package ru.rudXson.responses;

import java.io.Serializable;

public abstract class Response implements Serializable {
    public final String name;
    public final String error;

    public Response(String name, String error) {
        this.name = name;
        this.error = error;
    }
}
