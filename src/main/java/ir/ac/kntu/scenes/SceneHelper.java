package ir.ac.kntu.scenes;

import ir.ac.kntu.Game;
import ir.ac.kntu.GameStatus;
import ir.ac.kntu.GlobalConstance;
import ir.ac.kntu.eventHandler.EventHandler;
import ir.ac.kntu.gameObjects.Flag;
import ir.ac.kntu.gameObjects.SceneObject;
import ir.ac.kntu.gameObjects.tank.Tank;
import ir.ac.kntu.gameObjects.tank.TankSide;
import ir.ac.kntu.gameObjects.tank.TankType;
import ir.ac.kntu.gameObjects.wall.Wall;
import ir.ac.kntu.gameObjects.wall.WallType;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static ir.ac.kntu.GlobalConstance.*;
import static ir.ac.kntu.GlobalConstance.MAP_FIRST_Y;

public class SceneHelper {

    public static void conformStage(Stage stage, Pane pane, Scene scene) {
        pane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
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
        scoreTitle.setFont(new Font(40));
        scoreTitle.setX(WINDOWS_WIDTH - 225);
        scoreTitle.setY(WINDOWS_HEIGHT - 100);
        Text currentScore = new Text(String.valueOf(Game.getScore()));
        currentScore.setFont(new Font(40));
        currentScore.setX(WINDOWS_WIDTH - 100);
        currentScore.setY(WINDOWS_HEIGHT - 100);
        pane.getChildren().add(currentScore);
        pane.getChildren().add(scoreTitle);
        makeHealth(pane);
    }

    private static void makeHealth(Pane pane) {
        for (int i = 0; i < Game.getPlayersTank().size(); i++) {
            Tank tank = Game.getPlayersTank().get(i);
            Text healthTitle = new Text("Helth : ");
            healthTitle.setFont(new Font(40));
            healthTitle.setX(WINDOWS_WIDTH - 225);
            healthTitle.setY(WINDOWS_HEIGHT - 200 + i * 50);
            Text currentHealth = new Text(String.valueOf(tank.getHealth()));
            currentHealth.setFont(new Font(25));
            currentHealth.setX(WINDOWS_WIDTH - 100);
            currentHealth.setY(WINDOWS_HEIGHT - 200 + i * 50);
            pane.getChildren().add(currentHealth);
            pane.getChildren().add(healthTitle);

        }
    }

    public static void makeEndGameLose(Pane pane) {
        Text gameOver = new Text("Game Over");
        gameOver.setFont(new Font(50));
        gameOver.setX(MAP_FIRST_X + mapHeight / 2 - 150);
        gameOver.setY(MAP_FIRST_Y + mapHeight / 2);
        gameOver.setFill(Color.RED);
        pane.getChildren().add(gameOver);
    }

    public static void makeEndGameWin(Pane pane) {
        Text gameOver = new Text("Win");
        gameOver.setFont(new Font(50));
        gameOver.setX(MAP_FIRST_X + mapHeight / 2);
        gameOver.setY(MAP_FIRST_Y + mapHeight / 2);
        gameOver.setFill(Color.GREEN);
        pane.getChildren().add(gameOver);
    }

    public static void readMap(String[][] map, List<SceneObject> sceneObjects) {
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
                        case "P" -> sceneObjects.add(new Tank(TankType.Player, TankSide.Player, x, y, scale));
                        case "F" -> sceneObjects.add(new Flag(x, y, scale));
                        case "O", "A", "c", "C" -> attachEnemy(map[i][j], x, y, sceneObjects);
                        default -> {

                        }
                    }

                }
            }
        }
        addMapWall();
    }

    private static void attachEnemy(String s, double x, double y, List<SceneObject> sceneObjects) {
        switch (s) {
            case "O" -> sceneObjects.add(new Tank(TankType.NormalEnemy, TankSide.Enemy, x, y, scale));
            case "A" -> sceneObjects.add(new Tank(TankType.StrongEnemy, TankSide.Enemy, x, y, scale));
            case "c" -> {
                Tank sceneObject = new Tank(TankType.RandomEnemy, TankSide.Enemy, x, y, scale);
                sceneObject.setHealth(NORMAL_TANK_HEALTH);
                sceneObjects.add(sceneObject);
            }
            case "C" -> {
                Tank sceneObject = new Tank(TankType.RandomEnemy, TankSide.Enemy, x, y, scale);
                sceneObject.setHealth(STRONG_TANK_HEALTH);
                sceneObjects.add(sceneObject);
            }
            default -> {

            }
        }
    }

    private static void addMapWall() {
        int mapWallSize = 14;
        for (int i = 0; i < mapHeight / mapWallSize; i++) {
            //Left wall
            new Wall(WallType.Iron, MAP_FIRST_X - mapWallSize, (MAP_FIRST_Y) + (i) * mapWallSize, mapWallSize);
            //Right wall
            new Wall(WallType.Iron, mapHeight + MAP_FIRST_X, (MAP_FIRST_Y) + (i) * mapWallSize, mapWallSize);
            //Up wall
            new Wall(WallType.Iron, (MAP_FIRST_X) + (i) * mapWallSize, MAP_FIRST_Y - mapWallSize, mapWallSize);
            //Button
            new Wall(WallType.Iron, (MAP_FIRST_X) + (i) * mapWallSize, MAP_FIRST_Y + mapHeight, mapWallSize);
        }
    }

    public static String[][] readMapFile() {
        String fileName = "src/main/resources/map.txt";
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
            e.printStackTrace();
        }
        return null;
    }


}
