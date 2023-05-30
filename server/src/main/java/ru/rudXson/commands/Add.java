package ru.rudXson.commands;

import ru.rudXson.base.CLIController;
import ru.rudXson.requests.AddRequest;
import ru.rudXson.requests.Request;
import ru.rudXson.responses.AddResponse;
import ru.rudXson.responses.Response;

import java.util.Scanner;

public class Add implements Command {
    private final CLIController controller;

    public Add(CLIController controller) {
        this.controller = controller;
    }

    @Override
    public Response execute(Request req) {
        AddRequest request = (AddRequest) req;
        this.controller.addFlat(request.flat);
        return new AddResponse("Element added successfully");
    }

    @Override
    public String getDescription() {
        return "Adds an element to the collection";
    }


}
