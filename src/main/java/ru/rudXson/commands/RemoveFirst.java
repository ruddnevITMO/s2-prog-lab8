package ru.rudXson.commands;

import ru.rudXson.base.CLIController;

import java.util.Scanner;


public class RemoveFirst implements Command {
    private final CLIController controller;

    public RemoveFirst(CLIController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args, boolean fromExecute, Scanner executeScanner) {
        controller.getFlats().poll();
    }

    @Override
    public String getDescription() {
        return "Removes first element of sorted array";
    }
}