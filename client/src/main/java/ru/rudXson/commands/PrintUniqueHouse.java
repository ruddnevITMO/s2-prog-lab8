package ru.rudXson.commands;


import ru.rudXson.base.Client;
import ru.rudXson.requests.PrintUniqueHouseRequest;
import ru.rudXson.responses.PrintUniqueHouseResponse;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.util.Scanner;

public class PrintUniqueHouse implements Command {
    public PrintUniqueHouse() {
    }
    @Override
    public void execute(String[] args, Client client, boolean fromExecute, Scanner executeScanner) throws NoPermissionException, IOException {
        PrintUniqueHouseResponse response = (PrintUniqueHouseResponse) client.sendRequestGetResponse(new PrintUniqueHouseRequest());

        System.out.println("Unique house values:");
        for (String house : response.uniqueHouses) {
            System.out.println(house);
        }
    }

    @Override
    public String getDescription() {
        return "Prints unique values of the house field for all elements in the collection.";
    }
}
