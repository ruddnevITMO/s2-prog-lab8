package ru.rudXson.commands;

import ru.rudXson.base.Client;
import ru.rudXson.datatype.Flat;
import ru.rudXson.requests.PrintDescendingRequest;
import ru.rudXson.responses.PrintDescendingResponse;

import java.io.IOException;
import java.util.Scanner;

public class PrintDescending implements Command {
    @Override
    public void execute(String[] args, Client client, boolean fromExecute, Scanner executeScanner) throws IOException {
        PrintDescendingResponse response = (PrintDescendingResponse) client.sendRequestGetResponse(new PrintDescendingRequest());
        System.out.println("Elements of collection in descending order:");
        for (Flat flat : response.flats) {
            System.out.println(flat);
        }
    }
}
