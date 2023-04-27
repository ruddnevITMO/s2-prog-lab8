package ru.rudXson.commands;

import ru.rudXson.base.CLIController;

import java.util.Scanner;

public class Show implements Command {
    CLIController c;

    public Show(CLIController c) {
        this.c = c;
    }

    @Override
    public void execute(String[] args, boolean fromExecute, Scanner executeScanner) {
        System.out.println(c.getFlats());
    }

    @Override
    public String getDescription() {
        return "Shows all collection items";
    }
}