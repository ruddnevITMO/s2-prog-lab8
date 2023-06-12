package ru.rudXson.commands;


import ru.rudXson.base.Client;
import ru.rudXson.requests.PrintUniqueHouseRequest;
import ru.rudXson.responses.PrintUniqueHouseResponse;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.util.Scanner;

public class PrintUniqueHouse implements Command {
    @Override
    public void execute(String[] args, Client client, boolean fromExecute, Scanner executeScanner) throws NoPermissionException, IOException {
        PrintUniqueHouseResponse response = (PrintUniqueHouseResponse) client.sendRequestGetResponse(new PrintUniqueHouseRequest());

        if (response.uniqueHouses.isEmpty()) System.out.println("Unique house values:");
        for (String house : response.uniqueHouses) {
            System.out.println(house);
        }
    }


}
