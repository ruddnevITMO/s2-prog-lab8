package ru.rudXson.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class SQLController {


    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");


    private final String url;
    private final String user;
    private final String password;
    public Connection connection;

    public SQLController(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;

        this.connection = this.connect();
    }

    public Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return connection;
    }
}



