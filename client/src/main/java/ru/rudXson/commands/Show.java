package ru.rudXson.commands;

import ru.rudXson.base.CLIController;

import java.util.Scanner;

public class Show implements Command {
    private final CLIController controller;

    public Show(CLIController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args, boolean fromExecute, Scanner executeScanner) {
        System.out.println(controller.getFlats());
    }

    @Override
    public String getDescription() {
        return "Shows all collection items";
    }
}