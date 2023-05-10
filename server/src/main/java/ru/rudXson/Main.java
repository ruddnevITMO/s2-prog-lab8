package ru.rudXson;

import ru.rudXson.base.CLIController;
import ru.rudXson.base.CommandExecutor;

import javax.naming.NoPermissionException;
import java.io.*;
import java.util.Scanner;

public class Main {
    /**
     * Main method that is responsible for running the program.
     *
     * @param args Command line arguments that specify the name of the input file.
     * @throws IOException If there is an error reading or writing to the file.
     */
    public static void main(String[] args) throws NoPermissionException, IOException {
        CLIController controller = new CLIController();
        CommandExecutor go = new CommandExecutor(controller, new Scanner(System.in));
        go.startService();

    }
}
