package ru.rudXson.commands;

import ru.rudXson.base.CLIController;
import ru.rudXson.datatype.Flat;
import ru.rudXson.requests.Request;
import ru.rudXson.responses.PrintDescendingResponse;
import ru.rudXson.responses.Response;

import java.util.*;

public class PrintDescending implements Command {

    private final CLIController controller;

    public PrintDescending(CLIController controller) {
        this.controller = controller;
    }

    @Override
    public Response execute(Request req) {
        if (controller.getFlats().isEmpty()) {
            return new PrintDescendingResponse(new ArrayList<>(),null);
        }
        PriorityQueue<Flat> sortedFlats = new PriorityQueue<>(Comparator.reverseOrder());
        sortedFlats.addAll(controller.getFlats());
        List<Flat> result = new ArrayList<>(sortedFlats.size());
        while (!sortedFlats.isEmpty()) {
            result.add(sortedFlats.poll());
        }

        return new PrintDescendingResponse(result, null);
    }

    @Override
    public String getDescription(){
        return "Sorts the elements in descending order";
    }
}
