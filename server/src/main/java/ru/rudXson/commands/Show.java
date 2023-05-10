package ru.rudXson.commands;

import ru.rudXson.base.CLIController;
import ru.rudXson.requests.Request;
import ru.rudXson.responses.Response;
import ru.rudXson.responses.ShowResponse;

public class Show implements Command {
    private final CLIController controller;

    public Show(CLIController controller) {
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