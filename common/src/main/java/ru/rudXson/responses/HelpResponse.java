package ru.rudXson.responses;

import java.util.HashMap;

public class HelpResponse extends Response {
    public final HashMap<String, String> descriptions;


    public HelpResponse(HashMap<String, String> descriptions, String error) {
        super("help", error);
        this.descriptions = descriptions;
    }
}
