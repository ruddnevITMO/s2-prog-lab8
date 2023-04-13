package ru.rudXson.commands;

import ru.rudXson.exceptions.NoPermission;
import ru.rudXson.exceptions.NotEnoughArgsException;
import ru.rudXson.exceptions.WrongArgsException;

import java.io.IOException;

public interface Command {
    public void execute(String [] args) throws NoPermission, IOException, WrongArgsException, NotEnoughArgsException; // throws NEA, WA
    public String getDescription();
}
