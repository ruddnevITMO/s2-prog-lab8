package ru.rudXson;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javafx.util.Duration;
import javafx.util.Pair;
import ru.rudXson.base.Client;
import ru.rudXson.datatype.*;
import ru.rudXson.requests.*;
import ru.rudXson.responses.*;

public class Main extends Application {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private Stage primaryStage;
    private Scene loginScene;
    private Scene registerScene;
    private Scene mainScene;
    private String currLanguage = "language.englishIreland";
    private String currTimezone = "+1";
    private String currScene;
    private TableView<Flat> tableView = new TableView<>();
    private double refreshTime = 0.5;
    private Pane pane3D;
    private HBox languageSwitcher;
    private ResourceBundle bundle;
    private TabPane tabPane;
    private Client client;
    private UUID id;
    private PriorityQueue<Flat> flats = new PriorityQueue<>();
    private List<Pair<Flat, Box>> list;



    @Override
    public void start(Stage primaryStage) throws IOException {
        client = new Client(InetAddress.getLocalHost(), 1488);

        bundle = ResourceBundle.getBundle("ru.rudXson.l10n.Lab_en_irish");

        this.primaryStage = primaryStage;
        primaryStage.setResizable(false);

        createLoginScene();
        this.primaryStage.setTitle(bundle.getString("titles.login"));
        this.currScene = "loginScene";
        this.primaryStage.setScene(loginScene);
        this.primaryStage.show();

    }



    private void create3DFrame() {
        list = new ArrayList<>();

        Group group = new Group();
        group.getChildren().add(new MeshView());
        for (Flat flat : flats) {
            double areaSquared = Math.sqrt(flat.getArea());
            Box box = new Box(areaSquared, areaSquared, areaSquared/5);
            box.setMaterial(new PhongMaterial());
            box.setTranslateX(flat.getCoordinatesX());
            box.setTranslateY(flat.getCoordinatesY());
            box.setMaterial(new javafx.scene.paint.PhongMaterial(encodeStringToColor(flat.getCreatedBy())));
            list.add(new Pair<>(flat, box));
            group.getChildren().add(box);
        }
        Camera camera = new PerspectiveCamera(true);
        camera.setNearClip(1);
        camera.setFarClip(1000);
        camera.translateZProperty().set(-300);

        SubScene subScene = new SubScene(group, 1000, 400);
        subScene.setOnMouseClicked(this::handleMouseClicked);
        subScene.setFill(Color.LAVENDER);
        subScene.setCamera(camera);

        group.translateXProperty().set(0);
        group.translateYProperty().set(0);
        group.translateZProperty().set(0);

        initMouseControl(group, subScene);

        this.pane3D = new StackPane();
        this.pane3D.getChildren().add(subScene);

    }
    private void handleMouseClicked(MouseEvent event) {
        javafx.scene.Node pickedNode = event.getPickResult().getIntersectedNode();
        if (pickedNode instanceof Box) {
            for (Pair<Flat, Box> pair : list) {
                if (pair.getValue().equals(pickedNode)) {
                    Flat selectedFlat = pair.getKey();
                    if (!Objects.equals(selectedFlat.getCreatedBy(), client.getUsername())) {
                        runAlert(Alert.AlertType.ERROR, "You do not own that element, so you can't edit it");
                        return;
                    }
                    updateFlatFromTable(selectedFlat);
                }
            }
        }
    }

    Rotate xRotate;
    Rotate yRotate;
    double anchorX, anchorY;
    double anchorAngleX = 0;
    double anchorAngleY = 0;
    final DoubleProperty angleX = new SimpleDoubleProperty(0);
    final DoubleProperty angleY = new SimpleDoubleProperty(0);
    private void initMouseControl(Group group, SubScene subScene) {

        group.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        subScene.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        subScene.setOnMouseDragged(event -> {
            angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
            angleY.set(anchorAngleY + anchorX - event.getSceneX());
        });

        subScene.addEventHandler(ScrollEvent.SCROLL, event -> {
            double delta = event.getDeltaY();
            if (group.getTranslateZ()+delta < 600 && group.getTranslateZ()+delta > -220) {
                group.translateZProperty().set(group.getTranslateZ() + delta);
            }
        });
    }
    public static Color encodeStringToColor(String input) {
        int hashCode = input.hashCode();
        int red = (hashCode & 0xFF0000) >> 16;
        int green = (hashCode & 0xFF00) >> 8;
        int blue = hashCode & 0xFF;
        return Color.rgb(red, green, blue);
    }







