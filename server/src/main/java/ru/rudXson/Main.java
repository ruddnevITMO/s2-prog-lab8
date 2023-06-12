package ru.rudXson;

import ru.rudXson.base.*;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class Main {
    /**
     * Main method that is responsible for running the program.
     *
     * @param args Command line arguments that specify the credentials for the PostgreSQL database.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        SQLController controller;
        String url;
        String user;
        String password;

        if (args.length < 4) {
            url = args[0];
            user = args[1];
            password = args[2];
        } else {
            System.out.println("Arguments are wrong.");
            System.out.print("Please enter JDBC url: ");
            url = scanner.nextLine();
            System.out.print("Please enter db username: ");
            user = scanner.nextLine();
            System.out.print("Please enter db password: ");
            password = scanner.nextLine();
        }

        while (true) {
            try {
                controller = new SQLController(url, user, password);
                break;
            } catch (SQLException e) {
                System.out.println("Wrong credentials. Try again.");
                System.out.print("Please enter JDBC url: ");
                url = scanner.nextLine();
                System.out.print("Please enter db username: ");
                user = scanner.nextLine();
                System.out.print("Please enter db password: ");
                password = scanner.nextLine();
            }
        }

        CommandExecutor commandExecutor = new CommandExecutor(controller);

        Executors.newSingleThreadExecutor().execute(new ServerCommandExecutor());

        try {
            var server = new Server(new InetSocketAddress(InetAddress.getLocalHost(), 1488), commandExecutor);
            server.go();
        } catch (SocketException e) {
            System.out.println("Случилась ошибка сокета");
        } catch (UnknownHostException e) {
            System.out.println("Неизвестный хост");
        }
    }
}
