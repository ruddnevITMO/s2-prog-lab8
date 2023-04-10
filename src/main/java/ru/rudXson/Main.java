package ru.rudXson;

import ru.rudXson.base.CommandExecutor;
import ru.rudXson.base.Deserializer;
import ru.rudXson.base.Serializer;
import ru.rudXson.datatype.Flat;
import ru.rudXson.base.CLIController;

import ru.rudXson.base.FileValidator;
import ru.rudXson.exceptions.NoPermission;

import java.io.*;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    /**
     * Main method that is responsible for running the program.
     *
     * @param args Command line arguments that specify the name of the input file.
     * @throws IOException If there is an error reading or writing to the file.
     */
    public static void main(String[] args) throws IOException, NoPermission {
        CLIController controller = new CLIController(args);

        System.out.println(controller.getFlats());
        // Execute the interactive command line mode using CommandExecutor
        CommandExecutor go = new CommandExecutor(controller);
        go.startInteractiveMode();

    }
}
