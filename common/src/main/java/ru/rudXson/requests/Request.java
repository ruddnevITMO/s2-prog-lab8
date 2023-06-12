package ru.rudXson.requests;

import java.io.Serializable;

public abstract class Request implements Serializable {
    public final String name;
    private String username;
    private String password;

    public Request(String name) {
        this.name = name;
    }

    public void setCreds(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}
