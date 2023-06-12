package ru.rudXson.commands;

import ru.rudXson.base.InputManager;
import ru.rudXson.datatype.Flat;
import ru.rudXson.exceptions.NotEnoughArgsException;
import ru.rudXson.exceptions.WrongArgsException;
import ru.rudXson.base.Client;
import ru.rudXson.requests.UpdateRequest;
import ru.rudXson.responses.UpdateResponse;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class Update implements Command {

    private final Scanner scanner;

    public Update(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void execute(String[] args, Client client, boolean fromExecute, Scanner executeScanner) throws WrongArgsException, NotEnoughArgsException, IOException {
        if (args.length < 2) throw new NotEnoughArgsException("ID is required");
        UUID id;
        try {
            id = UUID.fromString(args[1]);
        } catch (IllegalArgumentException e) {
            throw new WrongArgsException("You need to supply an ID, which is an UUID");
        }

        Flat flat = new Flat();
        Scanner currScanner = this.scanner;
        if (fromExecute) currScanner = executeScanner;
        InputManager inManager = new InputManager(currScanner);
        inManager.describeFlat(flat);

        UpdateResponse response = (UpdateResponse) client.sendRequestGetResponse(new UpdateRequest(id, flat));

        if (response.error != null) System.out.println(response.error);

    }

}
