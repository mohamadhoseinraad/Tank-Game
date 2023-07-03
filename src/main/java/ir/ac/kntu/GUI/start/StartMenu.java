package ir.ac.kntu.GUI.start;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class StartMenu {

    public static void makeMenuScene(Stage stage, Pane pane, Scene scene) {

        BorderPane root;
        root = new BorderPane();
        root.setStyle("-fx-background-color: #000000;");
        VBox page = new VBox();
        HBox topOfPage = makeTitleGame();
        HBox bottomOfPage = new HBox();
        VBox loginBox = StartMenuHelper.loginBox(bottomOfPage, scene, pane, stage);
        bottomOfPage.getChildren().add(loginBox);
        page.getChildren().add(topOfPage);
        page.getChildren().add(bottomOfPage);
        root.setCenter(page);
        stage.setScene(new Scene(root));
    }


    private static HBox makeTitleGame() {
        HBox titleGame = new HBox();
        Label tankGame = new Label("Tank Game");
        tankGame.setTextFill(Color.WHITE);
        tankGame.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        tankGame.setAlignment(Pos.CENTER);

        titleGame.getChildren().add(tankGame);
        titleGame.setAlignment(Pos.CENTER);
        titleGame.setPadding(new Insets(40));

        return titleGame;
    }

}