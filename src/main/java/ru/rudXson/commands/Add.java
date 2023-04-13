package ru.rudXson.commands;

import ru.rudXson.base.CLIController;
import ru.rudXson.base.InputManager;
import ru.rudXson.datatype.*;

public class Add implements Command {
    CLIController c;
    private InputManager inManager;

    public Add(CLIController c) {
        this.c = c;
    }

    @Override
    public void execute(String[] args) {
        InputManager inManager = new InputManager(c.getScanner());
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
