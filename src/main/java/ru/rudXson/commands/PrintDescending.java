package ru.rudXson.commands;

import ru.rudXson.base.CLIController;
import ru.rudXson.datatype.Flat;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class PrintDescending implements Command{

    private final CLIController controller;

    public PrintDescending(CLIController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args, boolean fromExecute, Scanner executeScanner) {
        if (controller.getFlats().isEmpty()) {
            System.out.println("Collection is empty.");
            return;
        }
        PriorityQueue<Flat> sortedFlats = new PriorityQueue<>(Comparator.reverseOrder());
        sortedFlats.addAll(controller.getFlats());
        System.out.println("Elements of collection in descending order:");
        for (Flat flat : sortedFlats) {
            System.out.println(flat.toString());
        }
    }

    @Override
    public String getDescription(){
        return "Sorts the elements in descending order";
    }
}
