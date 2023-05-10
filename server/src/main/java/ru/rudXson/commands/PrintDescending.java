package ru.rudXson.commands;

import ru.rudXson.base.CLIController;
import ru.rudXson.datatype.Flat;
import ru.rudXson.requests.Request;
import ru.rudXson.responses.PrintDescendingResponse;
import ru.rudXson.responses.Response;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PrintDescending implements Command {

    private final CLIController controller;

    public PrintDescending(CLIController controller) {
        this.controller = controller;
    }

    @Override
    public Response execute(Request req) {
        if (controller.getFlats().isEmpty()) {
            return new PrintDescendingResponse(new Flat[0],null);
        }
        PriorityQueue<Flat> sortedFlats = new PriorityQueue<>(Comparator.reverseOrder());
        sortedFlats.addAll(controller.getFlats());

        Flat[] result = new Flat[0];
        sortedFlats.toArray(result);

        return new PrintDescendingResponse(result, null);
    }

    @Override
    public String getDescription(){
        return "Sorts the elements in descending order";
    }
}
