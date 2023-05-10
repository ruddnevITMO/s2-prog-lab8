package ru.rudXson.commands;

import ru.rudXson.requests.Request;
import ru.rudXson.responses.HelpResponse;
import ru.rudXson.responses.Response;

import java.util.HashMap;

public class Help implements Command {
    private final HashMap<String, Command> commands;

    public Help(HashMap<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public Response execute(Request req) {
        HashMap<String, String> descriptions = new HashMap<>();
        for (String command:commands.keySet()){
            descriptions.put(command, commands.get(command).getDescription());
        }
        return new HelpResponse(descriptions, null);

    }
    @Override
    public String getDescription() {
        return "Shows all command descriptions";
    }
}
