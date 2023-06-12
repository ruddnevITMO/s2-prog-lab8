package ru.rudXson.commands;

import ru.rudXson.base.InputManager;
import ru.rudXson.datatype.Flat;
import ru.rudXson.requests.AddRequest;
import ru.rudXson.responses.AddResponse;
import ru.rudXson.base.Client;

import java.io.IOException;
import java.util.Scanner;

public class Add implements Command {
    private final Scanner scanner;

    public Add(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void execute(String[] args, Client client, boolean fromExecute, Scanner executeScanner) throws IOException {
        Scanner currScanner = this.scanner;
        if (fromExecute) currScanner = executeScanner;
        InputManager inManager = new InputManager(currScanner);
        Flat flat = new Flat();
        inManager.describeFlat(flat);

        AddResponse response = (AddResponse) client.sendRequestGetResponse(new AddRequest(flat));
        if (response.error != null) {
            System.out.println(response.error);
            return;
        }
        System.out.println("Flat was added to collection.");
    }
}
