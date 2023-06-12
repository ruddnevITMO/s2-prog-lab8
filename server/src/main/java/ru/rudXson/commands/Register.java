package ru.rudXson.commands;

import ru.rudXson.base.SQLController;
import ru.rudXson.requests.RegisterRequest;
import ru.rudXson.requests.Request;
import ru.rudXson.responses.Response;

public class Register implements Command {
    private final SQLController controller;

    public Register(SQLController controller) {
        this.controller = controller;
    }

    @Override
    public Response execute(Request req) {
        RegisterRequest request = (RegisterRequest) req;
        return controller.register(request.getUsername(), request.getPassword());
    }

    @Override
    public String getDescription() {
        return "Registers a new user for the collection manager";
    }

}