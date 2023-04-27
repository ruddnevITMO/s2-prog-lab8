package ru.rudXson.commands;

import ru.rudXson.base.CommandExecutor;
import ru.rudXson.exceptions.NotEnoughArgsException;
import ru.rudXson.exceptions.WrongArgsException;

import javax.naming.NoPermissionException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class ExecuteScript implements Command {
    CommandExecutor commandExecutor;
    private static final ArrayList<Integer> recursionHistory = new ArrayList<>();


    public ExecuteScript(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @Override
    public void execute(String[] args, boolean fromExecute, Scanner executeScanner) throws WrongArgsException, NotEnoughArgsException, IOException {
        if (args.length < 2) throw new NotEnoughArgsException("Command requires \"path\" argument");
        Path path = Paths.get(args[1]);
        recursionHistory.add(args[1].hashCode());
        String scriptFileName = args[1];

        try {
            // check file permissions
            if (!Files.exists(path)) throw new FileNotFoundException("File " + path + " not found");
            if (!Files.isReadable(path)) throw new NoPermissionException("Cannot read file.");
            if (!Files.isWritable(path)) throw new NoPermissionException("Cannot write to file.");
        } catch (FileNotFoundException e) {
            System.out.println("File " + path + " not found."); // file does not exist
            return;
        } catch (NoPermissionException e) {
            System.out.print("No enough permissions to " + path + " - " + e.getMessage()); // permissions deny
            return;
        }

        try (Scanner scanner = new Scanner(new File(path.toUri()))) {
            System.out.println("Running " + path);
            runThrough(scanner);
            recursionHistory.clear();

        } catch (IOException e) {
            throw new IOException("Failed to deserialize the priority queue from file: " + scriptFileName, e);
        }
    }

    private void runThrough(Scanner scanner) throws IOException {
        while (scanner.hasNextLine()) {
            String currLine = scanner.nextLine();
            if (currLine == null) return;
            String[] args = currLine.trim().toLowerCase().split(" ");
            Command command = commandExecutor.getCommand(args[0]);



            if (command == null) {
                System.out.println(args[0] + " is not a command. Try again.");
                continue;
            }

            try {
                if (command.getClass() == ExecuteScript.class) {
                    if (ExecuteScript.recursionHistory.contains(args[1].hashCode())) {
                        System.out.println("Recursion! Command skipped!");
                        System.out.println(ExecuteScript.recursionHistory);
                        return;
                    }
                    ExecuteScript.recursionHistory.add(args[0].hashCode());
                }
                command.execute(args, true, scanner);

            } catch (WrongArgsException e) {
                System.out.println("Error while running " + args[0] + " command.");
                System.out.println("Wrong argument! " + e.getMessage() + " Command skipped");
            } catch (NotEnoughArgsException e) {
                System.out.println("Error while running " + args[0] + " command.");
                System.out.println("Not enough arguments. " + e.getMessage() + " Command skipped");
            } catch (NoPermissionException e) {
                throw new RuntimeException(e);
            }


        }

    }


    @Override
    public String getDescription() {
        return "Runs commands from a file, line by line";
    }

}
