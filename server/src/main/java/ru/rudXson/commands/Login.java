package ru.rudXson.commands;

import ru.rudXson.base.SQLController;
import ru.rudXson.requests.LoginRequest;
import ru.rudXson.requests.Request;
import ru.rudXson.responses.Response;

public class Login implements Command {
    private final SQLController controller;

    public Login(SQLController controller) {
        this.controller = controller;
    }

    @Override
    public Response execute(Request req) {
        LoginRequest request = (LoginRequest) req;
        return controller.verifyCreds(request.getUsername(), request.getPassword());
    }

    @Override
    public String getDescription() {
        return "Verifies login credentials";
    }

}
