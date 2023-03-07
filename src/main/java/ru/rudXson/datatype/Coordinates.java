package ru.rudXson.datatype;

public class Coordinates {
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
}
