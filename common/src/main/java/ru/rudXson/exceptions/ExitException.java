package ru.rudXson.exceptions;

public class ExitException extends Throwable {

    public ExitException() {
        super("Program has safely exited.");
    }
}
