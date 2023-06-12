package ru.rudXson.commands;

import ru.rudXson.base.Client;
import ru.rudXson.base.InputManager;
import ru.rudXson.requests.RegisterRequest;
import ru.rudXson.responses.RegisterResponse;

import java.io.IOException;
import java.util.Scanner;

public class Register implements Command {
    private final Scanner scanner;

    public Register(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void execute(String[] args, Client client, boolean fromExecute, Scanner executeScanner) throws IOException {
        Scanner currScanner = this.scanner;
        if (fromExecute) currScanner = executeScanner;
        InputManager inManager = new InputManager(currScanner);
        while (true) {
            client.setCreds(inManager.registerPrompt());
            RegisterResponse response = (RegisterResponse) client.sendRequestGetResponse(new RegisterRequest());
            if (response.error == null) {
                break;
            }
            System.out.println(response.error);
        }


        System.out.println("Successfully registered! Now you have access to all of the commands.");
    }




}
