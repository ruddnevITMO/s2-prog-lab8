package ru.rudXson.base;

import ru.rudXson.datatype.*;

import java.util.Scanner;

public class InputManager {
    Scanner scanner;
    
    public InputManager(Scanner scanner) {
        this.scanner = scanner;
    }
    
    public Flat describeFlat(Flat flat) {
        System.out.println("Please enter the following information:");
        System.out.print("Name: ");
        flat.setName(readNonEmptyString());

        System.out.print("X coordinate (less than or equal to 314): ");
        int x = readCoordinate();

        System.out.print("Y coordinate (less than or equal to 314): ");
        int y = readCoordinate();

        flat.setCoordinates(new Coordinates(x, y));

        System.out.print("Area: ");
        flat.setArea(readPositiveFloat());

        System.out.print("Number of rooms: ");
        flat.setNumberOfRooms(readPositiveLong());

        System.out.print("Furnish (DESIGNER, NONE, FINE, LITTLE): ");
        flat.setFurnish(readFurnish());

        System.out.print("View (STREET, PARK, NORMAL, GOOD, TERRIBLE): ");
        flat.setView(readView());

        System.out.print("Transport (FEW, NONE, NORMAL): ");
        flat.setTransport(readTransport());

        System.out.print("House name (or enter nothing): ");
        String houseName = readNullableString();

        System.out.print("Year (greater than 0): ");
        int year = readPositiveInt();

        System.out.print("Number of lifts (greater than 0): ");
        int numberOfLifts = readPositiveInt();

        flat.setHouse(new House(houseName, year, numberOfLifts));

        return flat;
    }

    private String readNonEmptyString() {
        String input = this.scanner.nextLine().trim();
        while (input.isEmpty()) {
            System.out.print("Please enter a non-empty string: ");
            input = this.scanner.nextLine().trim();
        }
        return input;
    }

    private int readCoordinate() {
        int coordinate = this.scanner.nextInt();
        while (coordinate > 314) {
            System.out.print("Coordinate can't be greater than 314. Please enter again: ");
            coordinate = this.scanner.nextInt();
        }
        this.scanner.nextLine(); // consume the newline character
        return coordinate;
    }

    private float readPositiveFloat() {
        float value = this.scanner.nextFloat();
        while (value <= 0) {
            System.out.print("Please enter a positive float: ");
            value = this.scanner.nextFloat();
        }
        this.scanner.nextLine(); // consume the newline character
        return value;
    }

    private long readPositiveLong() {
        long value = this.scanner.nextLong();
        while (value <= 0) {
            System.out.print("Please enter a positive long: ");
            value = this.scanner.nextLong();
        }
        this.scanner.nextLine(); // consume the newline character
        return value;
    }

    private Furnish readFurnish() {
        String input = this.scanner.nextLine().toUpperCase().trim();
        Furnish furnish = null;
        while (furnish == null) {
            try {
                furnish = Enum.valueOf(Furnish.class, input);
            } catch (IllegalArgumentException e) {
                System.out.print("Please enter a valid Furnish (DESIGNER, NONE, FINE, LITTLE): ");
                input = this.scanner.nextLine().toUpperCase().trim();
            }
        }
        return furnish;
    }

    private View readView() {
        String input = this.scanner.nextLine().toUpperCase().trim();
        View view = null;
        while (view == null) {
            try {
                view = Enum.valueOf(View.class, input);
            } catch (IllegalArgumentException e) {
                System.out.print("Please enter a valid View (STREET, PARK, NORMAL, GOOD, TERRIBLE): ");
                input = this.scanner.nextLine().toUpperCase().trim();
            }
        }
        return view;
    }

    private Transport readTransport() {
        String input = this.scanner.nextLine().toUpperCase().trim();
        Transport transport = null;
        while (transport == null) {
            try {
                transport = Enum.valueOf(Transport.class, input);
            } catch (IllegalArgumentException e) {
                System.out.print("Please enter a valid Transport (FEW, NONE, NORMAL): ");
                input = this.scanner.nextLine().toUpperCase().trim();
            }
        }
        return transport;
    }


    private String readNullableString() {
        String input = this.scanner.nextLine().trim();
        if (input.isEmpty()) {
            return null;
        }
        return input;
    }

    private int readPositiveInt() {
        int value = this.scanner.nextInt();
        while (value <= 0) {
            System.out.print("Please enter a positive integer: ");
            value = this.scanner.nextInt();
        }
        this.scanner.nextLine(); // consume the newline character
        return value;
    }
}
