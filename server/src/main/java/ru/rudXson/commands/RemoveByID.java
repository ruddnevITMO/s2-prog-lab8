package ru.rudXson.commands;

import ru.rudXson.base.SQLController;
import ru.rudXson.exceptions.WrongArgsException;
import ru.rudXson.requests.RemoveByIdRequest;
import ru.rudXson.requests.Request;
import ru.rudXson.responses.RemoveByIdResponse;
import ru.rudXson.responses.Response;

public class RemoveByID implements Command {
    private final SQLController controller;

    public RemoveByID(SQLController controller) {
        this.controller = controller;
    }

    @Override
    public Response execute(Request req) {
        try {
            controller.removeFlatByID(((RemoveByIdRequest) req).id);
            return new RemoveByIdResponse(null);
        } catch (WrongArgsException e) {
            return new RemoveByIdResponse(e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "Removes element with ID specified";
    }
}