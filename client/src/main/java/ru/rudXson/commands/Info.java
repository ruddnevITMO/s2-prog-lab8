package ru.rudXson.commands;

import ru.rudXson.base.Client;
import ru.rudXson.requests.InfoRequest;
import ru.rudXson.responses.InfoResponse;

import java.io.IOException;
import java.util.Scanner;

public class Info implements Command{
    @Override
    public void execute(String[] args, Client client, boolean fromExecute, Scanner executeScanner) throws IOException {
        System.out.println(((InfoResponse) client.sendRequestGetResponse(new InfoRequest())).infoMessage);
    }
}

