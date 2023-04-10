package ru.rudXson.commands;

import java.nio.charset.CoderResult;
import java.util.HashMap;
import java.util.Scanner;
import ru.rudXson.datatype.*;

public class Add implements Command {
    HashMap<String, Command> commands;
    private final Scanner scanner;

    public Add(Scanner scanner, HashMap<String, Command> commands) {
        this.commands = commands;
        this.scanner = scanner;
    }

    @Override
    public void execute(String[] args) {
        System.out.println("Please enter the following information:");
        System.out.print("Name: ");
        String name = readNonEmptyString();

        System.out.print("X coordinate (less than or equal to 314): ");
        int x = readCoordinate();

        System.out.print("Y coordinate (less than or equal to 314): ");
        int y = readCoordinate();

        Coordinates coordinates = new Coordinates(x, y);

        System.out.print("Area: ");
        float area = readPositiveFloat();

        System.out.print("Number of rooms: ");
        long numberOfRooms = readPositiveLong();

        System.out.print("Furnish (DESIGNER, NONE, FINE, LITTLE): ");
        Furnish furnish = readFurnish();

        System.out.print("View (STREET, PARK, NORMAL, GOOD, TERRIBLE): ");
        View view = readView();

        System.out.print("Transport (FEW, NONE, NORMAL): ");
        Transport transport = readTransport();

        System.out.print("House name (or enter nothing): ");
        String houseName = readNullableString();

        System.out.print("Year (greater than 0): ");
        int year = readPositiveInt();

        System.out.print("Number of lifts (greater than 0): ");
        int numberOfLifts = readPositiveInt();

        House house = new House(houseName, year, numberOfLifts);


        Flat flat = new Flat(name, coordinates, area, numberOfRooms, furnish, view, transport, house);
    }

    @Override
    public String getDescription() {
        return "Adds an element to the collection";
    }

    private String readNonEmptyString() {
        String input = scanner.nextLine().trim();
        while (input.isEmpty()) {
            System.out.print("Please enter a non-empty string: ");
            input = scanner.nextLine().trim();
        }
        return input;
    }

    private int readCoordinate() {
        int coordinate = scanner.nextInt();
        while (coordinate > 314) {
            System.out.print("Coordinate can't be greater than 314. Please enter again: ");
            coordinate = scanner.nextInt();
        }
        scanner.nextLine(); // consume the newline character
        return coordinate;
    }

    private float readPositiveFloat() {
        float value = scanner.nextFloat();
        while (value <= 0) {
            System.out.print("Please enter a positive float: ");
            value = scanner.nextFloat();
        }
        scanner.nextLine(); // consume the newline character
        return value;
    }

    private long readPositiveLong() {
        long value = scanner.nextLong();
        while (value <= 0) {
            System.out.print("Please enter a positive long: ");
            value = scanner.nextLong();
        }
        scanner.nextLine(); // consume the newline character
        return value;
    }

    private Furnish readFurnish() {
        String input = scanner.nextLine().toUpperCase().trim();
        Furnish furnish = null;
        while (furnish == null) {
            try {
                furnish = Enum.valueOf(Furnish.class, input);
            } catch (IllegalArgumentException e) {
                System.out.print("Please enter a valid Furnish (DESIGNER, NONE, FINE, LITTLE): ");
                input = scanner.nextLine().toUpperCase().trim();
            }
        }
        return furnish;
    }

    private View readView() {
        String input = scanner.nextLine().toUpperCase().trim();
        View view = null;
        while (view == null) {
            try {
                view = Enum.valueOf(View.class, input);
            } catch (IllegalArgumentException e) {
                System.out.print("Please enter a valid View (STREET, PARK, NORMAL, GOOD, TERRIBLE): ");
                input = scanner.nextLine().toUpperCase().trim();
            }
        }
        return view;
    }

    private Transport readTransport() {
        String input = scanner.nextLine().toUpperCase().trim();
        Transport transport = null;
        while (transport == null) {
            try {
                transport = Enum.valueOf(Transport.class, input);
            } catch (IllegalArgumentException e) {
                System.out.print("Please enter a valid Transport (FEW, NONE, NORMAL): ");
                input = scanner.nextLine().toUpperCase().trim();
            }
        }
        return transport;
    }


    private String readNullableString() {
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return null;
        }
        return input;
    }

    private int readPositiveInt() {
        int value = scanner.nextInt();
        while (value <= 0) {
            System.out.print("Please enter a positive integer: ");
            value = scanner.nextInt();
        }
        scanner.nextLine(); // consume the newline character
        return value;
    }
}
