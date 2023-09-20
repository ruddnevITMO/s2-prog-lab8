package ru.rudXson.l10n;

import java.util.ListResourceBundle;

public class Lab_fin extends ListResourceBundle {
    private final Object[][] contents = {
            {"welcome", "Hei"},
            {"buttons.add", "Lisää"},
            {"buttons.addIfMin", "Lisää jos Min"},
            {"buttons.clear", "Kirkas"},
            {"buttons.executeScript", "Suorita skripti"},
            {"buttons.exit", "Poistu"},
            {"buttons.info", "Info"},
            {"buttons.printDescending", "Tulosta laskevasti"},
            {"buttons.printFieldDescendingTransport", "Tulosta kenttä \"Transport\" alenevasti"},
            {"buttons.printUniqueHouse", "Tulosta ainutlaatuisia taloja"},
            {"buttons.removeByID", "Poista ID:n mukaan"},
            {"buttons.removeFirst", "Poista ensin"},
            {"buttons.removeGreater", "Poista suurempi"},
            {"buttons.update", "Päivitys"},
            {"buttons.game", "Peli"},

            {"entering1", "Siirrytään interaktiiviseen tilaan!"},
            {"entering2", "Sinun tulee kirjautua sisään tai rekisteröityä ensin."},
            {"notexist", "Tätä komentoa ei ole olemassa"},
            {"error", "Tapahtui virhe:"},
            {"ent", "Anna seuraavat tiedot"},
            {"name", "Nimi"},
            {"xcord", "X-koordinaatti (pienempi tai yhtä suuri kuin 314)"},
            {"ycord", "Y-koordinaatti (pienempi tai yhtä suuri kuin 314)"},
            {"area", "Alue"},
            {"numofrooms", "Huoneiden lukumäärä"},
            {"furnish", "Sisustaa (DESIGNER, EI OLE, HIENO, PIENI) tai (1, 2, 3, 4)."},
            {"view", "Näkymä (STREET, PARK, NORMAL, GOOD, TERRIBLE) tai (1, 2, 3, 4, 5)"},
            {"transport", "Kuljetus (FEW, NONE, NORMAL) tai (1, 2, 3)"},
            {"housename", "Talon nimi (tai ei mitään)"},
            {"year", "Vuosi (suurempi kuin 0)"},
            {"numoflifts", "Hissien lukumäärä (suurempi kuin 0)"},
            {"login", "Käyttäjätunnus"},
            {"password", "Salasana"},
            {"login.button", "Kirjaudu sisään"},
            {"register.button", "Rekisteröi"},
            {"wronglog", "Käyttäjätunnus ei saa olla alle 3 eikä yli 20 merkkiä, eikä se saa sisältää muita kuin aakkosnumeerisia merkkejä, alleviivausmerkkejä ja pisteitä, jälkimmäiset kaksi eivät saa olla vierekkäin eivätkä käyttäjätunnuksen alussa tai lopussa."},
            {"trylog", "Anna oikea käyttäjätunnus"},
            {"wrongpass", "Se ei voi olla salasana. Muista, että salasanassasi on oltava vähintään 8 merkkiä (ja enintään 50), yksi kirjain ja yksi numero."},
            {"trypass", "Anna oikea salasana"},
            {"tab.table", "Taulukko"},
            {"tab.3dFrame", "3D-kehys"},
            {"column.id", "ID"},
            {"column.createdBy", "Luonut"},
            {"column.name", "Nimi"},
            {"column.coordinatesX", "Koordinaatit X"},
            {"column.coordinatesY", "Koordinaatit Y"},
            {"column.creationDate", "Luontipäivämäärä"},
            {"column.numberOfRooms", "Huoneiden lukumäärä"},
            {"column.furnish", "Furnish"},
            {"column.view", "Näytä"},
            {"column.transport", "Liikenne"},
            {"column.houseName", "Talon nimi"},
            {"column.houseYear", "Talo Vuosi"},
            {"column.houseNumberOfLifts", "Talo: Hissin määrä"},
            {"column.area", "Alue"},
            {"language.russian", "Venäläinen"},
            {"language.finnish", "Suomalainen"},
            {"language.lithuanian", "Liettuan"},
            {"language.englishIreland", "Englanti (Irlanti)"},
            {"titles.login", "Kirjaudu sisään"},
            {"titles.register", "Rekisteröi"},
            {"titles.main", "JavaBNB Sovellus"},
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}

