package ru.rudXson.commands;

import ru.rudXson.base.CLIController;
import ru.rudXson.datatype.Flat;

import java.io.IOException;
import java.util.HashSet;
import java.util.PriorityQueue;

public class PrintUniqueHouse implements Command {
    private final CLIController c;
    public PrintUniqueHouse(CLIController c) {
        this.c = c;
    }
    @Override
    public void execute(String[] args) throws NoPermission, IOException {
        PriorityQueue<Flat> flats = c.getFlats();

        HashSet<String> uniqueHouses = new HashSet<>();

        for (Flat flat : flats) {
            uniqueHouses.add(flat.getHouse().toString());
        }

        System.out.println("Unique house values:");
        for (String house : uniqueHouses) {
            System.out.println(house);
        }
    }

    @Override
    public String getDescription() {
        return "Prints unique values of the house field for all elements in the collection.";
    }
}
