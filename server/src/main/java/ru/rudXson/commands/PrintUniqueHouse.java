package ru.rudXson.commands;

import ru.rudXson.base.CLIController;
import ru.rudXson.datatype.Flat;
import ru.rudXson.requests.Request;
import ru.rudXson.responses.PrintUniqueHouseResponse;
import ru.rudXson.responses.Response;

import java.util.HashSet;

public class PrintUniqueHouse implements Command {
    private final CLIController controller;
    public PrintUniqueHouse(CLIController controller) {
        this.controller = controller;
    }
    @Override
    public Response execute(Request req) {
        HashSet<String> uniqueHouses = new HashSet<>();

        for (Flat flat : controller.getFlats()) {
            uniqueHouses.add(flat.getHouse().toString());
        }


        return new PrintUniqueHouseResponse(uniqueHouses, null);
    }

    @Override
    public String getDescription() {
        return "Prints unique values of the house field for all elements in the collection.";
    }
}
