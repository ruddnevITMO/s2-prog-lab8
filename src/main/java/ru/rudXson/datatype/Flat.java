package ru.rudXson.datatype;

import java.util.Date;
import java.util.UUID;

public class Flat implements Comparable<Flat> {
    private UUID id; // Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; // Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; // Поле не может быть null
    private Date creationDate; // Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float area; // Значение поля должно быть больше 0
    private long numberOfRooms; // Значение поля должно быть больше 0
    private Furnish furnish; // Поле может быть null
    private View view; // Поле может быть null
    private Transport transport; // Поле не может быть null
    private House house; // Поле может быть null

    public Flat(String name, Coordinates coordinates, float area, long numberOfRooms, Furnish furnish, View view, Transport transport, House house) {
        setId();
        setName(name);
        setCoordinates(coordinates);
        setCreationDate();
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

    private void setId() {
        this.id = UUID.randomUUID();
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
        return "Flat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", area=" + area +
                ", numberOfRooms=" + numberOfRooms +
                ", furnish=" + furnish +
                ", view=" + view +
                ", transport=" + transport +
                ", house=" + house +
                '}';
    }

    @Override
    public int compareTo(Flat other) {
        return this.id.compareTo(other.id);
    }
}
