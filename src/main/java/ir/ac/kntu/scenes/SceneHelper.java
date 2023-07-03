package ir.ac.kntu.scenes;

import ir.ac.kntu.models.gameObjects.tank.Tank;
import ir.ac.kntu.services.GameData;
import ir.ac.kntu.services.eventHandler.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import static ir.ac.kntu.GlobalConstance.*;

public class SceneHelper {

    public static void conformStage(Stage stage, Pane pane, Scene scene) {
        pane.setStyle("-fx-background-color: #708090;");
        EventHandler.getInstance().attachEventHandlers(scene);
        stage.setScene(scene);
        stage.setTitle("Tank Game");
        stage.setHeight(WINDOWS_HEIGHT);
        stage.setWidth(WINDOWS_WIDTH);
        stage.setMaxHeight(WINDOWS_HEIGHT);
        stage.setMaxWidth(WINDOWS_WIDTH);
        stage.setMinHeight(WINDOWS_HEIGHT);
        stage.setMinWidth(WINDOWS_WIDTH);
    }

    public static void makeGameScene(Pane pane) {
        Rectangle square = new Rectangle(mapHeight, mapHeight);
        square.setFill(Color.BLACK);
        square.setStroke(Color.WHITE);
        pane.getChildren().add(square);
        square.setX(MAP_FIRST_X);
        square.setY(MAP_FIRST_Y);
        Text scoreTitle = new Text("Score : ");
        scoreTitle.setFill(Color.WHITE);
        scoreTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        scoreTitle.setX(WINDOWS_WIDTH - 225);
        scoreTitle.setY(WINDOWS_HEIGHT - 100);
        Text currentScore = new Text(String.valueOf(GameData.getInstance().getScore()));
        currentScore.setFill(Color.WHITE);
        currentScore.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        currentScore.setX(WINDOWS_WIDTH - 150);
        currentScore.setY(WINDOWS_HEIGHT - 50);
        pane.getChildren().add(currentScore);
        pane.getChildren().add(scoreTitle);
        healthData(pane);
        userData(pane);
        tankShotData(pane);
        stageTitle(pane);
    }

    private static void stageTitle(Pane pane) {
        if (GameData.getInstance().getLevel() != null) {
            Text level = new Text(GameData.getInstance().getLevel().toString());
            level.setFill(Color.WHITE);
            level.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
            level.setX(WINDOWS_WIDTH - 225);
            level.setY(50);
            pane.getChildren().addAll(level);
        }
    }

    private static void userData(Pane pane) {
        if (GameData.getInstance().getCurrentPlayer() != null) {
            Text username = createUsernameText();
            Text highScore = createHighScoreText();
            pane.getChildren().addAll(username, highScore);
        }
    }

    @NotNull
    private static Text createHighScoreText() {
        Text highScore = new Text("HighScore" + GameData.getInstance().getCurrentPlayer().getHighScore());
        highScore.setFill(Color.WHITE);
        highScore.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        highScore.setX(WINDOWS_WIDTH - 225);
        highScore.setY(150);
        return highScore;
    }

    @NotNull
    private static Text createUsernameText() {
        Text username = new Text(GameData.getInstance().getCurrentPlayer().getUsername());
        username.setFill(Color.WHITE);
        username.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        username.setX(WINDOWS_WIDTH - 225);
        username.setY(100);
        return username;
    }

    private static void healthData(Pane pane) {
        for (int i = 0; i < GameData.getInstance().getPlayersTank().size(); i++) {
            Tank tank = GameData.getInstance().getPlayersTank().get(i);
            Text healthTitle = createHealthTitle(i);
            Text currentHealth = createCurrentHealth(i, tank);
            pane.getChildren().add(currentHealth);
            pane.getChildren().add(healthTitle);

        }
    }

    @NotNull
    private static Text createCurrentHealth(int i, Tank tank) {
        Text currentHealth = new Text(String.valueOf(tank.getHealth()));
        currentHealth.setFill(Color.WHITE);
        currentHealth.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        currentHealth.setX(WINDOWS_WIDTH - 50);
        currentHealth.setY(WINDOWS_HEIGHT - 200 + i * 50);
        return currentHealth;
    }

    @NotNull
    private static Text createHealthTitle(int i) {
        Text healthTitle = new Text("Health : ");
        healthTitle.setFill(Color.WHITE);
        healthTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        healthTitle.setX(WINDOWS_WIDTH - 225);
        healthTitle.setY(WINDOWS_HEIGHT - 200 + i * 50);
        return healthTitle;
    }

    private static void tankShotData(Pane pane) {
        Text shotDamageTitle = shotDamageTitle();
        Text shotDamageValue = shotDamageValue();
        pane.getChildren().add(shotDamageValue);
        pane.getChildren().add(shotDamageTitle);

    }

    @NotNull
    private static Text shotDamageValue() {
        Text currentHealth = new Text(String.valueOf(playerShotDamage));
        currentHealth.setFill(Color.WHITE);
        currentHealth.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
        currentHealth.setX(WINDOWS_WIDTH - 50);
        currentHealth.setY(WINDOWS_HEIGHT - 290);
        return currentHealth;
    }

    @NotNull
    private static Text shotDamageTitle() {
        Text healthTitle = new Text("Shot power : ");
        healthTitle.setFill(Color.WHITE);
        healthTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
        healthTitle.setX(WINDOWS_WIDTH - 225);
        healthTitle.setY(WINDOWS_HEIGHT - 300);
        return healthTitle;
    }

}
