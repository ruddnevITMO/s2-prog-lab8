package ru.rudXson.commands;

import ru.rudXson.base.SQLController;
import ru.rudXson.requests.Request;
import ru.rudXson.responses.RemoveFirstResponse;
import ru.rudXson.responses.Response;


public class RemoveFirst implements Command {
    private final SQLController controller;

    public RemoveFirst(SQLController controller) {
        this.controller = controller;
    }

    @Override
    public Response execute(Request req) {
        controller.getFlats().poll();
        return new RemoveFirstResponse(null);
    }

    @Override
    public String getDescription() {
        return "Removes first element of sorted array";
    }
}