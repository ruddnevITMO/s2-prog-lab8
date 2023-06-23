package ru.rudXson.base;

import ru.rudXson.commands.*;
import ru.rudXson.exceptions.ExitException;
import ru.rudXson.exceptions.NotEnoughArgsException;
import ru.rudXson.exceptions.WrongArgsException;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class represents a command executor that is used to execute different commands
 */
public class CommandExecutor {
    HashMap<String, Command> commands = new HashMap<>();

    Scanner scanner = new Scanner(System.in);

    public CommandExecutor() {
        commands.put("login", new Login(scanner));
        commands.put("register", new Register(scanner));
        commands.put("help", new Help());
        commands.put("show", new Show());
        commands.put("add", new Add(scanner));
        commands.put("update", new Update(scanner));
        commands.put("execute_script", new ExecuteScript(this));
        commands.put("remove_by_id", new RemoveByID());
        commands.put("remove_first", new RemoveFirst());
        commands.put("add_if_min", new AddIfMin(scanner));
        commands.put("print_descending", new PrintDescending());
        commands.put("info", new Info());
        commands.put("clear", new Clear());
        commands.put("print_unique_house", new PrintUniqueHouse());
        commands.put("exit", new Exit());
        commands.put("remove_greater", new RemoveGreater(scanner));
        commands.put("print_field_descending_transport", new PrintFieldDescendingTransport());
    }

    /**
     * Starts the interactive mode for the user to input commands
     */
    public void startInteractiveMode(Client client){
        String[] noLoginCommands = new String[] {"login", "register", "exit", "help"};

        System.out.println("Entered the interactive mode!");
        System.out.println("You should login or register first.");
        while (true) {
            System.out.print("\u001B[36mEnter command: \u001B[0m");
            String[] line = this.scanner.nextLine().toLowerCase().strip().split(" ");
            if (!commands.containsKey(line[0])){
                System.out.println("This command doesn't exist");
                continue;
            }
            try {
                if (!Arrays.asList(noLoginCommands).contains(line[0]) && client.getUsername() == null)
                    throw new IOException("You have to login or register first!");
                commands.get(line[0]).execute(line, client, false, null);
            } catch (NotEnoughArgsException | NoPermissionException | WrongArgsException | IOException e) {
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
