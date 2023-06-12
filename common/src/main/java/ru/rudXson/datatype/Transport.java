package ru.rudXson.datatype;

import java.io.Serializable;

public enum Transport implements Serializable {
    FEW,
    NONE,
    NORMAL;

    @Override
    public String toString() {
        return "'" + this.name() + "'";
    }
}