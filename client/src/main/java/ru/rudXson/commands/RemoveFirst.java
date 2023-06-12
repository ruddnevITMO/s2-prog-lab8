package ru.rudXson.commands;

import ru.rudXson.base.Client;
import ru.rudXson.requests.RemoveFirstRequest;
import ru.rudXson.responses.RemoveFirstResponse;

import java.io.IOException;
import java.util.Scanner;


public class RemoveFirst implements Command {
    @Override
    public void execute(String[] args, Client client, boolean fromExecute, Scanner executeScanner) throws IOException {
        RemoveFirstResponse response = (RemoveFirstResponse) client.sendRequestGetResponse(new RemoveFirstRequest());
        if (response.error != null) System.out.println(response.error);
    }

}