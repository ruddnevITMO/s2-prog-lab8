package ru.rudXson.exceptions;

public class WrongArgsException extends Exception {
    private final String usage;

    public WrongArgsException(String message, String usage) {
        super(message);
        this.usage = usage;
    }

    public String getUsage() {
        return usage;
    }
}
