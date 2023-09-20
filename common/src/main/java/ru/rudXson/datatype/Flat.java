package ru.rudXson.datatype;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

public class Flat implements Comparable<Flat>, Serializable {
    private UUID id; // Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; // Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; // Поле не может быть null
    private Date creationDate; // Поле не может быть null, Значение этого поля должно генерироваться автоматически

    private String createdBy;
    private float area; // Значение поля должно быть больше 0
    private long numberOfRooms; // Значение поля должно быть больше 0
    private Furnish furnish; // Поле может быть null
    private View view; // Поле может быть null
    private Transport transport; // Поле не может быть null
    private House house; // Поле может быть null


    public Flat(){
        setCreationDate();
    }

    public Flat(String createdBy, UUID id, String name, Coordinates coordinates, Date creationDate, float area, long numberOfRooms, Furnish furnish, View view, Transport transport, House house) {
        this(createdBy, name, coordinates, area, numberOfRooms, furnish, view, transport, house);
        setId(id);
        setCreationDate(creationDate);
    }

    public Flat(String createdBy, String name, Coordinates coordinates, float area, long numberOfRooms, Furnish furnish, View view, Transport transport, House house) {
        setName(name);
        setCoordinates(coordinates);
        setCreationDate();
        setCreatedBy(createdBy);
        setArea(area);
        setNumberOfRooms(numberOfRooms);
        setFurnish(furnish);
        setView(view);
        setTransport(transport);
        setHouse(house);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
    public double getCoordinatesX() {
        return coordinates.getX();
    }
    public double getCoordinatesY() {
        return coordinates.getY();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public float getArea() {
        return area;
    }

    public long getNumberOfRooms() {
        return numberOfRooms;
    }

    public Furnish getFurnish() {
        return furnish;
    }

    public View getView() {
        return view;
    }

    public Transport getTransport() {
        return transport;
    }

    public House getHouse() {
        return house;
    }
    public String getHouseName() {
        return house.getName();
    }
    public int getHouseYear() {
        return house.getYear();
    }
    public int getHouseNumberOfLifts() {
        return house.getNumberOfLifts();
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Flat name cannot be null or empty");
        }
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("Coordinates cannot be null");
        }
        this.coordinates = coordinates;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    private void setCreationDate() {
        this.creationDate = new Date();
    }

    public void setArea(float area) {
        if (area <= 0) {
            throw new IllegalArgumentException("Area must be greater than 0");
        }
        this.area = area;
    }

    public void setNumberOfRooms(long numberOfRooms) {
        if (numberOfRooms <= 0) {
            throw new IllegalArgumentException("Number of rooms must be greater than 0");
        }
        this.numberOfRooms = numberOfRooms;
    }

    public void setFurnish(Furnish furnish) {
        this.furnish = furnish;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setTransport(Transport transport) {
        if (transport == null) {
            throw new IllegalArgumentException("Transport cannot be null");
        }
        this.transport = transport;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String result = "\n\t{\n";
        result += "\t\t" + "\u001B[33m\"id\"\u001B[0m: " + "\u001B[36m" + id + "\u001B[0m,\n";
        result += "\t\t" + "\u001B[33m\"owner\"\u001B[0m: " + "\u001B[34m" + createdBy + "\u001B[0m,\n";
        result += "\t\t" + "\u001B[33m\"name\"\u001B[0m: " + "\u001B[32m\"" + name + "\"\u001B[0m,\n";
        result += "\t\t" + "\u001B[33m\"coordinates\"\u001B[0m: " + coordinates + ",\n";
        result += "\t\t" + "\u001B[33m\"creationDate\"\u001B[0m: " + "\u001B[35m\"" + creationDate.toInstant().atZone(ZoneId.systemDefault()).format(formatter) + "\"\u001B[0m,\n";
        result += "\t\t" + "\u001B[33m\"area\"\u001B[0m: " + "\u001B[36m" + area + "\u001B[0m,\n";
        result += "\t\t" + "\u001B[33m\"numberOfRooms\"\u001B[0m: " + "\u001B[36m" + numberOfRooms + "\u001B[0m,\n";
        result += "\t\t" + "\u001B[33m\"furnish\"\u001B[0m: " + "\u001B[34m" + furnish + "\u001B[0m,\n";
        result += "\t\t" + "\u001B[33m\"view\"\u001B[0m: " + "\u001B[34m" + view + "\u001B[0m,\n";
        result += "\t\t" + "\u001B[33m\"transport\"\u001B[0m: " + "\u001B[34m" + transport + "\u001B[0m,\n";
        result += "\t\t" + "\u001B[33m\"house\"\u001B[0m: " + house + "\n";
        result += "\t" + "}\n";
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return this.hashCode() == ((Flat) obj).hashCode();
    }

    @Override
    public int hashCode() {
        return ("" + this.id.hashCode() + this.createdBy.hashCode() + this.name.hashCode() +
                this.coordinates.hashCode() + this.creationDate.hashCode() +
                this.area + this.numberOfRooms + this.furnish.hashCode() +
                this.view.hashCode() + this.transport.hashCode() + this.house.hashCode()).hashCode();
    }


    @Override
    public int compareTo(Flat other) {
        return this.creationDate.compareTo(other.creationDate);
    }
}
