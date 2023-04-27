package ru.rudXson.commands;

import ru.rudXson.base.CLIController;
import ru.rudXson.datatype.Flat;

import java.util.PriorityQueue;
import java.util.Scanner;

public class Clear implements Command {

    private final PriorityQueue<Flat> collection;
    private final CLIController c;

    public Clear(CLIController c) {
        this.c = c;
        this.collection = c.getFlats();
    }

    @Override
    public void execute(String[] args, boolean fromExecute, Scanner executeScanner) {
        if (collection.isEmpty()) {
            System.out.println("The collection is already empty.");
            return;
        }
        collection.clear();
        System.out.println("The collection has been cleared.");
    }

    @Override
    public String getDescription() {
        return "Clears the collection";
    }
}

