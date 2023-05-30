package ru.rudXson.commands;

import ru.rudXson.exceptions.ExitException;
import ru.rudXson.base.Client;

import java.io.IOException;
import java.util.Scanner;

public class Exit implements Command {
    @Override
    public void execute(String[] args, Client client, boolean fromExecute, Scanner executeScanner) throws IOException, ExitException {
        System.out.println("Closed the program.");
        throw new ExitException();
    }

    @Override
    public String getDescription() {
        return "Exits the program without saving the changes to the file.";
    }
}
