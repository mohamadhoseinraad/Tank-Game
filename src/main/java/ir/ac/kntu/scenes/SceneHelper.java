package ir.ac.kntu.scenes;

import ir.ac.kntu.models.Level;
import ir.ac.kntu.models.Player;
import ir.ac.kntu.services.GameData;
import ir.ac.kntu.GlobalConstance;
import ir.ac.kntu.services.PlayerService;
import ir.ac.kntu.services.eventHandler.EventHandler;
import ir.ac.kntu.models.gameObjects.Flag;
import ir.ac.kntu.models.gameObjects.SceneObject;
import ir.ac.kntu.models.gameObjects.tank.Tank;
import ir.ac.kntu.models.gameObjects.tank.TankSide;
import ir.ac.kntu.models.gameObjects.tank.TankType;
import ir.ac.kntu.models.gameObjects.wall.Wall;
import ir.ac.kntu.models.gameObjects.wall.WallType;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        makeHealth(pane);
        userData(pane);
        makeTankData(pane);
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
            Text username = new Text(GameData.getInstance().getCurrentPlayer().getUsername());
            username.setFill(Color.WHITE);
            username.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
            username.setX(WINDOWS_WIDTH - 225);
            username.setY(100);
            Text highScore = new Text("HighScore" + GameData.getInstance().getCurrentPlayer().getHighScore());
            highScore.setFill(Color.WHITE);
            highScore.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
            highScore.setX(WINDOWS_WIDTH - 225);
            highScore.setY(150);
            pane.getChildren().addAll(username, highScore);
        }
    }

    private static void makeHealth(Pane pane) {
        for (int i = 0; i < GameData.getInstance().getPlayersTank().size(); i++) {
            Tank tank = GameData.getInstance().getPlayersTank().get(i);
            Text healthTitle = new Text("Health : ");
            healthTitle.setFill(Color.WHITE);
            healthTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
            healthTitle.setX(WINDOWS_WIDTH - 225);
            healthTitle.setY(WINDOWS_HEIGHT - 200 + i * 50);
            Text currentHealth = new Text(String.valueOf(tank.getHealth()));
            currentHealth.setFill(Color.WHITE);
            currentHealth.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
            currentHealth.setX(WINDOWS_WIDTH - 50);
            currentHealth.setY(WINDOWS_HEIGHT - 200 + i * 50);
            pane.getChildren().add(currentHealth);
            pane.getChildren().add(healthTitle);

        }
    }

    private static void makeTankData(Pane pane) {
        Text healthTitle = new Text("Shot power : ");
        healthTitle.setFill(Color.WHITE);
        healthTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
        healthTitle.setX(WINDOWS_WIDTH - 225);
        healthTitle.setY(WINDOWS_HEIGHT - 300);
        Text currentHealth = new Text(String.valueOf(playerShotDamage));
        currentHealth.setFill(Color.WHITE);
        currentHealth.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
        currentHealth.setX(WINDOWS_WIDTH - 50);
        currentHealth.setY(WINDOWS_HEIGHT - 290);
        pane.getChildren().add(currentHealth);
        pane.getChildren().add(healthTitle);

    }

    public static void makeEndGameLose(Pane pane, Stage stage, Scene scene) {
        Text gameOver = new Text("Game Over");
        gameOver.setFont(new Font(50));
        gameOver.setX(MAP_FIRST_X + mapHeight / 2 - 150);
        gameOver.setY(MAP_FIRST_Y + mapHeight / 2);
        gameOver.setFill(Color.RED);
        pane.getChildren().add(gameOver);
        new AnimationTimer() {
            int i = 3;
            private long lastUpdate = 0;

            public void handle(long currentNanoTime) {
                if (currentNanoTime - lastUpdate >= 1_000_000_000) {
                    i--;

                    if (i == 0) {
                        this.stop();
                        GameData.getInstance().resetAll(null);
                        StartMenu.makeMenuScene(stage, pane, scene);
                    }
                    lastUpdate = currentNanoTime;
                }
            }
        }.start();
    }

    public static void makeEndGameWin(Pane pane, Stage stage, Scene scene, int playerHealth) {
        Text gameOver = new Text("Win");
        gameOver.setFont(new Font(50));
        gameOver.setX(MAP_FIRST_X + mapHeight / 2);
        gameOver.setY(MAP_FIRST_Y + mapHeight / 2);
        gameOver.setFill(Color.GREEN);
        pane.getChildren().add(gameOver);
        updateLevel();
        new AnimationTimer() {
            int i = 3;
            private long lastUpdate = 0;

            public void handle(long currentNanoTime) {
                if (currentNanoTime - lastUpdate >= 1_000_000_000) {
                    i--;

                    if (i == 0) {
                        this.stop();
                        if (GameData.getInstance().getLevel() == null || GameData.getInstance().getLevel() == Level.Level_10) {
                            GameData.getInstance().resetAll(null);
                            StartMenu.makeMenuScene(stage, pane, scene);
                        } else {
                            GamePage.countDownTimer(GameData.getInstance().getCurrentPlayer().getLastStage(), stage, pane, scene, GameData.getInstance(), playerHealth);
                        }
                    }
                    lastUpdate = currentNanoTime;
                }
            }
        }.start();
    }

    private static void updateLevel() {
        if (GameData.getInstance().getLevel() != null && GameData.getInstance().getLevel() == GameData.getInstance().getCurrentPlayer().getLastStage()) {
            GameData.getInstance().getCurrentPlayer().levelUp();
        }
    }

    public static void readMap(String[][] map, List<SceneObject> sceneObjects, int health) {
        mapSize = map.length;
        GlobalConstance.updateSize();
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                if (map[i][j] != null) {
                    double x = MAP_FIRST_X + j * scale;
                    double y = MAP_FIRST_Y + i * scale;
                    switch (map[i][j]) {
                        case "B" -> sceneObjects.add(new Wall(WallType.Normal, x, y, scale));
                        case "M" -> sceneObjects.add(new Wall(WallType.Iron, x, y, scale));
                        case "P" -> {
                            Tank tank = new Tank(TankType.Player, TankSide.Player, x, y, scale);
                            tank.setHealth(health);
                            sceneObjects.add(tank);
                            GameData.getInstance().getPlayersTank().add(tank);
                        }
                        case "F" -> sceneObjects.add(new Flag(x, y, scale));
                        case "O", "A", "c", "C" -> attachEnemy(map[i][j], x, y, sceneObjects);
                        default -> {

                        }
                    }

                }
            }
        }
        addMapWall(sceneObjects);
    }

    private static void attachEnemy(String s, double x, double y, List<SceneObject> sceneObjects) {
        Tank sceneObject = null;
        switch (s) {
            case "O" -> {
                sceneObject = new Tank(TankType.NormalEnemy, TankSide.Enemy, x, y, scale);
                sceneObjects.add(sceneObject);
            }
            case "A" -> {
                sceneObject = new Tank(TankType.StrongEnemy, TankSide.Enemy, x, y, scale);
                sceneObjects.add(sceneObject);
            }
            case "c" -> {
                sceneObject = new Tank(TankType.RandomEnemy, TankSide.Enemy, x, y, scale);
                sceneObject.setHealth(NORMAL_TANK_HEALTH);
                sceneObjects.add(sceneObject);
            }
            case "C" -> {
                sceneObject = new Tank(TankType.RandomEnemy, TankSide.Enemy, x, y, scale);
                sceneObject.setHealth(STRONG_TANK_HEALTH);
                sceneObjects.add(sceneObject);
            }
            default -> {

            }
        }
        GameData.getInstance().getEnemyTank().add(sceneObject);
    }

    private static void addMapWall(List<SceneObject> sceneObjects) {
        int mapWallSize = 14;
        for (int i = 0; i < mapHeight / mapWallSize; i++) {
            //Left wall
            sceneObjects.add(new Wall(WallType.Iron, MAP_FIRST_X - mapWallSize,
                    (MAP_FIRST_Y) + (i) * mapWallSize, mapWallSize));
            //Right wall
            sceneObjects.add(new Wall(WallType.Iron, mapHeight + MAP_FIRST_X,
                    (MAP_FIRST_Y) + (i) * mapWallSize, mapWallSize));
            //Up wall
            sceneObjects.add(new Wall(WallType.Iron, (MAP_FIRST_X) + (i) * mapWallSize,
                    MAP_FIRST_Y - mapWallSize, mapWallSize));
            //Button
            sceneObjects.add(new Wall(WallType.Iron, (MAP_FIRST_X) + (i) * mapWallSize,
                    MAP_FIRST_Y + mapHeight, mapWallSize));
        }
    }

    public static String[][] readMapFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int numRows = 0;
            int numCols = 0;
            while ((line = reader.readLine()) != null) {
                numRows++;
                numCols = Math.max(numCols, line.length());
            }
            String[][] array = new String[numRows][numCols];
            reader.close();

            BufferedReader read = new BufferedReader(new FileReader(fileName));
            int row = 0;
            while ((line = read.readLine()) != null) {
                for (int col = 0; col < line.length(); col++) {
                    array[row][col] = String.valueOf(line.charAt(col));
                }
                row++;
            }
            read.close();
            return array;
        } catch (IOException e) {
            return readMapFile(DEFAULT_MAP_ONE_PLAYER);
        }
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
        save.setOnMouseClicked(mouseEvent -> {
            secondStage.close();
        });

        vBox.getChildren().addAll(save);
        vBox.setAlignment(Pos.CENTER);
        secondPane.getChildren().add(vBox);
        secondStage.setScene(secondScene);
        secondStage.setWidth(200);
        secondStage.setHeight(400);
        secondStage.show();
    }

}
