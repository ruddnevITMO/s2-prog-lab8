package ru.rudXson.commands;

import java.io.IOException;

public class Exit implements Command {
    public void execute(String[] args) throws IOException {
        System.out.println("Closed the program.");
        System.exit(0);
    }

    public String getDescription() {
        return "Exits the program without saving the changes to the file.";
    }
}
