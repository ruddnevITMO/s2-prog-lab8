package ru.rudXson.commands;

import java.io.IOException;
import java.util.Scanner;

import ru.rudXson.base.Client;
import ru.rudXson.requests.ClearRequest;

public class Clear implements Command {
    @Override
    public void execute(String[] args, Client client, boolean fromExecute, Scanner executeScanner) throws IOException {
        client.sendRequestGetResponse(new ClearRequest());
        System.out.println("All elements you owned, were successfully cleared.");
    }

}

