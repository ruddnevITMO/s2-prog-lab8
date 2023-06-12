package ru.rudXson.commands;

import ru.rudXson.exceptions.NotEnoughArgsException;
import ru.rudXson.exceptions.WrongArgsException;
import ru.rudXson.base.Client;
import ru.rudXson.requests.RemoveByIdRequest;
import ru.rudXson.responses.RemoveByIdResponse;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class RemoveByID implements Command {
    @Override
    public void execute(String[] args, Client client, boolean fromExecute, Scanner executeScanner) throws NotEnoughArgsException, WrongArgsException, IOException {
        if (args.length < 2) throw new NotEnoughArgsException("ID is required");
        try {
            RemoveByIdResponse response = (RemoveByIdResponse) client.sendRequestGetResponse(new RemoveByIdRequest(UUID.fromString(args[1])));
            if (response.error != null) System.out.println(response.error);
        } catch (IllegalArgumentException e) {
            throw new WrongArgsException("You need to supply an ID, which is an UUID");
        }
    }
}