package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;

public class Main extends Application {
    static Connection connection = null;
    static ArrayList<Fashion> fashions = new ArrayList<>();
    static ArrayList<Fashion> usingList = new ArrayList<>();


    static VBox mainContainer = new VBox(10);

    static int numberOfRowsInPage = 20;
    static int currentPage = 0;

    Button nextButton = new Button("Next");
    Button previousButton = new Button("Previous");

    TextField searchTF = new TextField();
    ObservableList<PieChart.Data> pieChartData =
            FXCollections.observableArrayList(
                    new PieChart.Data("Formal", 13),
                    new PieChart.Data("Sports", 25),
                    new PieChart.Data("NA", 10),
                    new PieChart.Data("Ethnic", 22),
                    new PieChart.Data("Casual", 30));
    PieChart chartV = new PieChart(pieChartData);
    ObservableList<PieChart.Data> statusChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Discounts", 60),
                        new PieChart.Data("Retail", 25),
                        new PieChart.Data("Sold out", 15));
        PieChart chartS = new PieChart(statusChartData);

    CheckBox favCh = new CheckBox("Favourites");
    CheckBox menCh = new CheckBox("Men");
    CheckBox womenCh = new CheckBox("Women");

    @Override
    public void start(Stage primaryStage) throws Exception {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                connectToDB();

                usingList.addAll(fashions);
                showRows();
            }
        });


        nextButton.setPrefWidth(150);
        previousButton.setPrefWidth(150);
        HBox navigateBox = new HBox(10, previousButton, nextButton);
        navigateBox.setAlignment(Pos.CENTER);

        setListeners();

        ScrollPane scrollPane = new ScrollPane(mainContainer);

        searchTF.setPromptText("Search");

        VBox leftSide = new VBox(20, searchTF, favCh, menCh, womenCh, chartV, chartS);
        leftSide.setPadding(new Insets(10));

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(scrollPane);
        borderPane.setBottom(navigateBox);
        borderPane.setLeft(leftSide);

        Scene scene = new Scene(borderPane, 1500, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setListeners() {
        nextButton.setOnMouseClicked(event -> {
            currentPage++;
            showRows();
        });
        previousButton.setOnMouseClicked(event -> {
            if (currentPage > 0) {
                currentPage--;
                showRows();
            }
        });
        searchTF.textProperty().addListener(event -> {
            usingList.clear();
            for (Fashion f : fashions
            ) {
                if (f.getProductDisplayName().toLowerCase().contains(searchTF.getText().toLowerCase())) {
                    usingList.add(f);
                }
            }
            showRows();
        });
        favCh.setOnAction(event -> {
            usingList.clear();
            if (favCh.isSelected()) {

                for (Fashion f : fashions
                ) {
                    if (f.getIsFavourite() == 1) {
                        usingList.add(f);
                    }
                }
            } else {
                usingList.addAll(fashions);
            }
            showRows();
        });
        menCh.setOnAction(event -> {
            usingList.clear();
            if (menCh.isSelected()) {

                for (Fashion f : fashions
                ) {
                    if (f.getGender().equals("Men")) {
                        usingList.add(f);
                    }
                }
            } else {
                usingList.addAll(fashions);
            }
            showRows();
        });
        womenCh.setOnAction(event -> {
            usingList.clear();
            if (womenCh.isSelected()) {

                for (Fashion f : fashions
                ) {
                    if (f.getGender().equals("Women")) {
                        usingList.add(f);
                    }
                }
            } else {
                usingList.addAll(fashions);
            }
            showRows();
        });
    }


    public static void showRows() {
        mainContainer.getChildren().clear();
        for (int i = currentPage * numberOfRowsInPage; i < (currentPage + 1) * numberOfRowsInPage && i < usingList.size(); i++) {
            mainContainer.getChildren().add(new ClothePane(usingList.get(i)));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void connectToDB() {
        Locale.setDefault(Locale.ENGLISH);
        String dbUrl = "jdbc:oracle:thin:@localhost:1521:XE";
        String username = "project";
        String password = "123";
        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            connection = DriverManager.getConnection(dbUrl, username, password);
            System.out.println("Connected to Oracle database server");
            fashions = selectClothes("select * from fashion");

        } catch (SQLException e) {
            System.out.println("ERROR: ");
            e.printStackTrace();
        }
    }

    public static ArrayList<Fashion> selectClothes(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        ArrayList<Fashion> queryResult = new ArrayList<>();
        while (rs.next()) {
            //Retrieve by column name
            queryResult.add(new Fashion(
                    rs.getInt("ID"),
                    rs.getString("GENDER"),
                    rs.getString("MASTERCATEGORY"),
                    rs.getString("SUBCATEGORY"),
                    rs.getString("ARTICLETYPE"),
                    rs.getString("BASECOLOUR"),
                    rs.getString("SEASON"),
                    rs.getInt("YEAR"),
                    rs.getString("USAGE"),
                    rs.getString("PRODUCTDISPLAYNAME"),
                    rs.getString("FILENAME"),
                    rs.getString("STATUS")
            ));
        }
        return queryResult;
    }
}
