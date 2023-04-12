package ru.rudXson.commands;

import ru.rudXson.base.CLIController;
import ru.rudXson.datatype.Flat;

import java.util.Iterator;
import java.util.PriorityQueue;

public class Show implements Command {
    CLIController c;

    public Show(CLIController c) {
        this.c = c;
    }

    @Override
    public String getDescription() {
        return "Show all collection items";
    }

    @Override
    public void execute(String[] args) {
        System.out.println(c.getFlats());
    }
}