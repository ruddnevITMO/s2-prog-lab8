    package ru.rudXson.commands;

import ru.rudXson.exceptions.NotEnoughArgsException;
import ru.rudXson.exceptions.WrongArgsException;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.util.Scanner;

    public interface Command {
    public void execute(String[] args, boolean fromExecute, Scanner executeScanner) throws NoPermissionException, IOException, WrongArgsException, NotEnoughArgsException; // throws NEA, WA
    public String getDescription();
}