    private void createMainScene(int currTab) throws IOException {
        createLanguageSwitcher();

        // Create a TabPane to hold the tabs
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Create the first tab with a table
        Tab tableTab = new Tab(bundle.getString("tab.table"));
        createTable();
        tableTab.setContent(tableView);
        tabPane.getTabs().add(tableTab);

        // Create the second tab with a 3D frame
        Tab frameTab = new Tab(bundle.getString("tab.3dFrame"));
        create3DFrame();
        frameTab.setContent(pane3D);
        tabPane.getTabs().add(frameTab);
        tabPane.getSelectionModel().select(currTab);
        Text greet = new Text(bundle.getString("welcome") + ", " + client.getUsername() + "!");
        greet.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        HBox greetWithLang = new HBox(greet, languageSwitcher);
        greetWithLang.setAlignment(Pos.CENTER_RIGHT);

        Button addButton = new Button(bundle.getString("buttons.add"));
        addButton.setOnAction(e -> {
            Flat flat = askForFlat(bundle.getString("buttons.add"));
            if (flat == null) return;
            AddResponse response = (AddResponse) client.sendRequestGetResponse(new AddRequest(flat));
            if (response.error != null) {
                runAlert(Alert.AlertType.ERROR, response.error);
            }
        });
        Button addIfMinButton = new Button(bundle.getString("buttons.addIfMin"));
        addIfMinButton.setOnAction(e -> {
            Flat flat = askForFlat(bundle.getString("buttons.addIfMin"));
            if (flat == null) return;
            AddIfMinResponse response = (AddIfMinResponse) client.sendRequestGetResponse(new AddIfMinRequest(flat));
            if (response.error != null) {
                runAlert(Alert.AlertType.ERROR, response.error);
            }
        });
        Button clearButton = new Button(bundle.getString("buttons.clear"));
        clearButton.setOnAction(e -> {
            client.sendRequestGetResponse(new ClearRequest());
        });
//        Button executeScriptButton = new Button(bundle.getString("buttons.executeScript"));
//        executeScriptButton.setOnAction(e -> {
//
//        });
        Button infoButton = new Button(bundle.getString("buttons.info"));
        infoButton.setOnAction(e -> {
            createAlert(Alert.AlertType.INFORMATION, ((InfoResponse) client.sendRequestGetResponse(new InfoRequest())).infoMessage).show();
            refresh();
        });
        Button gameButton = new Button(bundle.getString("buttons.game"));
        gameButton.setOnAction(e -> {
            ArrayList<String> arguments = new ArrayList<>();
            for (Flat flat : flats) {
                System.out.println(flat.getName());
                arguments.add(String.valueOf(flat.getCreatedBy()));
                arguments.add(String.valueOf(flat.getCoordinatesX()));
                arguments.add(String.valueOf(flat.getCoordinatesY()));
                arguments.add(String.valueOf(flat.getArea()));
            }
            try {
//                System.out.println("\"" + String.join("\", \"", arguments) + "\"");
                Runtime.getRuntime().exec(
                        "java -jar \"C:\\Users\\user\\IdeaProjects\\lab9-prepare\\target\\Maze-Prepare-1.0-jar-with-dependencies.jar\" \"" + String.join("\" \"", arguments) + "\"");
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
//            System.exit(0);
        });
//        Button printDescendingButton = new Button(bundle.getString("buttons.printDescending"));
//        printDescendingButton.setOnAction(e -> {
////            TableColumn<Flat, ?> creationDateCol = getTableColumnByName(tableView, bundle.getString("column.creationDate"));
////            creationDateCol.setSortType(TreeTableColumn.SortType.DESCENDING);
////            tableView.getSortOrder().setAll(creationDateCol);
//        });
        Button printFieldDescendingTransportButton = new Button(bundle.getString("buttons.printFieldDescendingTransport"));
        printFieldDescendingTransportButton.setOnAction(e -> {
            PrintFieldDescendingTransportResponse response = (PrintFieldDescendingTransportResponse) client.sendRequestGetResponse(new PrintFieldDescendingTransportRequest());
            StringBuilder sb = new StringBuilder();
            for (Transport transport : response.transports) {
                sb.append("\n").append(transport);
            }
            Alert alert = createAlert(Alert.AlertType.INFORMATION, sb.toString());
            alert.setHeaderText("Transport fields");
            alert.show();

        });
        Button printUniqueHouseButton = new Button(bundle.getString("buttons.printUniqueHouse"));
        printUniqueHouseButton.setOnAction(e -> {
            PrintUniqueHouseResponse response = (PrintUniqueHouseResponse) client.sendRequestGetResponse(new PrintUniqueHouseRequest());
            StringBuilder sb = new StringBuilder();
            for (String house : response.uniqueHouses) {
                sb.append("\n").append(house);
            }
            String houses = sb.toString().replace("\u001B[33m", "").replace("\u001B[0m", "").replace("\u001B[36m", "").replace("\u001B[32m", "");
            Alert alert = createAlert(Alert.AlertType.INFORMATION, houses);
            alert.setHeaderText("Unique house values");
            alert.show();
        });
        Button removeByIDButton = new Button(bundle.getString("buttons.removeByID"));
        removeByIDButton.setOnAction(e -> {
            if (!askForID(bundle.getString("buttons.removeByID"))) return;
            try {
                RemoveByIdResponse response = (RemoveByIdResponse) client.sendRequestGetResponse(new RemoveByIdRequest(this.id));
                if (response.error != null) createAlert(Alert.AlertType.ERROR, response.error);
//                runAlert(Alert.AlertType.INFORMATION, this.id);
            } catch (IllegalArgumentException ee) {
                runAlert(Alert.AlertType.ERROR, "You need to supply an ID, which is an UUID");
            }
        });
        Button removeFirstButton = new Button(bundle.getString("buttons.removeFirst"));
        removeFirstButton.setOnAction(e -> {
            RemoveFirstResponse response = (RemoveFirstResponse) client.sendRequestGetResponse(new RemoveFirstRequest());
            if (response.error != null) createAlert(Alert.AlertType.ERROR, response.error).show();
        });
        Button removeGreaterButton = new Button(bundle.getString("buttons.removeGreater"));
        removeGreaterButton.setOnAction(e -> {
            Flat flat = askForFlat(bundle.getString("buttons.removeGreater"));
            if (flat == null) return;
            client.sendRequestGetResponse(new RemoveGreaterRequest(flat));
        });
        Button updateButton = new Button(bundle.getString("buttons.update"));
        updateButton.setOnAction(e -> {
            if (!askForID(bundle.getString("buttons.update"))) return;
            Flat flat = askForFlat(bundle.getString("buttons.update"));
            if (flat == null) return;
            UpdateResponse response = (UpdateResponse) client.sendRequestGetResponse(new UpdateRequest(id, flat));

        });

        HBox addPane = new HBox(addButton, addIfMinButton, updateButton);
        addPane.setAlignment(Pos.CENTER);
        addPane.setSpacing(50);
        HBox removePane = new HBox(removeByIDButton, removeFirstButton, removeGreaterButton, clearButton);
        removePane.setAlignment(Pos.CENTER);
        removePane.setSpacing(50);
        HBox printPane = new HBox(printFieldDescendingTransportButton, printUniqueHouseButton);
        printPane.setAlignment(Pos.CENTER);
        printPane.setSpacing(50);
        HBox miscPane = new HBox(infoButton, gameButton);
        miscPane.setAlignment(Pos.CENTER);
        miscPane.setSpacing(50);
        VBox buttonPane = new VBox(addPane, removePane, printPane, miscPane);
        buttonPane.setSpacing(10);
        buttonPane.setPadding(new Insets(10, 10, 10, 10));


        // Create a VBox to hold the language switcher and tab pane
        VBox root = new VBox(greetWithLang, tabPane, buttonPane);
        this.mainScene = new Scene(root, 1000, 600);
    }






    private void refresh() {
        PriorityQueue<Flat> newFlats = ((ShowResponse) client.sendRequestGetResponse(new ShowRequest())).flats;
        if (!newFlats.containsAll(flats) || !flats.containsAll(newFlats)) {
            try {
                createMainScene(tabPane.getSelectionModel().getSelectedIndex());
                primaryStage.setScene(mainScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.tableView.refresh();
        }
    }

    private void runAlert(Alert.AlertType type, String message) {
        createAlert(type, message).show();
    }

    public static Alert createAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);

//        StringBuilder sb = new StringBuilder(message);
//        for (int i = 0; i < message.length(); i += 200) {
//            sb.insert(i, "\n");
//        }

        Label t = new Label(message);
        alert.getDialogPane().setContent(t);
        return alert;
    }

    private Flat askForFlat(String header) {
        Dialog<Flat> dialog = new Dialog<>();
        dialog.setTitle("Flat input dialog");
        dialog.setHeaderText(header);

        Label nameLabel = new Label(bundle.getString("name"));
        TextField nameField = new TextField();

        Label xLabel = new Label(bundle.getString("xcord"));
        TextField xField = new TextField();

        Label yLabel = new Label(bundle.getString("ycord"));
        TextField yField = new TextField();

        Label areaLabel = new Label(bundle.getString("area"));
        TextField areaField = new TextField();

        Label roomsLabel = new Label(bundle.getString("numofrooms"));
        TextField roomsField = new TextField();

        Label furnishLabel = new Label(bundle.getString("furnish"));
        TextField furnishField = new TextField();

        Label viewLabel = new Label(bundle.getString("view"));
        TextField viewField = new TextField();

        Label transportLabel = new Label(bundle.getString("transport"));
        TextField transportField = new TextField();

        Label houseNameLabel = new Label(bundle.getString("housename"));
        TextField houseNameField = new TextField();

        Label yearLabel = new Label(bundle.getString("year"));
        TextField yearField = new TextField();

        Label numberOfLiftsLabel = new Label(bundle.getString("numoflifts"));
        TextField numberOfLiftsField = new TextField();

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(xLabel, 0, 1);
        gridPane.add(xField, 1, 1);
        gridPane.add(yLabel, 0, 2);
        gridPane.add(yField, 1, 2);
        gridPane.add(areaLabel, 0, 3);
        gridPane.add(areaField, 1, 3);
        gridPane.add(roomsLabel, 0, 4);
        gridPane.add(roomsField, 1, 4);
        gridPane.add(furnishLabel, 0, 5);
        gridPane.add(furnishField, 1, 5);
        gridPane.add(viewLabel, 0, 6);
        gridPane.add(viewField, 1, 6);
        gridPane.add(transportLabel, 0, 7);
        gridPane.add(transportField, 1, 7);
        gridPane.add(houseNameLabel, 0, 8);
        gridPane.add(houseNameField, 1, 8);
        gridPane.add(yearLabel, 0, 9);
        gridPane.add(yearField, 1, 9);
        gridPane.add(numberOfLiftsLabel, 0, 10);
        gridPane.add(numberOfLiftsField, 1, 10);
        gridPane.setPrefHeight(600);
        dialog.getDialogPane().setContent(gridPane);


        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        Button okButton = (Button) dialog.getDialogPane().lookupButton(okButtonType);

        okButton.addEventFilter(ActionEvent.ACTION, event -> {
            boolean hasError = false;

            // Check and validate name
            String name = nameField.getText();
            nameField.setStyle("-fx-border-color: green;");
            if (name.isEmpty()) {
                nameField.setStyle("-fx-border-color: red;");
                hasError = true;
            }

            // Check and validate coordinates (x and y)
            double x = 0;
            try {
                x = Double.parseDouble(xField.getText());
                xField.setStyle("-fx-border-color: green;");
                if (x > 314) {
                    xField.setStyle("-fx-border-color: red;");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                xField.setStyle("-fx-border-color: red;");
                hasError = true;
            }

            double y = 0;
            try {
                y = Double.parseDouble(yField.getText());
                yField.setStyle("-fx-border-color: green;");
                if (y > 314) {
                    yField.setStyle("-fx-border-color: red;");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                yField.setStyle("-fx-border-color: red;");
                hasError = true;
            }

            // Check and validate area
            float area = 0;
            try {
                area = Float.parseFloat(areaField.getText());
                areaField.setStyle("-fx-border-color: green;");
                if (area <= 0) {
                    areaField.setStyle("-fx-border-color: red;");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                areaField.setStyle("-fx-border-color: red;");
                hasError = true;
            }

            // Check and validate number of rooms
            long rooms = 0;
            try {
                rooms = Long.parseLong(roomsField.getText());
                roomsField.setStyle("-fx-border-color: green;");
                if (rooms <= 0) {
                    roomsField.setStyle("-fx-border-color: red;");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                roomsField.setStyle("-fx-border-color: red;");
                hasError = true;
            }

            // Check and validate furnish
            String furnishInput = furnishField.getText().toUpperCase().trim();
            furnishField.setStyle("-fx-border-color: green;");
            Furnish furnish = null;
            try {
                if (furnishInput.matches("\\d+")) {
                    int index = Integer.parseInt(furnishInput);
                    try {
                        furnish = Furnish.values()[index - 1];
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new IllegalArgumentException();
                    }
                } else {
                    furnish = Enum.valueOf(Furnish.class, furnishInput);
                }
            } catch (IllegalArgumentException e) {
                furnishField.setStyle("-fx-border-color: red;");
                hasError = true;
            }

            // Check and validate view
            String viewInput = viewField.getText().toUpperCase().trim();
            viewField.setStyle("-fx-border-color: green;");
            View view = null;
            try {
                if (viewInput.matches("\\d+")) {
                    int index = Integer.parseInt(viewInput);
                    try {
                        view = View.values()[index - 1];
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new IllegalArgumentException();
                    }
                } else {
                    view = Enum.valueOf(View.class, viewInput);
                }
            } catch (IllegalArgumentException e) {
                viewField.setStyle("-fx-border-color: red;");
                hasError = true;
            }

            // Check and validate transport
            String transportInput = transportField.getText().toUpperCase().trim();
            transportField.setStyle("-fx-border-color: green;");
            Transport transport = null;
            try {
                if (transportInput.matches("\\d+")) {
                    int index = Integer.parseInt(transportInput);
                    try {
                        transport = Transport.values()[index - 1];
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new IllegalArgumentException();
                    }
                } else {
                    transport = Enum.valueOf(Transport.class, transportInput);
                }
            } catch (IllegalArgumentException e) {
                transportField.setStyle("-fx-border-color: red;");
                hasError = true;
            }

            // Check and validate house name
            String houseName = houseNameField.getText();
            houseNameField.setStyle("-fx-border-color: green;");
            if (houseName.isEmpty()) {
                houseName = null;
            }

            // Check and validate year
            int year = 0;
            try {
                year = Integer.parseInt(yearField.getText());
                yearField.setStyle("-fx-border-color: green;");
                if (year <= 0) {
                    yearField.setStyle("-fx-border-color: red;");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                yearField.setStyle("-fx-border-color: red;");
                hasError = true;
            }

            // Check and validate number of lifts
            int numberOfLifts = 0;
            try {
                numberOfLifts = Integer.parseInt(numberOfLiftsField.getText());
                numberOfLiftsField.setStyle("-fx-border-color: green;");
                if (numberOfLifts <= 0) {
                    numberOfLiftsField.setStyle("-fx-border-color: red;");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                numberOfLiftsField.setStyle("-fx-border-color: red;");
                hasError = true;
            }

            if (hasError) {
                event.consume(); // Prevent the dialog from closing
            }
        });

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {

                String name = nameField.getText();

                double x = Double.parseDouble(xField.getText());
                double y = Double.parseDouble(yField.getText());
                float area = Float.parseFloat(areaField.getText());
                long rooms = Long.parseLong(roomsField.getText());
                String furnishInput = furnishField.getText().toUpperCase().trim();
                Furnish furnish = null;
                if (furnishInput.matches("\\d+")) {
                    int index = Integer.parseInt(furnishInput);
                    try {
                        furnish = Furnish.values()[index - 1];
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new IllegalArgumentException();
                    }
                } else {
                    furnish = Enum.valueOf(Furnish.class, furnishInput);
                }

                String viewInput = viewField.getText().toUpperCase().trim();
                View view = null;
                if (viewInput.matches("\\d+")) {
                    int index = Integer.parseInt(viewInput);
                    try {
                        view = View.values()[index - 1];
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new IllegalArgumentException();
                    }
                } else {
                    view = Enum.valueOf(View.class, viewInput);
                }

                String transportInput = transportField.getText().toUpperCase().trim();
                Transport transport = null;
                if (transportInput.matches("\\d+")) {
                    int index = Integer.parseInt(transportInput);
                    try {
                        transport = Transport.values()[index - 1];
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new IllegalArgumentException();
                    }
                } else {
                    transport = Enum.valueOf(Transport.class, transportInput);
                }

                String houseName = houseNameField.getText();
                if (houseName.isEmpty()) {
                    houseName = null;
                }

                int year = Integer.parseInt(yearField.getText());

                int numberOfLifts = Integer.parseInt(numberOfLiftsField.getText());

                Flat flat = new Flat();
                flat.setName(name);
                flat.setCoordinates(new Coordinates(x, y));
                flat.setArea(area);
                flat.setNumberOfRooms(rooms);
                flat.setFurnish(furnish);
                flat.setView(view);
                flat.setTransport(transport);
                flat.setHouse(new House(houseName, year, numberOfLifts));

                return flat;
            }
            return null;
        });

        dialog.showAndWait();
        if (dialog.getResult() != null) return dialog.getResult();
        return null;

    }

    private void updateFlatFromTable(Flat oldFlat) {
        Dialog<Flat> dialog = new Dialog<>();
        dialog.setTitle("Flat input dialog (" + oldFlat.getName() + ")");
        dialog.setHeaderText(bundle.getString("buttons.update"));

        Label nameLabel = new Label(bundle.getString("name"));
        TextField nameField = new TextField(oldFlat.getName());

        Label xLabel = new Label(bundle.getString("xcord"));
        TextField xField = new TextField("" + oldFlat.getCoordinatesX());

        Label yLabel = new Label(bundle.getString("ycord"));
        TextField yField = new TextField("" + oldFlat.getCoordinatesY());

        Label areaLabel = new Label(bundle.getString("area"));
        TextField areaField = new TextField("" + oldFlat.getArea());

        Label roomsLabel = new Label(bundle.getString("numofrooms"));
        TextField roomsField = new TextField("" + oldFlat.getNumberOfRooms());

        Label furnishLabel = new Label(bundle.getString("furnish"));
        TextField furnishField = new TextField(oldFlat.getFurnish().toString().replace("'", ""));

        Label viewLabel = new Label(bundle.getString("view"));
        TextField viewField = new TextField(oldFlat.getView().toString().replace("'", ""));

        Label transportLabel = new Label(bundle.getString("transport"));
        TextField transportField = new TextField(oldFlat.getTransport().toString().replace("'", ""));

        Label houseNameLabel = new Label(bundle.getString("housename"));
        TextField houseNameField = new TextField(oldFlat.getHouseName());

        Label yearLabel = new Label(bundle.getString("year"));
        TextField yearField = new TextField("" + oldFlat.getHouseYear());

        Label numberOfLiftsLabel = new Label(bundle.getString("numoflifts"));
        TextField numberOfLiftsField = new TextField("" + oldFlat.getHouseNumberOfLifts());

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(xLabel, 0, 1);
        gridPane.add(xField, 1, 1);
        gridPane.add(yLabel, 0, 2);
        gridPane.add(yField, 1, 2);
        gridPane.add(areaLabel, 0, 3);
        gridPane.add(areaField, 1, 3);
        gridPane.add(roomsLabel, 0, 4);
        gridPane.add(roomsField, 1, 4);
        gridPane.add(furnishLabel, 0, 5);
        gridPane.add(furnishField, 1, 5);
        gridPane.add(viewLabel, 0, 6);
        gridPane.add(viewField, 1, 6);
        gridPane.add(transportLabel, 0, 7);
        gridPane.add(transportField, 1, 7);
        gridPane.add(houseNameLabel, 0, 8);
        gridPane.add(houseNameField, 1, 8);
        gridPane.add(yearLabel, 0, 9);
        gridPane.add(yearField, 1, 9);
        gridPane.add(numberOfLiftsLabel, 0, 10);
        gridPane.add(numberOfLiftsField, 1, 10);
        gridPane.setPrefHeight(600);
        dialog.getDialogPane().setContent(gridPane);


        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType removeButtonType = new ButtonType("Remove element", ButtonBar.ButtonData.OK_DONE);
//        Button optionButton = new Button("Remove");
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL, removeButtonType);

        Button okButton = (Button) dialog.getDialogPane().lookupButton(okButtonType);
        Button removeButton = (Button) dialog.getDialogPane().lookupButton(removeButtonType);
        removeButton.addEventFilter(ActionEvent.ACTION, event -> client.sendRequestGetResponse(new RemoveByIdRequest(oldFlat.getId())));


        okButton.addEventFilter(ActionEvent.ACTION, event -> {
            boolean hasError = false;

            // Check and validate name
            String name = nameField.getText();
            nameField.setStyle("-fx-border-color: green;");
            if (name.isEmpty()) {
                nameField.setStyle("-fx-border-color: red;");
                hasError = true;
            }

            // Check and validate coordinates (x and y)
            double x = 0;
            try {
                x = Double.parseDouble(xField.getText());
                xField.setStyle("-fx-border-color: green;");
                if (x > 314) {
                    xField.setStyle("-fx-border-color: red;");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                xField.setStyle("-fx-border-color: red;");
                hasError = true;
            }

            double y = 0;
            try {
                y = Double.parseDouble(yField.getText());
                yField.setStyle("-fx-border-color: green;");
                if (y > 314) {
                    yField.setStyle("-fx-border-color: red;");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                yField.setStyle("-fx-border-color: red;");
                hasError = true;
            }

            // Check and validate area
            float area = 0;
            try {
                area = Float.parseFloat(areaField.getText());
                areaField.setStyle("-fx-border-color: green;");
                if (area <= 0) {
                    areaField.setStyle("-fx-border-color: red;");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                areaField.setStyle("-fx-border-color: red;");
                hasError = true;
            }

            // Check and validate number of rooms
            long rooms = 0;
            try {
                rooms = Long.parseLong(roomsField.getText());
                roomsField.setStyle("-fx-border-color: green;");
                if (rooms <= 0) {
                    roomsField.setStyle("-fx-border-color: red;");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                roomsField.setStyle("-fx-border-color: red;");
                hasError = true;
            }

            // Check and validate furnish
            String furnishInput = furnishField.getText().toUpperCase().trim();
            furnishField.setStyle("-fx-border-color: green;");
            Furnish furnish = null;
            try {
                if (furnishInput.matches("\\d+")) {
                    int index = Integer.parseInt(furnishInput);
                    try {
                        furnish = Furnish.values()[index - 1];
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new IllegalArgumentException();
                    }
                } else {
                    furnish = Enum.valueOf(Furnish.class, furnishInput);
                }
            } catch (IllegalArgumentException e) {
                furnishField.setStyle("-fx-border-color: red;");
                hasError = true;
            }

            // Check and validate view
            String viewInput = viewField.getText().toUpperCase().trim();
            viewField.setStyle("-fx-border-color: green;");
            View view = null;
            try {
                if (viewInput.matches("\\d+")) {
                    int index = Integer.parseInt(viewInput);
                    try {
                        view = View.values()[index - 1];
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new IllegalArgumentException();
                    }
                } else {
                    view = Enum.valueOf(View.class, viewInput);
                }
            } catch (IllegalArgumentException e) {
                viewField.setStyle("-fx-border-color: red;");
                hasError = true;
            }

            // Check and validate transport
            String transportInput = transportField.getText().toUpperCase().trim();
            transportField.setStyle("-fx-border-color: green;");
            Transport transport = null;
            try {
                if (transportInput.matches("\\d+")) {
                    int index = Integer.parseInt(transportInput);
                    try {
                        transport = Transport.values()[index - 1];
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new IllegalArgumentException();
                    }
                } else {
                    transport = Enum.valueOf(Transport.class, transportInput);
                }
            } catch (IllegalArgumentException e) {
                transportField.setStyle("-fx-border-color: red;");
                hasError = true;
            }

            // Check and validate house name
            String houseName = houseNameField.getText();
            houseNameField.setStyle("-fx-border-color: green;");
            if (houseName.isEmpty()) {
                houseName = null;
            }

            // Check and validate year
            int year = 0;
            try {
                year = Integer.parseInt(yearField.getText());
                yearField.setStyle("-fx-border-color: green;");
                if (year <= 0) {
                    yearField.setStyle("-fx-border-color: red;");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                yearField.setStyle("-fx-border-color: red;");
                hasError = true;
            }

            // Check and validate number of lifts
            int numberOfLifts = 0;
            try {
                numberOfLifts = Integer.parseInt(numberOfLiftsField.getText());
                numberOfLiftsField.setStyle("-fx-border-color: green;");
                if (numberOfLifts <= 0) {
                    numberOfLiftsField.setStyle("-fx-border-color: red;");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                numberOfLiftsField.setStyle("-fx-border-color: red;");
                hasError = true;
            }

            if (hasError) {
                event.consume(); // Prevent the dialog from closing
            }
        });

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {

                String name = nameField.getText();

                double x = Double.parseDouble(xField.getText());
                double y = Double.parseDouble(yField.getText());
                float area = Float.parseFloat(areaField.getText());
                long rooms = Long.parseLong(roomsField.getText());
                String furnishInput = furnishField.getText().toUpperCase().trim();
                Furnish furnish = null;
                if (furnishInput.matches("\\d+")) {
                    int index = Integer.parseInt(furnishInput);
                    try {
                        furnish = Furnish.values()[index - 1];
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new IllegalArgumentException();
                    }
                } else {
                    furnish = Enum.valueOf(Furnish.class, furnishInput);
                }

                String viewInput = viewField.getText().toUpperCase().trim();
                View view = null;
                if (viewInput.matches("\\d+")) {
                    int index = Integer.parseInt(viewInput);
                    try {
                        view = View.values()[index - 1];
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new IllegalArgumentException();
                    }
                } else {
                    view = Enum.valueOf(View.class, viewInput);
                }

                String transportInput = transportField.getText().toUpperCase().trim();
                Transport transport = null;
                if (transportInput.matches("\\d+")) {
                    int index = Integer.parseInt(transportInput);
                    try {
                        transport = Transport.values()[index - 1];
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new IllegalArgumentException();
                    }
                } else {
                    transport = Enum.valueOf(Transport.class, transportInput);
                }

                String houseName = houseNameField.getText();
                if (houseName.isEmpty()) {
                    houseName = null;
                }

                int year = Integer.parseInt(yearField.getText());

                int numberOfLifts = Integer.parseInt(numberOfLiftsField.getText());

                Flat flat = new Flat();
                flat.setName(name);
                flat.setCoordinates(new Coordinates(x, y));
                flat.setArea(area);
                flat.setNumberOfRooms(rooms);
                flat.setFurnish(furnish);
                flat.setView(view);
                flat.setTransport(transport);
                flat.setHouse(new House(houseName, year, numberOfLifts));

                return flat;
            }
            return null;
        });

        dialog.showAndWait();
        if (dialog.getResult() != null) client.sendRequestGetResponse(new UpdateRequest(oldFlat.getId(), dialog.getResult()));
    }


    private boolean askForID(String header) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("ID Dialog");
        dialog.setHeaderText(header);

        Label idLabel = new Label("ID:");
        TextField idField = new TextField();

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(idLabel, 0, 0);
        gridPane.add(idField, 1, 0);

        dialog.getDialogPane().setContent(gridPane);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return idField.getText();
            }
            return null;
        });

        // Close the dialog and set the ID when OK button is pressed
        dialog.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            dialog.setResult(idField.getText());
        });

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                setId(UUID.fromString(result.get()));
                return true;
            } catch (IllegalArgumentException e) {
                runAlert(Alert.AlertType.ERROR, "You need to supply an ID, which is an UUID");
                return false;
            }
        } else {
            return false;
        }
    }

