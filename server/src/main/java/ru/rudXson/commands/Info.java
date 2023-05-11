package ru.rudXson.commands;

import ru.rudXson.base.CLIController;
import ru.rudXson.datatype.Flat;
import ru.rudXson.requests.Request;
import ru.rudXson.responses.InfoResponse;
import ru.rudXson.responses.Response;

import java.util.PriorityQueue;

public class Info implements Command {
    private final CLIController controller;

    public Info(CLIController controller) {
        this.controller = controller;
    }

    @Override
    public Response execute(Request req) {
        PriorityQueue<Flat> flats = controller.getFlats();

        String result = "";
        result += "\tInformation about collection:\n";
            result += "Created at " + this.controller.getCreationDate() + '\n';

        result += "Collection type is " + flats.getClass().getSimpleName() + '\n';
        result += "Amount of items stored in - " + flats.size();

        return new InfoResponse(result);
    }
    @Override
    public String getDescription(){
        return "Shows info about collection";
    }
}

