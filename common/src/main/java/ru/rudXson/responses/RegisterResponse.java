package ru.rudXson.responses;

public class RegisterResponse extends Response {
    public RegisterResponse(String error) {
        super("register", error);
    }
}
