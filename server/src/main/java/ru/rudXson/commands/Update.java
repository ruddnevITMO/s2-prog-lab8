package ru.rudXson.commands;

import ru.rudXson.base.SQLController;
import ru.rudXson.exceptions.WrongArgsException;
import ru.rudXson.requests.Request;
import ru.rudXson.requests.UpdateRequest;
import ru.rudXson.responses.Response;
import ru.rudXson.responses.UpdateResponse;

import java.sql.SQLException;
import java.util.Objects;

public class Update implements Command {

    private final SQLController controller;

    public Update(SQLController controller) {
        this.controller = controller;
    }

    @Override
    public Response execute(Request req) {
        UpdateRequest request = (UpdateRequest) req;
        try {
            if (!Objects.equals(controller.getFlatByID(request.id).getCreatedBy(), request.getUsername())) return new UpdateResponse("You are not the owner of this element, so it can't be updated.");
            controller.replaceFlatById(request.id, request.newFlat, req.getUsername());
            return new UpdateResponse(null);
        } catch (WrongArgsException | SQLException e) {
            return new UpdateResponse(e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "Updates element with ID specified";
    }
}
