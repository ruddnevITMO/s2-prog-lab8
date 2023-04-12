package ru.rudXson.commands;

import java.nio.charset.CoderResult;
import java.util.HashMap;
import java.util.Scanner;

import ru.rudXson.base.CLIController;
import ru.rudXson.datatype.*;

public class Add implements Command {
    CLIController c;

    public Add(CLIController c) {
        this.c = c;
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


        c.addFlat(new Flat(name, coordinates, area, numberOfRooms, furnish, view, transport, house));

    }

    @Override
    public String getDescription() {
        return "Adds an element to the collection";
    }

    private String readNonEmptyString() {
        String input = c.getScanner().nextLine().trim();
        while (input.isEmpty()) {
            System.out.print("Please enter a non-empty string: ");
            input = c.getScanner().nextLine().trim();
        }
        return input;
    }

    private int readCoordinate() {
        int coordinate = c.getScanner().nextInt();
        while (coordinate > 314) {
            System.out.print("Coordinate can't be greater than 314. Please enter again: ");
            coordinate = c.getScanner().nextInt();
        }
        c.getScanner().nextLine(); // consume the newline character
        return coordinate;
    }

    private float readPositiveFloat() {
        float value = c.getScanner().nextFloat();
        while (value <= 0) {
            System.out.print("Please enter a positive float: ");
            value = c.getScanner().nextFloat();
        }
        c.getScanner().nextLine(); // consume the newline character
        return value;
    }

    private long readPositiveLong() {
        long value = c.getScanner().nextLong();
        while (value <= 0) {
            System.out.print("Please enter a positive long: ");
            value = c.getScanner().nextLong();
        }
        c.getScanner().nextLine(); // consume the newline character
        return value;
    }

    private Furnish readFurnish() {
        String input = c.getScanner().nextLine().toUpperCase().trim();
        Furnish furnish = null;
        while (furnish == null) {
            try {
                furnish = Enum.valueOf(Furnish.class, input);
            } catch (IllegalArgumentException e) {
                System.out.print("Please enter a valid Furnish (DESIGNER, NONE, FINE, LITTLE): ");
                input = c.getScanner().nextLine().toUpperCase().trim();
            }
        }
        return furnish;
    }

    private View readView() {
        String input = c.getScanner().nextLine().toUpperCase().trim();
        View view = null;
        while (view == null) {
            try {
                view = Enum.valueOf(View.class, input);
            } catch (IllegalArgumentException e) {
                System.out.print("Please enter a valid View (STREET, PARK, NORMAL, GOOD, TERRIBLE): ");
                input = c.getScanner().nextLine().toUpperCase().trim();
            }
        }
        return view;
    }

    private Transport readTransport() {
        String input = c.getScanner().nextLine().toUpperCase().trim();
        Transport transport = null;
        while (transport == null) {
            try {
                transport = Enum.valueOf(Transport.class, input);
            } catch (IllegalArgumentException e) {
                System.out.print("Please enter a valid Transport (FEW, NONE, NORMAL): ");
                input = c.getScanner().nextLine().toUpperCase().trim();
            }
        }
        return transport;
    }


    private String readNullableString() {
        String input = c.getScanner().nextLine().trim();
        if (input.isEmpty()) {
            return null;
        }
        return input;
    }

    private int readPositiveInt() {
        int value = c.getScanner().nextInt();
        while (value <= 0) {
            System.out.print("Please enter a positive integer: ");
            value = c.getScanner().nextInt();
        }
        c.getScanner().nextLine(); // consume the newline character
        return value;
    }
}
