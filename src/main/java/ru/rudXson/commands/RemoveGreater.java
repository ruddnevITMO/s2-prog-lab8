
package ru.rudXson.commands;

import ru.rudXson.base.CLIController;
import ru.rudXson.datatype.Flat;

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
    public void execute(String[] args, boolean fromExecute, Scanner executeScanner) throws NoPermissionException, IOException {
        if (args.length != 1) {
            System.out.println("There's no args");
            return;
        }
        UUID id = UUID.fromString(args[1]);
        Flat mainFlat = c.getFlatByID(id);
        Iterator<Flat> iter = c.getFlats().iterator();
        while (iter.hasNext()) {
            Flat flat = iter.next();
            if (mainFlat.compareTo(flat) > 0) {
                iter.remove();
            }
        }
        System.out.println("Elements removed successfully.");
    }

    @Override
    public String getDescription() {
        return "remove_greater id : remove all elements greater than given";
    }
}

