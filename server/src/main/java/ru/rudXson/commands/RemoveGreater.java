
package ru.rudXson.commands;

import ru.rudXson.base.SQLController;
import ru.rudXson.datatype.Flat;
import ru.rudXson.exceptions.WrongArgsException;
import ru.rudXson.requests.RemoveGreaterRequest;
import ru.rudXson.requests.Request;
import ru.rudXson.responses.RemoveGreaterResponse;
import ru.rudXson.responses.Response;


public class RemoveGreater implements Command {
    private final SQLController controller;

    public RemoveGreater(SQLController controller) {
        this.controller = controller;
    }

    @Override
    public Response execute(Request req) {
        RemoveGreaterRequest request = (RemoveGreaterRequest) req;
        Flat mainFlat = request.flat;

        for (Flat flat : controller.getSelfFlats(req.getUsername())) {
            if (mainFlat.compareTo(flat) > 0) {
                try {
                    controller.removeFlatByID(flat.getId(), req.getUsername());
                } catch (WrongArgsException ignored) {
                }
            }
        }
        return new RemoveGreaterResponse(null);
    }

    @Override
    public String getDescription() {
        return "removes all elements greater than the one given";
    }
}

