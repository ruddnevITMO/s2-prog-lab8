package ru.rudXson.datatype;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private double x; // Максимальное значение поля: 314
    private double y; // Максимальное значение поля: 314

    public Coordinates(double x, double y) {
        setX(x);
        setY(y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        if (x > 314) {
            throw new IllegalArgumentException("x cannot be greater than 314");
        }
        this.x = x;
    }

    public void setY(double y) {
        if (y > 314) {
            throw new IllegalArgumentException("y cannot be greater than 314");
        }
        this.y = y;
    }
    @Override
    public String toString() {
        return "{\n" +
                "\t\t\t" + "\u001B[33m\"x\"\u001B[0m: " + "\u001B[36m" + x + "\u001B[0m,\n" +
                "\t\t\t" + "\u001B[33m\"y\"\u001B[0m: " + "\u001B[36m" + y + "\u001B[0m\n" +
                "\t\t}";
    }
}
