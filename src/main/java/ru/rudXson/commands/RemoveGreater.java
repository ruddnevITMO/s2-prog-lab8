
package ru.rudXson.commands;

import ru.rudXson.base.CLIController;
import ru.rudXson.datatype.Flat;
import ru.rudXson.exceptions.NotEnoughArgsException;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.UUID;

public class RemoveGreater implements Command {
    private final CLIController c;

    public RemoveGreater(CLIController c) {
        this.c = c;
    }

    @Override
    public void execute(String[] args, boolean fromExecute, Scanner executeScanner) throws NotEnoughArgsException {
        if (args.length < 2) throw new NotEnoughArgsException("ID is required");
        UUID id = UUID.fromString(args[1]);
        try {
            Flat mainFlat = c.getFlatByID(id);
            c.getFlats().removeIf(flat -> mainFlat.compareTo(flat) > 0);
            System.out.println("Elements removed successfully.");

        } catch (IllegalArgumentException e) {
            System.out.println("You need to supply an ID, which is an UUID");
        }

    }

    @Override
    public String getDescription() {
        return "removes all elements greater than the one given";
    }
}

