package ru.rudXson.commands;

import java.io.IOException;
import java.util.Scanner;

import ru.rudXson.base.Client;
import ru.rudXson.base.InputManager;
import ru.rudXson.datatype.Flat;
import ru.rudXson.requests.AddIfMinRequest;
import ru.rudXson.responses.AddIfMinResponse;

public class AddIfMin implements Command {
    private final Scanner scanner;

    public AddIfMin(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String getDescription() {
        return "Adds element if it is smaller than the smallest element of sorted array";
    }

    @Override
    public void execute(String[] args, Client client, boolean fromExecute, Scanner executeScanner) throws IOException {
        Scanner currScanner = this.scanner;
        if (fromExecute) currScanner = executeScanner;
        InputManager inManager = new InputManager(currScanner);
        Flat flat = new Flat();
        inManager.describeFlat(flat);

        AddIfMinResponse response = (AddIfMinResponse) client.sendRequestGetResponse(new AddIfMinRequest(flat));

//        System.out.println("Flat was added to collection.");
//        } else {
//            System.out.println("Flat was not added to collection. Its value is greater than or equal to the minimum value in the collection.");
//        }
    }
}