package ru.rudXson;

import ru.rudXson.base.CommandExecutor;
import ru.rudXson.base.Deserializer;
import ru.rudXson.datatype.Flat;

import ru.rudXson.base.FileValidator;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    /**
     * Main method that is responsible for running the program.
     *
     * @param args Command line arguments that specify the name of the input file.
     * @throws IOException If there is an error reading or writing to the file.
     */
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String fileName;

        // check if argument is provided
        if (args.length < 1) {
            System.out.println("No file name provided.");
            System.out.print("Please enter file name: ");
            fileName = scanner.nextLine();
        } else {
            fileName = args[0];
        }

        // Check if file exists and has write access
        FileValidator.checkFile(fileName);

        // Deserialize the file and store the data in a priority queue
        PriorityQueue<Flat> flats = Deserializer.deserialize(fileName);

        System.out.println(flats);

        // Execute the interactive command line mode using CommandExecutor
        CommandExecutor go = new CommandExecutor();
        go.startInteractiveMode(scanner);
    }
}
