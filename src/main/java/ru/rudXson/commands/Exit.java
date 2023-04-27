package ru.rudXson.commands;

import java.io.IOException;
import java.util.Scanner;

public class Exit implements Command {
    @Override
    public void execute(String[] args, boolean fromExecute, Scanner executeScanner) throws IOException {
        System.out.println("Closed the program.");
        System.exit(0);
    }

    @Override
    public String getDescription() {
        return "Exits the program without saving the changes to the file.";
    }
}
