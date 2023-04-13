package ru.rudXson.base;

import ru.rudXson.commands.*;
import ru.rudXson.datatype.Flat;
import ru.rudXson.exceptions.NotEnoughArgsException;
import ru.rudXson.exceptions.WrongArgsException;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class CommandExecutor {
    HashMap<String, Command> commands = new HashMap<>();

    CLIController c;

    public CommandExecutor(CLIController c) {
        this.c = c;
        commands.put("help", new Help(commands));
        commands.put("show", new Show(c));
        commands.put("add", new Add(c));
        commands.put("save", new Save(c));
        commands.put("update", new Update(c));
        commands.put("print_descending", new SortDescending(c.getFlats()));
        commands.put("info", new Info(c));
        commands.put("clear", new Clear(c));
        commands.put("print_unique_house", new PrintUniqueHouse(c));
        commands.put("exit", new Exit());
        commands.put("print_field_descending_transport",new PrintFieldDescendingTransport(c));
    }

    public void startInteractiveMode(){
        System.out.println("Entered the interactive mode!");
        while (true) {
            System.out.print("Enter command: ");
            String [] line = c.getScanner().nextLine().toLowerCase().strip().split(" ");
            if (!commands.containsKey(line[0])){
                System.out.println("This command doesn't exist");
                continue;
            }
            try {
                commands.get(line[0]).execute(line);
//            } catch (NotEnoughArgsException e) {
//                System.out.println("Not enough arguments. Usage: " + e.getUsage());
//            } catch (WrongArgsException e) {
//                System.out.println("Wrong arguments. Usage: " + e.getUsage());
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
}
