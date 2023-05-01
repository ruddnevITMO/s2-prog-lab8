package ru.rudXson.commands;

import ru.rudXson.base.CLIController;
import ru.rudXson.datatype.Flat;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Scanner;

public class PrintFieldDescendingTransport implements Command {
    private final CLIController controller;
    public PrintFieldDescendingTransport(CLIController controller){
        this.controller = controller;
    }
    @Override
    public void execute(String[] args, boolean fromExecute, Scanner scanner) throws NoPermissionException, IOException {
        PriorityQueue<Flat> flats = controller.getFlats();

        PriorityQueue<Flat> transportPriorityQueue = new PriorityQueue<>((f1, f2) -> f2.getTransport().compareTo(f1.getTransport()));
        transportPriorityQueue.addAll(flats);

        for (Flat flat : transportPriorityQueue) {
            System.out.println(flat.getTransport());
        }
    }

    @Override
    public String getDescription() {
        return "Prints the transport field for all elements in descending order.";
    }
}
