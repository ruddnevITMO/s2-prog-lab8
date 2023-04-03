package ru.rudXson.base;

import ru.rudXson.commands.Command;
import ru.rudXson.commands.Help;

import java.util.HashMap;
import java.util.Scanner;

public class CommandExecutor {
    HashMap<String, Command> commands = new HashMap<>();

    public CommandExecutor() {
        commands.put("help", new Help(commands));

    }

    public void startInteractiveMode(Scanner scanner){
        System.out.println("Entered the interactive mode!");
        while (true) {
            System.out.println("Enter command:");
            String [] line = scanner.nextLine().toLowerCase().strip().split(" ");
            if (!commands.containsKey(line[0])){
                System.out.println("This command doesn't exist");
                continue;
            }
            commands.get(line[0]).execute(line);
            //TODO catch errors in commands (NotEnoughArgs and WrongArgs)
        }
    }
}
