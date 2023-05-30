package ru.rudXson.commands;

import ru.rudXson.base.SQLController;
import ru.rudXson.exceptions.WrongArgsException;
import ru.rudXson.requests.Request;
import ru.rudXson.requests.UpdateRequest;
import ru.rudXson.responses.Response;
import ru.rudXson.responses.UpdateResponse;

public class Update implements Command {

    private final SQLController controller;

    public Update(SQLController controller) {
        this.controller = controller;
    }

    @Override
    public Response execute(Request req) {
        UpdateRequest request = (UpdateRequest) req;
        try {
            controller.replaceFlatById(request.id, request.newFlat);
            return new UpdateResponse(null);
        } catch (WrongArgsException e) {
            return new UpdateResponse(e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "Updates element with ID specified";
    }
}
