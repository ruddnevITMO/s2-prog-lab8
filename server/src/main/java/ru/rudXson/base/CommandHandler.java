package ru.rudXson.base;

import ru.rudXson.commands.Command;
import ru.rudXson.exceptions.ExitException;
import ru.rudXson.requests.Request;
import ru.rudXson.responses.ErrorResponse;
import ru.rudXson.responses.Response;

public class CommandHandler {
    private final CommandExecutor executor;

    public CommandHandler(CommandExecutor executor) {
        this.executor = executor;
    }

    public Response handle(Request request) throws ExitException {
        Command command = executor.getCommand(request.name);
        if (command == null) return new ErrorResponse("No such command!");
        return command.execute(request);
    }
}
