package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.sql.SQLException;
import java.sql.Statement;

public class ClothePane extends Pane {
    Fashion fashion;
    private CheckBox favCB = new CheckBox("favourites");
    private Button editBt = new Button("Edit");
    private Button removeBt = new Button("Remove");

    public ClothePane(Fashion fashion) {
        this.fashion = fashion;

        draw();

    }


    private void draw() {
        File file = new File("images\\" + fashion.getFilename());
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);

        VBox col1 = new VBox(imageView);

        Text title = new Text(fashion.getArticleType() + " : " + fashion.getSubCategory());
        title.setFont(Font.font(20));

        Text seasonText = new Text("Season: " + fashion.getSeason());
        Text statusText = new Text("Status: " + fashion.getStatus());
        Text genderText = new Text("Gender: " + fashion.getGender());
        Text descriptionText = new Text("Description: " + fashion.getProductDisplayName());
        descriptionText.setWrappingWidth(400);
        seasonText.setWrappingWidth(400);
        statusText.setWrappingWidth(400);
        genderText.setWrappingWidth(400);




        VBox col2 = new VBox(10, title, descriptionText, seasonText, statusText, genderText);

        Text usageText = new Text(fashion.getUsage());
        VBox col3 = new VBox(10, usageText);

        if (fashion.getIsFavourite() == 1) {
            favCB.setSelected(true);
        }

        VBox col4 = new VBox(50, favCB, editBt, removeBt);

        setListeners();

        HBox hBox = new HBox(50, col1, col2, col3, col4);
        hBox.setPadding(new Insets(10));

        getChildren().add(hBox);
//
//        setStyle("-fx-background-color: black;");

    }

    private void setListeners() {
        favCB.setOnAction(event -> {
            updateFavQuery(favCB.isSelected());
        });
        editBt.setOnMouseClicked(event -> {
            UpdWindow updWindow = new UpdWindow(fashion);
        });
        removeBt.setOnMouseClicked(event -> {
            deleteRowQuery(fashion.getId());
            Main.mainContainer.getChildren().remove(this);
            Main.fashions.remove(fashion);
        });
    }

    public void updateFavQuery(boolean add) {
        try {
            Statement st = Main.connection.createStatement();
            String query = "begin " + (add?"add_to_fav":"remove_from_fav") + "(" + fashion.getId() + "); end;";
            st.executeQuery(query);
            fashion.setIsFavourite(add?1:0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void deleteRowQuery(int id) {
        try {
            Statement st = Main.connection.createStatement();
            String query = "begin  row_delete" + "(" + fashion.getId() + "); end;";
            st.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
