package ru.rudXson.commands;

import ru.rudXson.datatype.Flat;

import java.util.Iterator;
import java.util.PriorityQueue;

public class Show implements Command {
    private final PriorityQueue<Flat> flats;

    public Show(PriorityQueue<Flat> flats) {
        this.flats = flats;
    }

    @Override
    public String getDescription() {
        return "Show all collection items";
    }

    @Override
    public void execute(String[] args) {
        System.out.println(flats);
    }
}