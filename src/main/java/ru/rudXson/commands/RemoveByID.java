package ru.rudXson.commands;

import ru.rudXson.base.CLIController;
import ru.rudXson.exceptions.NotEnoughArgsException;
import ru.rudXson.exceptions.WrongArgsException;

import java.util.Scanner;
import java.util.UUID;

public class RemoveByID implements Command {
    private final CLIController controller;

    public RemoveByID(CLIController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args, boolean fromExecute, Scanner executeScanner) throws NotEnoughArgsException, WrongArgsException {
        if (args.length < 2) throw new NotEnoughArgsException("ID is required");
        try {
            controller.removeFlatByID(UUID.fromString(args[1]));
        } catch (IllegalArgumentException e) {
            throw new WrongArgsException("You need to supply an ID, which is an UUID");
        }
    }

    @Override
    public String getDescription() {
        return "Removes element with ID specified";
    }
}