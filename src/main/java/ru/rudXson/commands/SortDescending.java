package ru.rudXson.commands;

import ru.rudXson.base.CLIController;
import ru.rudXson.datatype.Flat;
import ru.rudXson.exceptions.NoPermission;

import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;

public class SortDescending implements Command{
    private PriorityQueue<Flat> flats;

    public SortDescending(PriorityQueue<Flat> flats) {
        this.flats = flats;
    }

    @Override
    public void execute(String[] args) {
        if (flats.isEmpty()) {
            System.out.println("Collection is empty.");
            return;
        }
        PriorityQueue<Flat> sortedFlats = new PriorityQueue<>(Comparator.reverseOrder());
        sortedFlats.addAll(flats);
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
