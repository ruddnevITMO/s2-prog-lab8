package ru.rudXson.base;

import ru.rudXson.datatype.Flat;
import ru.rudXson.exceptions.NoPermission;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CLIController {

    private String fileName;
    private PriorityQueue<Flat> flats;
    private final Scanner scanner;
    private LocalDateTime creationDate;
    public CLIController(String[] args) throws IOException, NoPermission {
        this.creationDate = LocalDateTime.now();
        this.scanner = new Scanner(System.in);

        // check if argument is provided
        if (args.length < 1) {
            System.out.println("No file name provided.");
            System.out.print("Please enter file name: ");
            this.fileName = this.scanner.nextLine();
        } else {
            this.fileName = args[0];
        }

        // Check if file exists and has write access
        FileValidator.checkFile(this.fileName);

        // Deserialize the file and store the data in a priority queue
        this.flats = Deserializer.deserialize(this.fileName);
        //TODO Error on this (ask for another file)
    }

    public void addFlat(Flat flat) {
        flats.add(flat);
    }


    // getters and setters
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public PriorityQueue<Flat> getFlats() {
        return flats;
    }

    public Flat getFlatByID(UUID id) {
        for (Flat flat : flats) {
            if (Objects.equals(id.toString(), flat.getId().toString())) {
                return flat;
            }
        }
        return null;
    }

    public Scanner getScanner() {
        return this.scanner;
    }

    public String getCreationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return creationDate.format(formatter);
    }
}
