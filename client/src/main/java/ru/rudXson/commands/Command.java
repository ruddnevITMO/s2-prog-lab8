    package ru.rudXson.commands;

import ru.rudXson.exceptions.ExitException;
import ru.rudXson.exceptions.NotEnoughArgsException;
import ru.rudXson.exceptions.WrongArgsException;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.util.Scanner;

    public interface Command {
     void execute(String[] args, boolean fromExecute, Scanner executeScanner) throws NoPermissionException, IOException, WrongArgsException, NotEnoughArgsException, ExitException; // throws NEA, WA
    String getDescription();
}
