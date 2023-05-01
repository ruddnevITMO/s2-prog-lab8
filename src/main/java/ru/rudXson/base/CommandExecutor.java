package ru.rudXson.base;

import ru.rudXson.commands.*;
import ru.rudXson.exceptions.ExitException;
import ru.rudXson.exceptions.NotEnoughArgsException;
import ru.rudXson.exceptions.WrongArgsException;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.util.HashMap;

/**
 * This class represents a command executor that is used to execute different commands
 */
public class CommandExecutor {
    HashMap<String, Command> commands = new HashMap<>();

    CLIController controller;

    /**
     * Constructs a new instance of the CommandExecutor
     * @param controller the command line interface controller to be used to execute the commands
     */
    public CommandExecutor(CLIController controller) {
        this.controller = controller;
        commands.put("help", new Help(commands));
        commands.put("show", new Show(controller));
        commands.put("add", new Add(controller));
        commands.put("save", new Save(controller));
        commands.put("update", new Update(controller));
        commands.put("execute_script", new ExecuteScript(this));
        commands.put("remove_by_id", new RemoveByID(controller));
        commands.put("remove_first", new RemoveFirst(controller));
        commands.put("add_if_min", new AddIfMin(controller));
        commands.put("print_descending", new PrintDescending(controller));
        commands.put("info", new Info(controller));
        commands.put("clear", new Clear(controller));
        commands.put("print_unique_house", new PrintUniqueHouse(controller));
        commands.put("exit", new Exit());
        commands.put("remove_greater", new RemoveGreater(controller));
        commands.put("print_field_descending_transport", new PrintFieldDescendingTransport(controller));
    }

    /**
     * Starts the interactive mode for the user to input commands
     */
    public void startInteractiveMode(){
        System.out.println("Entered the interactive mode!");
        while (true) {
            System.out.print("\u001B[36mEnter command: \u001B[0m");
            String [] line = controller.getScanner().nextLine().toLowerCase().strip().split(" ");
            if (!commands.containsKey(line[0])){
                System.out.println("This command doesn't exist");
                continue;
            }
            try {
                commands.get(line[0]).execute(line, false, null);
            } catch (NotEnoughArgsException |  NoPermissionException | WrongArgsException | IOException e) {
                System.out.println("An error occurred: " + e.getMessage());
            } catch (ExitException e) {
                break;
            }
        }
    }

    /**
     * Returns the command object for the given command name
     * @param commandName the name of the command to get
     * @return the command object for the given command name
     */
    public Command getCommand(String commandName) {
        if(!commands.containsKey(commandName)) return null; // check if command exist
        return commands.get(commandName);
    }
}
