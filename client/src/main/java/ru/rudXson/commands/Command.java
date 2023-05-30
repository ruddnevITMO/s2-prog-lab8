    package ru.rudXson.commands;

import ru.rudXson.exceptions.ExitException;
import ru.rudXson.exceptions.NotEnoughArgsException;
import ru.rudXson.exceptions.WrongArgsException;
import ru.rudXson.base.Client;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.util.Scanner;

    public interface Command {
        void execute(String[] args, Client client, boolean fromExecute, Scanner executeScanner) throws NoPermissionException, IOException, WrongArgsException, NotEnoughArgsException, ExitException; // throws NEA, WA
    String getDescription();
}
