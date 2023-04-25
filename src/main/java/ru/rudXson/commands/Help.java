package ru.rudXson.commands;

import java.util.HashMap;

public class Help implements Command{
    HashMap<String, Command> commands;

    public Help(HashMap<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public void execute(String[] args) {
        System.out.printf("%-35s   %-90s %n", "COMMAND", "DESCRIPTION");
        System.out.printf("--------------------------------------------------------------------------%n");
        for (String command:commands.keySet()){
            System.out.printf("%-35s   %-90s %n", command, commands.get(command).getDescription());
        }
    }
    @Override
    public String getDescription() {
        return "Shows all command descriptions";
    }
}
