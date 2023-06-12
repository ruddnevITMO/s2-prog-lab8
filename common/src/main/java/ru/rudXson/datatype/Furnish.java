package ru.rudXson.datatype;

import java.io.Serializable;

public enum Furnish implements Serializable {
    DESIGNER,
    NONE,
    FINE,
    LITTLE;

    @Override
    public String toString() {
        return "'" + this.name() + "'";
    }
}
