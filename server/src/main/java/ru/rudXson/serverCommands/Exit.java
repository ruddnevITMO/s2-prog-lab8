package ru.rudXson.serverCommands;

import ru.rudXson.exceptions.ExitException;


public class Exit implements Command {
    @Override
    public void execute(String[] args) throws ExitException {
        System.out.println("Closed the program.");
        throw new ExitException();
    }

    @Override
    public String getDescription() {
        return "Exits the program without saving the changes to the file.";
    }
}
