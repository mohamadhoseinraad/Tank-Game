package ir.ac.kntu.scenes;

import ir.ac.kntu.models.Player;
import ir.ac.kntu.services.GameData;
import ir.ac.kntu.services.PlayerService;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;

import static ir.ac.kntu.GlobalConstance.PLAYER_TANK_HEALTH;
import static ir.ac.kntu.GlobalConstance.customMap;

public class StageHelper {
    public static void rankPlayersStage() {
        Stage secondStage = new Stage();
        Pane secondPane = new Pane();
        Scene secondScene = new Scene(secondPane);
        VBox vBox = new VBox();

        ArrayList<Player> dataTopPlayers = PlayerService.getINSTANCE().getTopPlayers();
        dataTopPlayers.forEach((player) -> {
            Label fileLabel = new Label(player.getUsername() + "-" + player.getHighScore());
            fileLabel.setTextFill(Color.BLACK);
            fileLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
            vBox.getChildren().add(fileLabel);
        });

        Button save = new Button("Ok");
        save.setStyle("-fx-background-color: #808080; -fx-text-fill: white;");
        save.setOnMouseClicked(mouseEvent -> secondStage.close());

        vBox.getChildren().addAll(save);
        vBox.setAlignment(Pos.CENTER);
        secondPane.getChildren().add(vBox);
        secondStage.setScene(secondScene);
        secondStage.setWidth(200);
        secondStage.setHeight(400);
        secondStage.show();
    }

    public static void getFileNameStage(Stage mainStage, Pane pane, Scene scene) {
        Stage secondStage = new Stage();
        Pane secondPane = new Pane();
        Scene secondScene = new Scene(secondPane);
        VBox vBox = new VBox();
        Label fileLabel = new Label("File name");
        fileLabel.setTextFill(Color.BLACK);
        fileLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        TextField usernameField = new TextField();
        usernameField.setPromptText("File name");

        Button save = new Button("Ok");
        save.setStyle("-fx-background-color: #808080; -fx-text-fill: white;");
        save.setOnMouseClicked(mouseEvent -> {
            customMap += usernameField.getText() + ".txt";
            GamePage.countDownTimer(null, mainStage, pane, scene, GameData.getInstance(), PLAYER_TANK_HEALTH);
            secondStage.close();
        });

        vBox.getChildren().addAll(fileLabel, usernameField, save);
        vBox.setAlignment(Pos.CENTER);
        secondPane.getChildren().add(vBox);
        secondStage.setScene(secondScene);
        secondStage.setWidth(300);
        secondStage.setHeight(150);
        secondStage.show();
    }
}
