package ru.rudXson.base;

import ru.rudXson.serverCommands.*;
import ru.rudXson.exceptions.ExitException;
import ru.rudXson.exceptions.NotEnoughArgsException;
import ru.rudXson.exceptions.WrongArgsException;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class represents a command executor that is used to execute different commands
 */
public class ServerCommandExecutor implements Runnable {
    HashMap<String, Command> commands = new HashMap<>();

    Scanner scanner = new Scanner(System.in);

    public ServerCommandExecutor(CLIController controller) {
        commands.put("exit", new Exit());
        commands.put("save", new Save(controller, scanner));
    }

    /**
     * Starts the interactive mode for the user to input commands
     */
    public void run(){
        System.out.println("Entered the server-side control panel!");
        while (true) {
            System.out.print("\033[0;35mEnter command: \u001B[0m\t");
            String[] line = this.scanner.nextLine().toLowerCase().strip().split(" ");
            if (!commands.containsKey(line[0])){
                System.out.println("This command doesn't exist");
                continue;
            }
            try {
                commands.get(line[0]).execute(line);
            } catch (NotEnoughArgsException |  NoPermissionException | WrongArgsException | IOException e) {
                System.out.println("An error occurred: " + e.getMessage());
            } catch (ExitException e) {
                System.exit(0);
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
