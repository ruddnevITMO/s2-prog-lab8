package ru.rudXson.l10n;

import java.util.ListResourceBundle;

public class Lab_lit extends ListResourceBundle {
    private final Object[][] contents = {
            {"welcome", "Sveiki"},
            {"buttons.add", "Pridėti"},
            {"buttons.addIfMin", "Pridėti, jei Min"},
            {"buttons.clear", "Skaidrus"},
            {"buttons.executeScript", "Vykdyti scenarijų"},
            {"buttons.exit", "Išeiti"},
            {"buttons.info", "Informacija"},
            {"buttons.printDescending", "Spausdinti mažėjančia tvarka"},
            {"buttons.printFieldDescendingTransport", "Spausdinti lauką \"Transportas\" mažėjančia tvarka"},
            {"buttons.printUniqueHouse", "Spausdinti unikalius namus"},
            {"buttons.removeByID", "Pašalinti pagal ID"},
            {"buttons.removeFirst", "Pirmiausia pašalinkite"},
            {"buttons.removeGreater", "Pašalinti didesnį"},
            {"buttons.update", "Atnaujinti"},
            {"buttons.game", "Žaidimas"},

            {"entering1", "Įvestas interaktyvusis režimas!"},
            {"entering2", "Pirmiausia turėtumėte prisijungti arba užsiregistruoti."},
            {"notexist", "Ši komanda neegzistuoja"},
            {"error", "Įvyko klaida:"},
            {"ent", "Įveskite šią informaciją"},
            {"name", "Vardas ir pavardė"},
            {"xcord", "X koordinatė (mažesnė arba lygi 314)"},
            {"ycord", "Y koordinatė (mažesnė arba lygi 314)"},
            {"area", "Teritorija"},
            {"numofrooms", "Kambarių skaičius"},
            {"furnish", "Įrengti (DIZAINERIS, NĖRA, DIDELIS, MAŽAS) arba (1, 2, 3, 4)"},
            {"view", "Vaizdas (gatvė, parkas, normalus, geras, blogas) arba (1, 2, 3, 4, 5)"},
            {"transport", "Transportavimas (MAŽAI, NIEKADA, NORMALUS) arba (1, 2, 3)"},
            {"housename", "Namo pavadinimas (arba nieko neįveskite)"},
            {"year", "Metai (didesni nei 0)"},
            {"numoflifts", "Liftų skaičius (didesnis nei 0)"},
            {"login", "Vartotojo vardą"},
            {"password", "Slaptažodį"},
            {"login.button", "Prisijungimas"},
            {"register.button", "Registruoti"},
            {"wronglog", "Vartotojo vardas neturi būti trumpesnis nei 3 ir ilgesnis nei 20 simbolių, jame turi būti tik raidiniai skaitmeniniai simboliai, pabraukimai ir taškai, pastarieji du simboliai neturi būti vienas šalia kito ir neturi būti vartotojo vardo pradžioje ar pabaigoje."},
            {"trylog", "Įveskite tinkamą vartotojo vardą"},
            {"wrongpass", "Negalima to naudoti kaip slaptažodžio. Atminkite, kad slaptažodį turi sudaryti ne mažiau kaip 8 simboliai (ir ne daugiau kaip 50), viena raidė ir vienas skaičius."},
            {"trypass", "Įveskite tinkamą slaptažodį"},
            {"tab.table", "Lentelė"},
            {"tab.3dFrame", "3D rėmelis"},
            {"column.id", "ID"},
            {"column.createdBy", "Sukūrė"},
            {"column.name", "Pavadinimas"},
            {"column.coordinatesX", "Koordinatės X"},
            {"column.coordinatesY", "Koordinatės Y"},
            {"column.creationDate", "Sukūrimo data"},
            {"column.numberOfRooms", "Kambarių skaičius"},
            {"column.furnish", "Įrengti"},
            {"column.view", "Peržiūrėti"},
            {"column.transport", "Transportas"},
            {"column.houseName", "Namo pavadinimas"},
            {"column.houseYear", "Namas Metai"},
            {"column.houseNumberOfLifts", "Namas: Liftų skaičius"},
            {"column.area", "Plotas"},
            {"language.russian", "Rusų kalba"},
            {"language.finnish", "Suomijos"},
            {"language.lithuanian", "Lietuvių kalba"},
            {"language.englishIreland", "Anglų kalba (Airija)"},
            {"titles.login", "Prisijungimas"},
            {"titles.register", "Registruoti"},
            {"titles.main", "JavaBNB Programa"},
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}

