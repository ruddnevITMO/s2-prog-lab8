package ru.rudXson.commands;

import java.io.IOException;
import java.util.Scanner;
import java.util.PriorityQueue;

import ru.rudXson.base.Client;
import ru.rudXson.datatype.Flat;
import ru.rudXson.requests.AddRequest;
import ru.rudXson.requests.ClearRequest;
import ru.rudXson.responses.AddResponse;
import ru.rudXson.responses.ClearResponse;


public class Clear implements Command {
    public Clear() {
    }

    @Override
    public void execute(String[] args, Client client, boolean fromExecute, Scanner executeScanner) throws IOException {
        ClearResponse response = (ClearResponse) client.sendRequestGetResponse(new ClearRequest());
    }

    @Override
    public String getDescription() {
        return "Clears the collection";
    }
}

