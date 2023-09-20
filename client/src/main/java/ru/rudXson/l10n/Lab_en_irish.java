package ru.rudXson.l10n;

import java.util.ListResourceBundle;

public class Lab_en_irish extends ListResourceBundle {
    private final Object[][] contents = {
            {"welcome", "Hello"},
            {"buttons.add", "Add"},
            {"buttons.addIfMin", "Add if Min"},
            {"buttons.clear", "Clear"},
            {"buttons.executeScript", "Execute script"},
            {"buttons.exit", "Exit"},
            {"buttons.info", "Info"},
            {"buttons.printDescending", "Print descending"},
            {"buttons.printFieldDescendingTransport", "Print field \"Transport\" descending"},
            {"buttons.printUniqueHouse", "Print unique houses"},
            {"buttons.removeByID", "Remove by ID"},
            {"buttons.removeFirst", "Remove first"},
            {"buttons.removeGreater", "Remove greater"},
            {"buttons.update", "Update"},
            {"buttons.game", "Game"},

            {"entering1", "Entered the interactive mode!"},
            {"entering2", "You should login or register first."},
            {"notexist", "This command doesn't exist"},
            {"error", "An error occurred:"},
            {"ent", "Please enter the following information"},
            {"name", "Name"},
            {"xcord", "X coordinate (less than or equal to 314)"},
            {"ycord", "Y coordinate (less than or equal to 314)"},
            {"area", "Area"},
            {"numofrooms", "Number of rooms"},
            {"furnish", "Furnish (DESIGNER, NONE, FINE, LITTLE) or (1, 2, 3, 4)"},
            {"view", "View (STREET, PARK, NORMAL, GOOD, TERRIBLE) or (1, 2, 3, 4, 5)"},
            {"transport", "Transport (FEW, NONE, NORMAL) or (1, 2, 3)"},
            {"housename", "House name (or enter nothing)"},
            {"year", "Year (greater than 0)"},
            {"numoflifts", "Number of lifts (greater than 0)"},
            {"login", "Username"},
            {"password", "Password"},
            {"login.button", "Login"},
            {"register.button", "Register"},
            {"wronglog", "Username shouldn't be less than 3 or more than 20 characters, contain anything other than alphanumeric characters, underscores and dots, latter two should not be next to each other nor be at the start or end of the username."},
            {"trylog", "Please enter proper username"},
            {"wrongpass", "Can't have that as a password. Remember, your password needs to have at least 8 characters (and at most 50), one letter and one number."},
            {"trypass", "Please enter proper password"},
            {"tab.table", "Flats"},
            {"tab.3dFrame", "3D Frame"},
            {"column.id", "ID"},
            {"column.createdBy", "Created by"},
            {"column.name", "Name"},
            {"column.coordinatesX", "Coordinates X"},
            {"column.coordinatesY", "Coordinates Y"},
            {"column.creationDate", "Creation date"},
            {"column.numberOfRooms", "Number of rooms"},
            {"column.furnish", "Furnish"},
            {"column.view", "View"},
            {"column.transport", "Transport"},
            {"column.houseName", "House Name"},
            {"column.houseYear", "House Year"},
            {"column.houseNumberOfLifts", "House: Lift count"},
            {"column.area", "Area"},
            {"language.russian", "Russian"},
            {"language.finnish", "Finnish"},
            {"language.lithuanian", "Lithuanian"},
            {"language.englishIreland", "English (Ireland)"},
            {"titles.login", "Login"},
            {"titles.register", "Register"},
            {"titles.main", "JavaBNB App"},

    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}