    private void setId(UUID id) {
        this.id = id;
    }

    private void createTable() {
        TableColumn<Flat, Integer> idColumn = new TableColumn<>(bundle.getString("column.id"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Flat, String> createdByColumn = new TableColumn<>(bundle.getString("column.createdBy"));
        createdByColumn.setCellValueFactory(new PropertyValueFactory<>("createdBy"));

        TableColumn<Flat, String> nameColumn = new TableColumn<>(bundle.getString("column.name"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Flat, Double> coordinatesXColumn = new TableColumn<>(bundle.getString("column.coordinatesX"));
        coordinatesXColumn.setCellValueFactory(new PropertyValueFactory<>("coordinatesY"));

        TableColumn<Flat, Double> coordinatesYColumn = new TableColumn<>(bundle.getString("column.coordinatesY"));
        coordinatesYColumn.setCellValueFactory(new PropertyValueFactory<>("coordinatesY"));

        TableColumn<Flat, String> creationDateColumn = new TableColumn<>(bundle.getString("column.creationDate"));
        creationDateColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCreationDate().toInstant().atZone(ZoneId.of(currTimezone)).format(formatter)));

        TableColumn<Flat, Integer> areaColumn = new TableColumn<>(bundle.getString("column.area"));
        areaColumn.setCellValueFactory(new PropertyValueFactory<>("area"));

        TableColumn<Flat, Integer> numberOfRoomsColumn = new TableColumn<>(bundle.getString("column.numberOfRooms"));
        numberOfRoomsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfRooms"));

        TableColumn<Flat, String> furnishColumn = new TableColumn<>(bundle.getString("column.furnish"));
        furnishColumn.setCellValueFactory(new PropertyValueFactory<>("furnish"));

        TableColumn<Flat, String> viewColumn = new TableColumn<>(bundle.getString("column.view"));
        viewColumn.setCellValueFactory(new PropertyValueFactory<>("view"));

        TableColumn<Flat, String> transportColumn = new TableColumn<>(bundle.getString("column.transport"));
        transportColumn.setCellValueFactory(new PropertyValueFactory<>("transport"));

        TableColumn<Flat, String> houseNameColumn = new TableColumn<>(bundle.getString("column.houseName"));
        houseNameColumn.setCellValueFactory(new PropertyValueFactory<>("houseName"));
        TableColumn<Flat, String> houseYearColumn = new TableColumn<>(bundle.getString("column.houseYear"));
        houseYearColumn.setCellValueFactory(new PropertyValueFactory<>("houseYear"));
        TableColumn<Flat, String> houseNumberOfLiftsColumn = new TableColumn<>(bundle.getString("column.houseNumberOfLifts"));
        houseNumberOfLiftsColumn.setCellValueFactory(new PropertyValueFactory<>("houseNumberOfLifts"));

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !tableView.getSelectionModel().isEmpty()) {
                Flat selectedFlat = tableView.getSelectionModel().getSelectedItem();
                if (!Objects.equals(selectedFlat.getCreatedBy(), client.getUsername())) {
                    runAlert(Alert.AlertType.ERROR, "You do not own that element, so you can't edit it");
                    return;
                }
                updateFlatFromTable(selectedFlat);
            }
        });

