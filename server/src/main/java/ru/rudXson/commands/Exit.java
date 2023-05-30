package ru.rudXson.commands;

import ru.rudXson.exceptions.ExitException;
import ru.rudXson.requests.Request;
import ru.rudXson.responses.Response;


public class Exit implements Command {
    @Override
    public Response execute(Request req) throws ExitException {
        System.out.println("Closed the program.");
        throw new ExitException();
    }

    @Override
    public String getDescription() {
        return "Exits the program without saving the changes to the file.";
    }
}
