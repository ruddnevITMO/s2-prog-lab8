package ru.rudXson.commands;

import ru.rudXson.base.SQLController;
import ru.rudXson.datatype.Flat;
import ru.rudXson.datatype.Transport;
import ru.rudXson.requests.Request;
import ru.rudXson.responses.PrintFieldDescendingTransportResponse;
import ru.rudXson.responses.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class PrintFieldDescendingTransport implements Command {
    private final SQLController controller;
    public PrintFieldDescendingTransport(SQLController controller){
        this.controller = controller;
    }
    @Override
    public Response execute(Request req)  {
        if (controller.getFlats().isEmpty()) {
            return new PrintFieldDescendingTransportResponse(List.of(new Transport[0]),null);
        }

        PriorityQueue<Flat> transportPriorityQueue = new PriorityQueue<>((f1, f2) -> f2.getTransport().compareTo(f1.getTransport()));
        transportPriorityQueue.addAll(controller.getFlats());

        List<Transport> result = new ArrayList<>();
        for (Flat flat : transportPriorityQueue) {
            result.add(flat.getTransport());
        }

        return new PrintFieldDescendingTransportResponse(result, null);
    }

    @Override
    public String getDescription() {
        return "Prints the transport field for all elements in descending order.";
    }
}