        this.tableView.getColumns().setAll(
                idColumn, createdByColumn, nameColumn, coordinatesXColumn, coordinatesYColumn, areaColumn,
                creationDateColumn, numberOfRoomsColumn, furnishColumn, viewColumn,
                transportColumn, houseNameColumn, houseYearColumn, houseNumberOfLiftsColumn
        );

        this.flats = ((ShowResponse) client.sendRequestGetResponse(new ShowRequest())).flats;
        this.tableView.setItems(FXCollections.observableArrayList(this.flats));
    }

    private void createLoginScene() {
        createLanguageSwitcher();

        Text title = new Text(bundle.getString("titles.login"));
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Label usernameLabel = new Label(bundle.getString("login"));
        TextField usernameField = new TextField();

        Label passwordLabel = new Label(bundle.getString("password"));
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button(bundle.getString("login.button"));
        loginButton.setDefaultButton(true);
        loginButton.setOnAction(e -> authenticate(usernameField.getText(), passwordField.getText()));

        Hyperlink registerLink = new Hyperlink(bundle.getString("register.button"));
        registerLink.setOnAction(e -> {
            this.primaryStage.setTitle(bundle.getString("titles.register"));
            this.currScene = "registerScene";
            createRegisterScene();
            this.primaryStage.setScene(registerScene);
        });

        // Create the layout for the login page
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        gridPane.add(title, 0, 0, 2, 1);
        gridPane.add(usernameLabel, 0, 1);
        gridPane.add(usernameField, 1, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(loginButton, 0, 3, 2, 1);
        gridPane.add(registerLink, 0, 4, 2, 1);

        // Create a VBox to hold the language switcher and tab pane
        VBox root = new VBox(languageSwitcher, gridPane);
        this.loginScene = new Scene(root, 330, 350);
    }

    private void createRegisterScene() {

        createLanguageSwitcher();
        Text title = new Text(bundle.getString("titles.register"));
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Label usernameLabel = new Label(bundle.getString("login"));
        TextField usernameField = new TextField();

        Label passwordLabel = new Label(bundle.getString("password"));
        PasswordField passwordField = new PasswordField();

        Button registerButton = new Button(bundle.getString("register.button"));
        registerButton.setDefaultButton(true);
        registerButton.setOnAction(e -> register(usernameField.getText(), passwordField.getText()));

        Hyperlink loginLink = new Hyperlink(bundle.getString("login.button"));
        loginLink.setOnAction(e -> {
            this.primaryStage.setTitle(bundle.getString("titles.login"));
            this.currScene = "loginScene";
            createLoginScene();
            this.primaryStage.setScene(loginScene);
        });

        // Create the layout for the login page
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        gridPane.add(title, 0, 0, 2, 1);
        gridPane.add(usernameLabel, 0, 1);
        gridPane.add(usernameField, 1, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(registerButton, 0, 3, 2, 1);
        gridPane.add(loginLink, 0, 4, 2, 1);

        VBox root = new VBox(languageSwitcher, gridPane);
        this.registerScene = new Scene(root, 330, 350);
    }

    private void authenticate(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText(bundle.getString("ent"));
//            a.setContentText(bundle.getString("ent"));
            a.show();
            return;
        }

        client.setCreds(username, password);
        try {
            LoginResponse response = (LoginResponse) client.sendRequestGetResponse(new LoginRequest());
            if (response.error == null) {
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(this.refreshTime), e -> refresh()));
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.playFromStart();
                createMainScene(0);
                this.primaryStage.setTitle(bundle.getString("titles.main"));
                this.currScene = "mainScene";
                primaryStage.setScene(mainScene);
                return;
            }
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText(bundle.getString("titles.login"));
            a.setContentText(response.error);
            a.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void register(String username, String password) {
        if (username.isEmpty() | !username.matches("^(?=.{3,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$")) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText(bundle.getString("trylog"));
            a.setContentText(bundle.getString("wronglog"));
            a.show();
            return;
        }

        if (password.isEmpty() | !password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,50}$")) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText(bundle.getString("trypass"));
            a.setContentText(bundle.getString("wrongpass"));
            a.show();
            return;
        }

        client.setCreds(username, password);
        try {
            RegisterResponse response = null;
            response = (RegisterResponse) client.sendRequestGetResponse(new RegisterRequest());
            if (response.error == null) {
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(this.refreshTime), e -> refresh()));
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.playFromStart();
                createMainScene(0);
                this.primaryStage.setTitle(bundle.getString("titles.main"));
                this.currScene = "mainScene";
                primaryStage.setScene(mainScene);
                return;
            }

            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText(bundle.getString("titles.register"));
            a.setContentText(response.error);
            a.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void createLanguageSwitcher() {
        ComboBox<String> languageComboBox = new ComboBox<>();
        languageComboBox.getItems().addAll(bundle.getString("language.russian"), bundle.getString("language.englishIreland"), bundle.getString("language.lithuanian"), bundle.getString("language.finnish"));
        languageComboBox.setValue(bundle.getString(currLanguage));
        languageComboBox.setOnAction(event -> {
            String selectedLanguage = languageComboBox.getValue();
            if (selectedLanguage.equals(bundle.getString("language.russian"))) {
                this.bundle = ResourceBundle.getBundle("ru.rudXson.l10n.Lab_ru");
                currLanguage = "language.russian";
                currTimezone = "+3";
            } else if (selectedLanguage.equals(bundle.getString("language.englishIreland"))) {
                this.bundle = ResourceBundle.getBundle("ru.rudXson.l10n.Lab_en_irish");
                currLanguage = "language.englishIreland";
                currTimezone = "+1";
            } else if (selectedLanguage.equals(bundle.getString("language.lithuanian"))) {
                this.bundle = ResourceBundle.getBundle("ru.rudXson.l10n.Lab_lit");
                currLanguage = "language.lithuanian";
                currTimezone = "+3";
            } else if (selectedLanguage.equals(bundle.getString("language.finnish"))) {
                this.bundle = ResourceBundle.getBundle("ru.rudXson.l10n.Lab_fin");
                currLanguage = "language.finnish";
                currTimezone = "+3";
            }

            try {
                if (currScene.equals("mainScene")) {
                    createMainScene(tabPane.getSelectionModel().getSelectedIndex());
                    this.primaryStage.setScene(mainScene);
                } else if (currScene.equals("loginScene")) {
                    createLoginScene();
                    this.primaryStage.setScene(loginScene);
                } else if (currScene.equals("registerScene")) {
                    createRegisterScene();
                    this.primaryStage.setScene(registerScene);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Create the language switcher container
        languageSwitcher = new HBox(languageComboBox);
        languageSwitcher.setAlignment(Pos.CENTER_RIGHT);
        languageSwitcher.setPadding(new Insets(10));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
