package ru.rudXson.base;

import com.google.gson.JsonSyntaxException;


import ru.rudXson.datatype.*;
import ru.rudXson.exceptions.WrongArgsException;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * The CLIController class represents a controller for a command-line interface that manages a collection of flats.
 * It stores the name of the file containing the flats, a priority queue of flats, a scanner for user input, and the creation date of the flats.
 */
public class CLIController {

    private String fileName;
    private PriorityQueue<Flat> flats;
    private final Scanner scanner;
    private Date creationDate;


    /**
     * Constructs a CLIController object with the given command-line arguments.
     *
     * @param args the command-line arguments
     * @throws IOException if there was an error reading the file
     * @throws NoPermissionException if the user does not have permission to access the file
     */
    public CLIController(String[] args) throws IOException, NoPermissionException {
        this.scanner = new Scanner(System.in);

        // Check if argument is provided
        if (args.length < 1) {
            System.out.println("No file name provided.");
            System.out.print("Please enter file name: ");
            this.fileName = this.scanner.nextLine();
        } else {
            this.fileName = args[0];
        }

        // Check if file exists and has write access
        while (true) {
            try {
                FileValidator.checkFile(this.fileName);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: You don't have permission to access the file or it doesn't exist.");
                System.out.print("Please enter another file name: ");
                this.fileName = this.scanner.nextLine();
                FileValidator.checkFile(this.fileName);
            }
        }

        // Deserialize the file and store the data in a priority queue
        while (true) {
            try {
                this.flats = Deserializer.deserialize(this.fileName);
                break;
            } catch (IOException e) {
                System.out.println("Error: Unable to read the file.");
                System.out.print("Please enter another file name: ");
                this.fileName = this.scanner.nextLine();
                this.flats = Deserializer.deserialize(this.fileName);
            } catch (JsonSyntaxException e) {
                System.out.println("Error: Malformed JSON file.");
                System.out.print("Please enter another file name: ");
                this.fileName = this.scanner.nextLine();
                this.flats = Deserializer.deserialize(this.fileName);
            }
        }

        creationDate = this.flats.peek().getCreationDate();;

    }

    /**
     * Adds a flat to the priority queue of flats.
     *
     * @param flat the flat to add
     */
    public void addFlat(Flat flat) {
        flats.add(flat);
    }


    /**
     * Returns the name of the file containing the flats.
     *
     * @return the name of the file
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the name of the file containing the flats.
     *
     * @param fileName the name of the file
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Returns the priority queue of flats.
     *
     * @return the priority queue of flats
     */
    public PriorityQueue<Flat> getFlats() {
        return flats;
    }

    /**
     * Returns the flat with the given ID, or null if there is no such flat.
     *
     * @param id the ID of the flat to find
     * @return the flat with the given ID, or null if there is no such flat
     */
    public Flat getFlatByID(UUID id) throws WrongArgsException {
        for (Flat flat : flats) {
            if (Objects.equals(id.toString(), flat.getId().toString())) {
                return flat;
            }
        }
        throw new WrongArgsException("There is no element with such UUID");
    }

    /**
     * Removes the flat with the given ID from the priority queue of flats.
     *
     * @param id the ID of the flat to remove
     */
    public void removeFlatByID(UUID id) throws WrongArgsException {
        flats.remove(getFlatByID(id));
    }

    /**
     * Returns the scanner used for user input.
     *
     * @return the scanner used for user input
     */
    public Scanner getScanner() {
        return this.scanner;
    }

    /**
     * Returns the creation date of the flats in the format "dd.MM.yyyy HH:mm:ss".
     *
     * @return the creation date of the flats
     */
    public String getCreationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return this.creationDate.toInstant().atZone(ZoneId.systemDefault()).format(formatter);
    }

}



