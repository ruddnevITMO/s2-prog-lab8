package ru.rudXson.commands;

import ru.rudXson.exceptions.ExitException;
import ru.rudXson.requests.Request;
import ru.rudXson.responses.Response;

public interface Command {
    Response execute(Request req) throws ExitException;
    String getDescription();
}
