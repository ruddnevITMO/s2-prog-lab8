package ru.rudXson.commands;

import ru.rudXson.requests.Request;
import ru.rudXson.responses.Response;

public interface Command {
    Response execute(Request req);
    String getDescription();
}
