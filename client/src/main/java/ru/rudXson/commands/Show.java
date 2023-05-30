package ru.rudXson.commands;

import java.io.IOException;
import java.util.Scanner;
import ru.rudXson.base.Client;
import ru.rudXson.requests.ShowRequest;
import ru.rudXson.responses.ShowResponse;


public class Show implements Command {

    public Show() {
    }

    @Override
    public void execute(String[] args, Client client, boolean fromExecute, Scanner executeScanner) throws IOException {
        ShowResponse response = (ShowResponse) client.sendRequestGetResponse(new ShowRequest());
        System.out.println(response.flats);
    }

    @Override
    public String getDescription() {
        return "Shows all collection items";
    }
}