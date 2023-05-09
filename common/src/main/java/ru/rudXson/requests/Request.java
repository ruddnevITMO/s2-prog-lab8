package ru.rudXson.requests;

import java.io.Serializable;

public class Request implements Serializable {
    public final String name;

    public Request(String name) {
        this.name = name;
    }
}
