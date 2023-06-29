package ir.ac.kntu;

import ir.ac.kntu.eventHandler.EventHandler;
import ir.ac.kntu.gameObjects.SceneObject;
import ir.ac.kntu.gameObjects.wall.Wall;
import ir.ac.kntu.gameObjects.wall.WallType;
import ir.ac.kntu.gameObjects.tank.Tank;
import ir.ac.kntu.gameObjects.tank.TankSide;
import ir.ac.kntu.gameObjects.tank.TankType;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static ir.ac.kntu.GlobalConstance.*;


public class Game extends Application {

    public static Pane pane = new Pane();

    public static Scene scene = new Scene(pane);


    public static List<SceneObject> sceneObjects = new ArrayList<>();

    public static GameStatus gameStatus = GameStatus.Running;

    public static ArrayList<Tank> playersTank = new ArrayList<>();

    public static ArrayList<Tank> enemyTank = new ArrayList<>();

    private static String[][] map = new String[10][10];

    private int score = 0;


    public void start(Stage stage) {
        conformStage(stage);
        readMap();
        stage.show();
        new AnimationTimer() {
            private long lastUpdate = 0;

            public void handle(long currentNanoTime) {
                update();
                if (currentNanoTime - lastUpdate >= 100_000_000) {
                    pane.getChildren().clear();
                    makeGameScene();
                    for (SceneObject sceneObject : sceneObjects) {
                        pane.getChildren().add(sceneObject.getNode());
                    }
                    if (gameStatus == GameStatus.Stop) {
                        if (enemyTank.size() == 0) {
                            makeEndGameWin();
                        } else {
                            makeEndGame();
                        }
                    }

                    lastUpdate = currentNanoTime;
                }
            }
        }.start();

    }

    public static void main(String[] args) {
        launch(args);
    }

    private void update() {
        Iterator<SceneObject> iterator = sceneObjects.iterator();
        while (iterator.hasNext()) {
            SceneObject sceneObject = iterator.next();
            if (sceneObject.isVisible()) {
                sceneObject.update();
            } else {
                iterator.remove();
            }
        }
        int death = 0;
        for (Tank tank : playersTank) {
            if (tank.isDead()) {
                death++;
            }
        }
        if (death == playersTank.size()) {
            gameStatus = GameStatus.Stop;
        }
        if (enemyTank.size() == 0) {
            gameStatus = GameStatus.Stop;
        }
    }

    private void readMap() {
        map[2][2] = "B";
        mapSize = map.length;
        GlobalConstance.updateSize();
        Tank test = new Tank(TankType.RandomEnemy, TankSide.Enemy, MAP_FIRST_X + 4 * scale, MAP_FIRST_Y + 4 * scale, scale);
        Tank tes = new Tank(TankType.Player, TankSide.Player, mapSize / 2 * scale, (mapSize - 1) * scale + 25, scale);
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                if (map[i][j] != null) {
                    if (map[i][j].equals("B")) {
                        new Wall(WallType.Normal, MAP_FIRST_X + i * scale, MAP_FIRST_Y + j * scale, scale);
                    }
                    if (map[i][j].equals("M")) {
                        new Wall(WallType.Iron, MAP_FIRST_X + i * scale, MAP_FIRST_Y + j * scale, scale);
                    }
                }
            }
        }
        addMapWall();
    }

    private void conformStage(Stage stage) {
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

    private void makeGameScene() {
        Rectangle square = new Rectangle(mapHeight, mapHeight);
        square.setFill(Color.BLACK);
        square.setStroke(Color.WHITE);
        pane.getChildren().add(square);
        square.setX(MAP_FIRST_X);
        square.setY(MAP_FIRST_Y);
        Text scoreTitle = new Text("Score : ");
        scoreTitle.setFont(new Font(40));
        scoreTitle.setX(WINDOWS_WIDTH - 175);
        scoreTitle.setY(WINDOWS_HEIGHT - 100);
        Text currentScore = new Text(String.valueOf(score));
        currentScore.setFont(new Font(40));
        currentScore.setX(WINDOWS_WIDTH - 50);
        currentScore.setY(WINDOWS_HEIGHT - 100);
        pane.getChildren().add(currentScore);
        pane.getChildren().add(scoreTitle);
    }

    private void makeEndGame() {
        Text gameOver = new Text("Game Over");
        gameOver.setFont(new Font(50));
        gameOver.setX(MAP_FIRST_X + mapHeight / 2 - 150);
        gameOver.setY(MAP_FIRST_Y + mapHeight / 2);
        gameOver.setFill(Color.RED);
        pane.getChildren().add(gameOver);
        gameStatus = GameStatus.Stop;
    }

    private void makeEndGameWin() {
        Text gameOver = new Text("Win");
        gameOver.setFont(new Font(50));
        gameOver.setX(MAP_FIRST_X + mapHeight / 2);
        gameOver.setY(MAP_FIRST_Y + mapHeight / 2);
        gameOver.setFill(Color.GREEN);
        pane.getChildren().add(gameOver);
        gameStatus = GameStatus.Stop;
    }

    private void addMapWall() {
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

    public static ArrayList<Tank> getPlayersTank() {
        return playersTank;
    }

    public int getScore() {
        return score;
    }

}