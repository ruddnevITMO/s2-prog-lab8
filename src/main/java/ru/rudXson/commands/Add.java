package ru.rudXson.commands;

import ru.rudXson.base.CLIController;
import ru.rudXson.base.InputManager;
import ru.rudXson.datatype.*;

import java.util.Scanner;

public class Add implements Command {
    CLIController c;
    private InputManager inManager;

    public Add(CLIController c) {
        this.c = c;
    }

    @Override
    public void execute(String[] args, boolean fromExecute, Scanner executeScanner) {
        Scanner scanner = c.getScanner();
        if (fromExecute) scanner = executeScanner;
        InputManager inManager = new InputManager(scanner);
        Flat flat = new Flat();
        inManager.describeFlat(flat);
        this.c.addFlat(flat);
        System.out.println("Element added successfully");
    }

    @Override
    public String getDescription() {
        return "Adds an element to the collection";
    }


}
