
package ru.rudXson.commands;

import ru.rudXson.base.CLIController;
import ru.rudXson.datatype.Flat;
import ru.rudXson.exceptions.NoPermission;

import java.io.IOException;
import java.util.Iterator;

public class RemoveGreater implements Command {
    private final CLIController c;

    public RemoveGreater(CLIController c) {
        this.c = c;
    }

    @Override
    public void execute(String[] args) throws NoPermission, IOException {
        if (args.length != 1) {
            System.out.println("There's no args");
            return;
        }
        int id = Integer.parseInt(args[0]);
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

