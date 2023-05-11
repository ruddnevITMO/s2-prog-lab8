package ru.rudXson.commands;

import ru.rudXson.base.CLIController;
import ru.rudXson.base.FileValidator;
import ru.rudXson.base.Serializer;
import ru.rudXson.requests.Request;
import ru.rudXson.responses.RemoveByIdResponse;
import ru.rudXson.responses.Response;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.util.Scanner;

public class Save implements Command {

    private final CLIController controller;
    private final Scanner scanner;


    public Save(CLIController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public Response execute(Request req) {
        while (true) {
            try {
                FileValidator.checkFile(controller.getFileName());
                Serializer.serialize(controller.getFlats(), controller.getFileName());
                System.out.println("Successfully saved collection to a file!");
                return new Response("save", null);
            } catch (NoPermissionException | IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
                System.out.print("Enter a new file name: ");
                controller.setFileName(this.scanner.nextLine());
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