
package ru.rudXson.commands;

import ru.rudXson.base.InputManager;
import ru.rudXson.datatype.Flat;
import ru.rudXson.exceptions.NotEnoughArgsException;
import ru.rudXson.exceptions.WrongArgsException;
import ru.rudXson.base.Client;
import ru.rudXson.requests.RemoveGreaterRequest;
import ru.rudXson.responses.RemoveFirstResponse;
import ru.rudXson.responses.RemoveGreaterResponse;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class RemoveGreater implements Command {


    public RemoveGreater() {
    }

    @Override
    public void execute(String[] args, Client client, boolean fromExecute, Scanner executeScanner) throws IOException {
        Scanner currScanner = scanner;
        if (fromExecute) currScanner = executeScanner;
        InputManager inManager = new InputManager(currScanner);
        Flat flat = new Flat();
        inManager.describeFlat(flat);


        RemoveGreaterResponse response = (RemoveGreaterResponse) client.sendRequestGetResponse(new RemoveGreaterRequest(flat));
        System.out.println("Elements removed successfully.");

    }

    @Override
    public String getDescription() {
        return "removes all elements greater than the one given";
    }
}

