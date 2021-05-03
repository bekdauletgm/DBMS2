package sample;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.sql.Statement;

public class UpdWindow extends Stage {
    Fashion fashion;

    TextField descriptionTF;
    TextField seasonTF;
    TextField statusTF;
    TextField genderTF;

    Button saveBtn = new Button("Save");

    public UpdWindow(Fashion fashion) {
        this.fashion = fashion;

        descriptionTF = new TextField(fashion.getProductDisplayName());
        seasonTF = new TextField(fashion.getSeason());
        statusTF = new TextField(fashion.getStatus());
        genderTF = new TextField(fashion.getGender());



        VBox vBox = new VBox(10, new HBox(10, new Text("Description: "), descriptionTF),
                new HBox(10, new Text("Season: "), seasonTF),
                new HBox(10, new Text("Status: "), statusTF),
                new HBox(10, new Text("Gender: "), genderTF),
                saveBtn);

        vBox.setPadding(new Insets(10));

        setScene(new Scene(vBox, 300,300));
        show();

        setListeners();

    }
    public void saveDataQuery(String description, String status, String season) {
        try {
            Statement st = Main.connection.createStatement();
            String query = "begin update_dat(" +fashion.getId()+" , "+description+" , "+status+" , "+season+" , " + " ) end;" ;
            st.executeQuery(query);
            System.out.println(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void setListeners() {
        saveBtn.setOnMouseClicked(event -> {
            String description = descriptionTF.getText();
            String status = statusTF.getText();
            String season = seasonTF.getText();


            saveDataQuery(description, status, season);
            fashion.setProductDisplayName(description);
            fashion.setStatus(status);
            fashion.setSeason(season);

            Main.showRows();

            this.close();
        });
    }
}
