/**
 * A command that saves a priority queue of flats to a file using Serializer class
 */
package ru.rudXson.commands;

import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Scanner;

import ru.rudXson.base.CLIController;
import ru.rudXson.base.Serializer;
import ru.rudXson.datatype.Flat;
import ru.rudXson.exceptions.NoPermission;

public class Save implements Command {

    private final CLIController c;

    /**
     * Constructs a Save command with the given Scanner, priority queue of flats and file name.
     *
     * @param controller an object that holds every argument needed
     */
    public Save(CLIController controller) {
        this.c = controller;
    }

    /**
     * Executes the Save command by serializing the priority queue of flats to a file.
     * If there is an error writing to the file, the user will be prompted to enter a new file name.
     *
     * @param args the arguments to execute the command with
     */
    @Override
    public void execute(String[] args) {
        while (true) {
            try {
                Serializer.serialize(c.getFlats(), c.getFileName());
                break;
            } catch (NoPermission | IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
                System.out.print("Enter a new file name: ");
                c.setFileName(c.getScanner().nextLine());
            }
        }
    }

    /**
     * Gets the description of the Save command.
     *
     * @return the description of the command
     */
    @Override
    public String getDescription() {
        return "Saves collection to a file";
    }
}