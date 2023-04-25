package ru.rudXson.base;

import ru.rudXson.commands.*;

import java.util.HashMap;

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
        commands.put("execute_script", new ExecuteScript(this));
        commands.put("remove_by_id", new RemoveByID(c));
        commands.put("remove_first", new RemoveFirst(c));
        commands.put("add_if_min", new AddIfMin(c));
        commands.put("print_descending", new PrintDescending(c));
        commands.put("info", new Info(c));
        commands.put("clear", new Clear(c));
        commands.put("print_unique_house", new PrintUniqueHouse(c));
        commands.put("exit", new Exit());
        commands.put("print_field_descending_transport",new PrintFieldDescendingTransport(c));
    }

    public void startInteractiveMode(){
        System.out.println("Entered the interactive mode!");
        while (true) {
            System.out.print("\u001B[36mEnter command: \u001B[0m");
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
    public Command getCommand(String commandName) {
        if(!commands.containsKey(commandName)) return null; // check if command exist
        return commands.get(commandName);
    }
}
