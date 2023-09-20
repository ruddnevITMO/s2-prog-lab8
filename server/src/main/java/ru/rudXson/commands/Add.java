package ru.rudXson.commands;

import ru.rudXson.base.SQLController;
import ru.rudXson.requests.AddRequest;
import ru.rudXson.requests.Request;
import ru.rudXson.responses.AddResponse;
import ru.rudXson.responses.Response;

public class Add implements Command {
    private final SQLController controller;

    public Add(SQLController controller) {
        this.controller = controller;
    }

    @Override
    public Response execute(Request req) {
        AddRequest request = (AddRequest) req;
        this.controller.addFlat(request.flat, req.getUsername());
        return new AddResponse(null);
    }

    @Override
    public String getDescription() {
        return "Adds an element to the collection";
    }


}
