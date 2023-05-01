
package ru.rudXson.commands;

import ru.rudXson.base.CLIController;
import ru.rudXson.datatype.Flat;
import ru.rudXson.exceptions.NotEnoughArgsException;
import ru.rudXson.exceptions.WrongArgsException;

import java.util.Scanner;
import java.util.UUID;

public class RemoveGreater implements Command {
    private final CLIController controller;

    public RemoveGreater(CLIController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args, boolean fromExecute, Scanner executeScanner) throws NotEnoughArgsException, WrongArgsException {
        if (args.length < 2) throw new NotEnoughArgsException("ID is required");
        Flat mainFlat;
        try {
            mainFlat = controller.getFlatByID(UUID.fromString(args[1]));
        } catch (IllegalArgumentException e) {
            throw new WrongArgsException("You need to supply an ID, which is an UUID");
        }

        controller.getFlats().removeIf(flat -> mainFlat.compareTo(flat) > 0);
        System.out.println("Elements removed successfully.");

    }

    @Override
    public String getDescription() {
        return "removes all elements greater than the one given";
    }
}

