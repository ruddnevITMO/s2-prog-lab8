package ru.rudXson.commands;

public interface Command {
    public void execute(String [] args); // throws NEA, WA
    public String getDescription();
}
