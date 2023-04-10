package ru.rudXson.commands;

import ru.rudXson.exceptions.NoPermission;

import java.io.IOException;

public interface Command {
    public void execute(String [] args) throws NoPermission, IOException; // throws NEA, WA
    public String getDescription();
}
