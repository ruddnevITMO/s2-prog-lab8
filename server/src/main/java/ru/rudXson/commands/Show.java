package ru.rudXson.commands;

import ru.rudXson.base.SQLController;
import ru.rudXson.requests.Request;
import ru.rudXson.responses.Response;
import ru.rudXson.responses.ShowResponse;

public class Show implements Command {
    private final SQLController controller;

    public Show(SQLController controller) {
        this.controller = controller;
    }

    @Override
    public Response execute(Request req) {
        return new ShowResponse(controller.getFlats(), null);
    }

    @Override
    public String getDescription() {
        return "Shows all collection items";
    }
}