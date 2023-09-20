package ru.rudXson.base;

import ru.rudXson.datatype.*;
import ru.rudXson.exceptions.WrongArgsException;
import ru.rudXson.responses.LoginResponse;
import ru.rudXson.responses.RegisterResponse;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.DriverManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class SQLController {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private final ReentrantLock lock = new ReentrantLock();
    private final String DBUrl;
    private final String DBUser;
    private final String DBPassword;

    private final PriorityQueue<Flat> flats = new PriorityQueue<>();

    public Connection connection;

    public SQLController(String DBUrl, String DBUser, String DBPassword) throws SQLException {
        this.DBUrl = DBUrl;
        this.DBUser = DBUser;
        this.DBPassword = DBPassword;

        this.connection = this.connect();
        fetchFlats();
    }

    public Connection connect() throws SQLException {
        Connection connection;
        connection = DriverManager.getConnection("jdbc:postgresql://" + DBUrl, DBUser, DBPassword);
        System.out.println("Connected to the PostgreSQL server successfully.");
        return connection;
    }

    public void fetchFlats() {
        lock.lock();

        this.flats.clear();
        String query = "SELECT *, (SELECT username FROM users WHERE id=created_by) FROM flats";
        try (Statement statement = this.connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                String name = rs.getString("name");
                int coordinatesId = rs.getInt("coordinates_id");

                ResultSet coordinatesResult = this.connection.createStatement().executeQuery("SELECT X, Y FROM coordinates WHERE ID = '" + coordinatesId + "'");
                Coordinates coordinates = null;
                while (coordinatesResult.next()) {
                    coordinates = new Coordinates(coordinatesResult.getFloat("x"), coordinatesResult.getFloat("y"));
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssX");
                Date creationDate = dateFormat.parse(rs.getString("creation_date"));
                String createdBy = rs.getString("username");
                float area = rs.getFloat("area");
                int numberOfRooms = rs.getInt("number_of_rooms");

                Furnish furnish;
                String furnishDB = rs.getString("furnish");
                if (furnishDB == null) {
                    furnish = null;
                } else {
                    furnish = Furnish.valueOf(furnishDB);
                }

                View view;
                String viewDB = rs.getString("view");
                if (viewDB == null) {
                    view = null;
                } else {
                    view = View.valueOf(viewDB);
                }

                Transport transport = Transport.valueOf(rs.getString("transport"));
                int houseId = rs.getInt("house_id");
                ResultSet housesResult = this.connection.createStatement().executeQuery("SELECT * FROM houses WHERE ID = '" + houseId + "'");
                House house = null;
                while (housesResult.next()) {
                    house = new House(housesResult.getString("name"), housesResult.getInt("year"), housesResult.getInt("number_of_lifts"));
                }
                Flat flat = new Flat(createdBy, id, name, coordinates, creationDate, area, numberOfRooms, furnish, view, transport, house);

                this.flats.add(flat);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        lock.unlock();
    }

    /**
     * Adds a flat to the priority queue of flats.
     *
     * @param flat the flat to add
     */
    public void addFlat(Flat flat, String username) {
        lock.lock();

        int coordinatesId = 0;
        int houseId = 0;


        flat.setCreatedBy(username);
        String coordinatesQuery = "INSERT INTO coordinates(x, y)" +
                " VALUES " +
                "(" + flat.getCoordinates().getX() +
                ", " + flat.getCoordinates().getY() +
                ") RETURNING id";
        try (Statement statement = this.connection.createStatement()) {
            ResultSet rs = statement.executeQuery(coordinatesQuery);
            while (rs.next()) {
                coordinatesId = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        String houseQuery = "INSERT INTO houses(name, year, number_of_lifts)" +
                " VALUES " +
                "('" + flat.getHouse().getName() +
                "', " + flat.getHouse().getYear() +
                ", " + flat.getHouse().getNumberOfLifts() +
                ") RETURNING id";
        try (Statement statement = this.connection.createStatement()) {
            ResultSet rs = statement.executeQuery(houseQuery);
            while (rs.next()) {
                houseId = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        String query = "INSERT INTO flats(name, coordinates_id, creation_date, created_by, " +
                "area, number_of_rooms, furnish, view, transport, house_id)" +
                " VALUES " +
                "('" + flat.getName() +
                "', " + coordinatesId +
                ", '" + flat.getCreationDate() +
                "', (select id from users where username='" + username + "')" +
                ", " + flat.getArea() +
                ", " + flat.getNumberOfRooms() +
                ", " + flat.getFurnish() +
                ", " + flat.getView() +
                ", " + flat.getTransport() +
                ", " + houseId +
                ") RETURNING id";
        try (Statement statement = this.connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                flat.setId(UUID.fromString(rs.getString("id")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        flats.add(flat);

        lock.unlock();
    }

    /**
     * Returns the priority queue of flats.
     *
     * @return the priority queue of flats
     */
    public PriorityQueue<Flat> getFlats() {
        return flats;
    }

    public PriorityQueue<Flat> getSelfFlats(String username) {
        PriorityQueue<Flat> selfFlats = new PriorityQueue<>();
        for (Flat flat : flats) {
            if (Objects.equals(flat.getCreatedBy(), username)) selfFlats.add(flat);
        }
        return selfFlats;
    }

    /**
     * Returns the flat with the given ID, or null if there is no such flat.
     *
     * @param id the ID of the flat to find
     * @return the flat with the given ID, or null if there is no such flat
     */
    public Flat getFlatByID(UUID id) throws WrongArgsException {
        for (Flat flat : flats) {
            if (Objects.equals(id.toString(), flat.getId().toString())) {
                return flat;
            }
        }
        throw new WrongArgsException("There is no element with such UUID");
    }

    public void replaceFlatById(UUID id, Flat newFlat, String username) throws WrongArgsException, SQLException {
        lock.lock();

        newFlat.setCreatedBy(username);
        newFlat.setId(id);
        newFlat.setCreationDate(getFlatByID(id).getCreationDate());
        int coordinatesId = 0;
        int houseId = 0;

        Statement statement;
        statement = this.connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT coordinates_id, house_id FROM flats WHERE id='" + id + "' AND CREATED_BY=(SELECT id FROM users WHERE username='" + username + "')");

        if (!rs.next()) {
            System.out.println("Something has gone wrong with local collection");
        } else {
            do {
                houseId = rs.getInt("house_id");
                coordinatesId = rs.getInt("coordinates_id");
            } while (rs.next());
        }


        String coordinatesQuery = "UPDATE coordinates SET" +
                " x=" + newFlat.getCoordinates().getX() +
                ", y=" + newFlat.getCoordinates().getY() +
                " WHERE id=" + coordinatesId;
        statement = this.connection.createStatement();
        statement.executeUpdate(coordinatesQuery);

        String housesQuery = "UPDATE houses SET" +
                " name='" + newFlat.getHouse().getName() +
                "', year=" + newFlat.getHouse().getYear() +
                ", number_of_lifts=" + newFlat.getHouse().getNumberOfLifts() +
                " WHERE id=" + houseId;
        statement = this.connection.createStatement();
        statement.executeUpdate(housesQuery);

        String query = "UPDATE flats SET" +
                " name='" + newFlat.getName() +
                "', area=" + newFlat.getArea() +
                ", number_of_rooms=" + newFlat.getNumberOfRooms() +
                ", furnish=" + newFlat.getFurnish().toString() +
                ", view=" + newFlat.getView().toString() +
                ", transport=" + newFlat.getTransport() +
                " WHERE id='" + newFlat.getId() + "'";
        statement = this.connection.createStatement();
        statement.executeUpdate(query);


        flats.remove(getFlatByID(id));
        flats.add(newFlat);

        lock.unlock();
    }

    /**
     * Removes the flat with the given ID from the priority queue of flats.
     *
     * @param id the ID of the flat to remove
     */
    public void removeFlatByID(UUID id, String username) throws WrongArgsException {
        lock.lock();
        String coordinates_id = null;
        String house_id = null;

        String query = "DELETE FROM flats" +
                " WHERE id='" + id +
                "' AND created_by=(SELECT id FROM users WHERE username='" + username + "') RETURNING coordinates_id, house_id";
        try (Statement statement = this.connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            coordinates_id = rs.getString("coordinates_id");
            house_id = rs.getString("house_id");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try (Statement statement = this.connection.createStatement()) {
            statement.executeUpdate("DELETE FROM coordinates WHERE id='" + coordinates_id + "'");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try (Statement statement = this.connection.createStatement()) {
            statement.executeUpdate("DELETE FROM houses WHERE id='" + house_id + "'");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        flats.remove(getFlatByID(id));

        lock.unlock();
    }


    public void clearCollection(String username) {
        lock.lock();

        String query = "SELECT id FROM flats WHERE created_by=(SELECT id FROM users WHERE username='" + username + "')";
        try (Statement statement = this.connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                removeFlatByID(UUID.fromString(rs.getString("id")), username);
            }
        } catch (SQLException | WrongArgsException e) {
            System.out.println(e.getMessage());
        }
        fetchFlats();

        lock.unlock();
    }


    public LoginResponse verifyCreds(String username, String password) {
        String passHash = null;
        String salt = null;

        try (Statement statement = this.connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT pass, salt FROM users WHERE username='" + username + "'");
            while (rs.next()) {
                passHash = rs.getString("pass").replace("-", "");
                salt = rs.getString("salt");
            }
        } catch (SQLException e) {
            return new LoginResponse("There is no such user");
        }


        String hashedPass = hashMD5(password, salt);

        if (!Objects.equals(hashedPass, passHash)) return new LoginResponse("Wrong password. Try again.");

        return new LoginResponse(null);
    }

    public RegisterResponse register(String username, String password) {
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(10);
        String alphanumerics = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < 10; i++) sb.append(alphanumerics.charAt(rnd.nextInt(alphanumerics.length())));
        String salt = sb.toString();

        String hashedPass = hashMD5(password, salt);


        String query = "INSERT INTO users(username, pass, salt) VALUES " +
                "('" + username +
                "', '" + hashedPass +
                "', '" + salt + "')";
        try (Statement statement = this.connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new RegisterResponse("This username already exists.");
        }

        return new RegisterResponse(null);
    }

    public String hashMD5(String password, String salt) {
        String passWithSalt = password + salt;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ignored) {
        }
        byte[] messageDigest = Objects.requireNonNull(md).digest(passWithSalt.getBytes());
        BigInteger number = new BigInteger(1, messageDigest);
        return number.toString(16);
    }

    /**
     * Returns the creation date of the flats in the format "dd.MM.yyyy HH:mm:ss".
     *
     * @return the creation date of the flats
     */
    public String getCreationDate() {
        if (!flats.isEmpty()) {
            return this.flats.peek().getCreationDate().toInstant().atZone(ZoneId.systemDefault()).format(this.formatter);
        } else {
            return "25th hour";
        }

    }


}



