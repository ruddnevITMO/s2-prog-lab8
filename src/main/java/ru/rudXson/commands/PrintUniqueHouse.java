package ru.rudXson.commands;

import ru.rudXson.base.CLIController;
import ru.rudXson.datatype.Flat;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;

public class PrintUniqueHouse implements Command {
    private final CLIController controller;
    public PrintUniqueHouse(CLIController controller) {
        this.controller = controller;
    }
    @Override
    public void execute(String[] args, boolean fromExecute, Scanner executeScanner) throws NoPermissionException, IOException {
        PriorityQueue<Flat> flats = controller.getFlats();

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
