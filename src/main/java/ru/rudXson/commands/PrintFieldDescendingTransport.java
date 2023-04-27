package ru.rudXson.commands;

import ru.rudXson.base.CLIController;
import ru.rudXson.datatype.Flat;

import java.io.IOException;
import java.util.PriorityQueue;

public class PrintFieldDescendingTransport implements Command {
    private final CLIController c;
    public PrintFieldDescendingTransport(CLIController c){
        this.c = c;
    }
    @Override
    public void execute(String[] args) throws NoPermission, IOException {
        PriorityQueue<Flat> flats = c.getFlats();

        PriorityQueue<Flat> transportPriorityQueue = new PriorityQueue<Flat>((f1, f2) -> f2.getTransport().compareTo(f1.getTransport()));
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
