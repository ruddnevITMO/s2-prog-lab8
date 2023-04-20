package ru.rudXson.commands;

import ru.rudXson.base.CLIController;
import ru.rudXson.datatype.Flat;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PrintDescending implements Command{

    private CLIController c;

    public PrintDescending(CLIController c) {
        this.c = c;
    }

    @Override
    public void execute(String[] args) {
        if (c.getFlats().isEmpty()) {
            System.out.println("Collection is empty.");
            return;
        }
        PriorityQueue<Flat> sortedFlats = new PriorityQueue<>(Comparator.reverseOrder());
        sortedFlats.addAll(c.getFlats());
        System.out.println("Elements of collection in descending order:");
        for (Flat flat : sortedFlats) {
            System.out.println(flat.toString());
        }
    }

    @Override
    public String getDescription(){
        return "sort the elements in descending order";
    }
}
