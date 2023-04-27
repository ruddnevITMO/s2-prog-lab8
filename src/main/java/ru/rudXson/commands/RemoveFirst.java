package ru.rudXson.commands;

import ru.rudXson.base.CLIController;

import java.util.Scanner;


public class RemoveFirst implements Command {
    CLIController c;

    public RemoveFirst(CLIController c) {
        this.c = c;
    }

    @Override
    public void execute(String[] args, boolean fromExecute, Scanner executeScanner) {
        c.getFlats().poll();
    }

    @Override
    public String getDescription() {
        return "Removes first element of sorted array";
    }
}