package ru.rudXson.commands;

import ru.rudXson.base.CLIController;
import ru.rudXson.base.InputManager;
import ru.rudXson.datatype.Flat;
import ru.rudXson.exceptions.NotEnoughArgsException;
import ru.rudXson.exceptions.WrongArgsException;

import java.util.UUID;

public class Update implements Command {

    private final CLIController c;

    public Update(CLIController c) {
        this.c = c;
    }

    @Override
    public void execute(String[] args) throws WrongArgsException, NotEnoughArgsException {
        if (args.length < 2) throw new NotEnoughArgsException("ID is required");
        UUID id;
        try {
            id = UUID.fromString(args[1]);
        }
        catch (IllegalArgumentException e){
            throw new WrongArgsException("You need to supply an ID, which is an UUID");
        }

        Flat flat = c.getFlatByID(id);
        if (flat == null) throw new WrongArgsException("There is no element with such ID");

        InputManager inManager = new InputManager(c.getScanner());
        inManager.describeFlat(flat);

    }

    @Override
    public String getDescription() {
        return "Update element with ID specified";
    }
}
