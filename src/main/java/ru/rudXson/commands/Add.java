package ru.rudXson.commands;

import ru.rudXson.base.CLIController;
import ru.rudXson.base.InputManager;
import ru.rudXson.datatype.*;

import java.util.Scanner;

public class Add implements Command {
    private final CLIController controller;

    public Add(CLIController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args, boolean fromExecute, Scanner executeScanner) {
        Scanner scanner = controller.getScanner();
        if (fromExecute) scanner = executeScanner;
        InputManager inManager = new InputManager(scanner);
        Flat flat = new Flat();
        inManager.describeFlat(flat);
        this.controller.addFlat(flat);
        System.out.println("Element added successfully");
    }

    @Override
    public String getDescription() {
        return "Adds an element to the collection";
    }


}
