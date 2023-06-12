package ru.rudXson.commands;

import ru.rudXson.base.SQLController;
import ru.rudXson.requests.Request;
import ru.rudXson.responses.Response;
import ru.rudXson.responses.ClearResponse;


public class Clear implements Command {

    private final SQLController controller;

    public Clear(SQLController controller) {
        this.controller = controller;
    }

    @Override
    public Response execute(Request req) {
        controller.clearCollection();
        return new ClearResponse(null);
    }

    @Override
    public String getDescription() {
        return "Clears the collection";
    }
}

