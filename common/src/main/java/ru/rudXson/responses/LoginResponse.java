package ru.rudXson.responses;

public class LoginResponse extends Response {
    public LoginResponse(String error) {
        super("login", error);
    }
}
