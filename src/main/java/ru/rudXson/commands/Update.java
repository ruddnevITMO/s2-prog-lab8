package ru.rudXson.commands;

import ru.rudXson.base.CLIController;
import ru.rudXson.base.InputManager;
import ru.rudXson.datatype.Flat;
import ru.rudXson.exceptions.NotEnoughArgsException;
import ru.rudXson.exceptions.WrongArgsException;

import java.util.Scanner;
import java.util.UUID;

public class Update implements Command {

    private final CLIController controller;

    public Update(CLIController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args, boolean fromExecute, Scanner executeScanner) throws WrongArgsException, NotEnoughArgsException {
        if (args.length < 2) throw new NotEnoughArgsException("ID is required");
        Flat flat;
        try {
            flat = controller.getFlatByID(UUID.fromString(args[1]));
        } catch (IllegalArgumentException e) {
            throw new WrongArgsException("You need to supply an ID, which is an UUID");
        }

        Scanner scanner = controller.getScanner();
        if (fromExecute) scanner = executeScanner;
        InputManager inManager = new InputManager(scanner);
        inManager.describeFlat(flat);

    }

    @Override
    public String getDescription() {
        return "Updates element with ID specified";
    }
}
