package ru.rudXson.base;

import ru.rudXson.datatype.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputManager {
    Scanner scanner;
    
    public InputManager(Scanner scanner) {
        this.scanner = scanner;
    }
    
    public void describeFlat(Flat flat) {
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
        int coordinate = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                coordinate = this.scanner.nextInt();
                if (coordinate > 314) {
                    System.out.print("Coordinate can't be greater than 314. Please enter again: ");
                } else {
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.print("Please enter a valid integer: ");
                this.scanner.nextLine();
            }
        }
        this.scanner.nextLine(); // consume the newline character
        return coordinate;
    }

    private float readPositiveFloat() {
        float value = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                value = this.scanner.nextFloat();
                if (value <= 0) {
                    System.out.print("Please enter a positive float: ");
                } else {
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.print("Please enter a valid float: ");
                this.scanner.nextLine();
            }
        }
        this.scanner.nextLine(); // consume the newline character
        return value;
    }

    private long readPositiveLong() {
        long value = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                value = this.scanner.nextLong();
                if (value <= 0) {
                    System.out.print("Please enter a positive long: ");
                } else {
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.print("Please enter a valid long: ");
                this.scanner.nextLine();
            }
        }
        this.scanner.nextLine(); // consume the newline character
        return value;
    }

    private Furnish readFurnish() {
        String input = this.scanner.nextLine().toUpperCase().trim();
        Furnish furnish = null;
        boolean validInput = false;
        while (!validInput) {
            try {
                furnish = Enum.valueOf(Furnish.class, input);
                validInput = true;
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
        boolean validInput = false;
        while (!validInput) {
            try {
                view = Enum.valueOf(View.class, input);
                validInput = true;
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
        boolean validInput = false;
        while (!validInput) {
            try {
                transport = Enum.valueOf(Transport.class, input);
                validInput = true;
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
        int value = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                value = this.scanner.nextInt();
                if (value <= 0) {
                    System.out.print("You have to enter a positive integer. Please enter again: ");
                } else {
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.print("Please enter a valid positive integer: ");
                this.scanner.nextLine();
            }
        }
        this.scanner.nextLine(); // consume the newline character
        return value;
    }
}
