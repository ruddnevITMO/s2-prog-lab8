package ru.rudXson.commands;

import ru.rudXson.base.Client;
import ru.rudXson.requests.RemoveFirstRequest;
import ru.rudXson.responses.RemoveFirstResponse;

import java.io.IOException;
import java.util.Scanner;


public class RemoveFirst implements Command {

    public RemoveFirst() {
    }

    @Override
    public void execute(String[] args, Client client, boolean fromExecute, Scanner executeScanner) throws IOException {
        RemoveFirstResponse response = (RemoveFirstResponse) client.sendRequestGetResponse(new RemoveFirstRequest());
    }

    @Override
    public String getDescription() {
        return "Removes first element of sorted array";
    }
}