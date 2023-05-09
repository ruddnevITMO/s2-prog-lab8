package ru.rudXson;

import ru.rudXson.base.CommandExecutor;
import ru.rudXson.base.CLIController;
import ru.rudXson.datatype.*;
import javax.naming.NoPermissionException;
import java.io.*;

public class Main {
    /**
     * Main method that is responsible for running the program.
     *
     * @param args Command line arguments that specify the name of the input file.
     * @throws IOException If there is an error reading or writing to the file.
     */
    public static void main(String[] args) throws IOException, NoPermissionException {
        CLIController controller = new CLIController(args);
        // Execute the interactive command line mode using CommandExecutor
        CommandExecutor go = new CommandExecutor(controller);
        go.startInteractiveMode();

    }
}
