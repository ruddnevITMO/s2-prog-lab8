package ru.rudXson;

import ru.rudXson.base.*;
import ru.rudXson.exceptions.ExitException;

import javax.naming.NoPermissionException;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    /**
     * Main method that is responsible for running the program.
     *
     * @param args Command line arguments that specify the name of the input file.
     * @throws IOException If there is an error reading or writing to the file.
     */
    public static void main(String[] args) throws NoPermissionException, IOException, ExitException {
        Scanner scanner = new Scanner(System.in);

        String fileName;
        if (args.length < 1) {
            System.out.println("No file name provided.");
            System.out.print("Please enter file name: ");
            fileName = scanner.nextLine();
        } else {
            fileName = args[0];
        }

        CLIController controller = new CLIController(fileName, scanner);
        CommandExecutor go = new CommandExecutor(controller, scanner);
        CommandHandler handler = new CommandHandler(go);

        try {
            Runnable runnable = new ServerCommandExecutor(controller);
            Thread thread = new Thread(runnable);
            thread.start();

            var server = new Server(new InetSocketAddress(InetAddress.getLocalHost(), 1488), handler);
            server.run();
        } catch (SocketException e) {
            System.out.println("Случилась ошибка сокета");
        } catch (UnknownHostException e) {
            System.out.println("Неизвестный хост");
        }
    }
}
