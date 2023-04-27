package ru.rudXson.commands;

import ru.rudXson.base.CLIController;

import java.util.Scanner;
import java.util.UUID;

public class RemoveByID implements Command {
    CLIController c;

    public RemoveByID(CLIController c) {
        this.c = c;
    }

    @Override
    public void execute(String[] args, boolean fromExecute, Scanner executeScanner) {
        c.removeFlatByID(UUID.fromString(args[1]));
    }

    @Override
    public String getDescription() {
        return "Removes element with ID specified";
    }
}