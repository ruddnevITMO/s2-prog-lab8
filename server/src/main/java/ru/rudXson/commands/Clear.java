package ru.rudXson.commands;

import ru.rudXson.base.CLIController;
import ru.rudXson.datatype.Flat;
import ru.rudXson.requests.Request;
import ru.rudXson.responses.ClearResponse;
import ru.rudXson.responses.Response;

import java.util.PriorityQueue;

public class Clear implements Command {

    private final PriorityQueue<Flat> collection;

    public Clear(CLIController controller) {
        this.collection = controller.getFlats();
    }

    @Override
    public Response execute(Request req) {
        if (collection.isEmpty()) {
            return new ClearResponse(null);
        }
        collection.clear();
        return new ClearResponse(null);
    }

    @Override
    public String getDescription() {
        return "Clears the collection";
    }
}

