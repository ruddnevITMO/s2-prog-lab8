package ru.rudXson.commands;

import ru.rudXson.base.CLIController;
import ru.rudXson.exceptions.WrongArgsException;

import java.util.Scanner;
import java.util.UUID;

public class RemoveByID implements Command {
    CLIController c;

    public RemoveByID(CLIController c) {
        this.c = c;
    }

    @Override
    public void execute(String[] args, boolean fromExecute, Scanner executeScanner) {
        try {
            c.removeFlatByID(UUID.fromString(args[1]));
        } catch (IllegalArgumentException e) {
            System.out.println("You need to supply an ID, which is an UUID");
        } catch (WrongArgsException e) {
            System.out.println(e.getMessage());;
        }
    }

    @Override
    public String getDescription() {
        return "Removes element with ID specified";
    }
}