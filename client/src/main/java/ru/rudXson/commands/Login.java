package ru.rudXson.commands;

import ru.rudXson.base.InputManager;
import ru.rudXson.requests.LoginRequest;
import ru.rudXson.base.Client;
import ru.rudXson.responses.LoginResponse;

import java.io.IOException;
import java.util.Scanner;

public class Login implements Command {
    private final Scanner scanner;

    public Login(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void execute(String[] args, Client client, boolean fromExecute, Scanner executeScanner) throws IOException {
        Scanner currScanner = this.scanner;
        if (fromExecute) currScanner = executeScanner;
        InputManager inManager = new InputManager(currScanner);
        for (int i = 0; i < 3; i++) {
            client.setCreds(inManager.loginPrompt());
            LoginResponse response = (LoginResponse) client.sendRequestGetResponse(new LoginRequest());
            if (response.error == null) {
                System.out.println("Successfully logged in! Now you have access to all of the commands.");
                break;
            }
            System.out.println(response.error);
        }
    }




}
