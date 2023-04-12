package ru.rudXson.commands;

import java.util.HashMap;

public class Help implements Command{
    HashMap<String, Command> commands;

    public Help(HashMap<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public void execute(String[] args) {
        for (String command:commands.keySet()){
            System.out.println(command + " - " + commands.get(command).getDescription());
        }
    }
    @Override
    public String getDescription() {
        return "Shows all command descriptions";
    }
}
