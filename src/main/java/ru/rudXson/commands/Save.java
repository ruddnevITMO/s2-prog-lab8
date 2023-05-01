/**
 * A command that saves a priority queue of flats to a file using Serializer class
 */
package ru.rudXson.commands;

import java.io.IOException;
import java.util.Scanner;

import ru.rudXson.base.CLIController;
import ru.rudXson.base.FileValidator;
import ru.rudXson.base.Serializer;

import javax.naming.NoPermissionException;

public class Save implements Command {

    private final CLIController controller;

    /**
     * Constructs a Save command with the given Scanner, priority queue of flats and file name.
     *
     * @param controller an object that holds every argument needed
     */
    public Save(CLIController controller) {
        this.controller = controller;
    }

    /**
     * Executes the Save command by serializing the priority queue of flats to a file.
     * If there is an error writing to the file, the user will be prompted to enter a new file name.
     *
     * @param args the arguments to execute the command with
     */
    @Override
    public void execute(String[] args, boolean fromExecute, Scanner executeScanner) {
        while (true) {
            try {
                FileValidator.checkFile(controller.getFileName());
                Serializer.serialize(controller.getFlats(), controller.getFileName());
                System.out.println("Successfully saved collection to a file!");
                break;
            } catch (NoPermissionException | IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
                System.out.print("Enter a new file name: ");
                controller.setFileName(controller.getScanner().nextLine());
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