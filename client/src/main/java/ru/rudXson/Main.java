package ru.rudXson;

import ru.rudXson.base.Client;
import ru.rudXson.base.CommandExecutor;

import java.net.InetAddress;
import java.io.*;

public class Main {
    /**
     * Main method that is responsible for running the program.
     *
     * @param args Command line arguments that specify the name of the input file.
     */
    public static void main(String[] args) throws IOException {
        // Execute the interactive command line mode using CommandExecutor
        Client client = new Client(InetAddress.getLocalHost(), 1488);
        CommandExecutor go = new CommandExecutor();
        go.startInteractiveMode(client);

    }
}
