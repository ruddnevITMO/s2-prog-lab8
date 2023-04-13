package ru.rudXson.commands;

import ru.rudXson.base.CLIController;


public class RemoveFirst implements Command {
    CLIController c;

    public RemoveFirst(CLIController c) {
        this.c = c;
    }

    @Override
    public String getDescription() {
        return "Remove first element of sorted array";
    }

    @Override
    public void execute(String[] args) {
        c.getFlats().poll();
    }
}