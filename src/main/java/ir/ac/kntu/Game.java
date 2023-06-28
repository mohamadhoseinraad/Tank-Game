package ir.ac.kntu;

import ir.ac.kntu.eventHandler.EventHandler;
import ir.ac.kntu.gameObjects.SceneObject;
import ir.ac.kntu.gameObjects.Wall;
import ir.ac.kntu.gameObjects.WallType;
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
import java.util.List;

import static ir.ac.kntu.GlobalConstance.*;


public class Game extends Application {


    public static List<SceneObject> sceneObjects = new ArrayList<>();

    public static GameStatus gameStatus = GameStatus.Stop;

    private static Tank player = new Tank(TankType.Player, TankSide.Player, mapSize / 2 * scale, (mapSize - 1) * scale + 25);

    private static Tank test = new Tank(TankType.RandomEnemy, TankSide.Player, 50, 50);

    private static Wall testWall = new Wall(WallType.Normal, 50, 100);

    private static Wall testWall2 = new Wall(50, 100);

    private int score = 0;

    public Pane pane = new Pane();

    public Scene scene = new Scene(pane);

    public void start(Stage stage) {
        pane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        //StartMenu.start(pane);
        conformStage(stage);
        EventHandler.getInstance().attachEventHandlers(scene);
        stage.show();
        new AnimationTimer() {
            private long lastUpdate = 0;

            public void handle(long currentNanoTime) {
                for (SceneObject sceneObject : sceneObjects) {
                    sceneObject.update();
                }
                if (currentNanoTime - lastUpdate >= 1_000_000_000) {
                    pane.getChildren().clear();
                    if (!player.isDead()) {
                        makeGameScene();
                        for (SceneObject sceneObject : sceneObjects) {
                            pane.getChildren().add(sceneObject.getNode());
                        }
                    } else {
                        makeEndGame();
                    }

                    lastUpdate = currentNanoTime;
                }
            }
        }.start();

    }

    public static void main(String[] args) {
        launch(args);
    }

    private void conformStage(Stage stage) {
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
        Rectangle square = new Rectangle(500, 500);
        square.setFill(Color.BLACK);
        square.setStroke(Color.WHITE);
        pane.getChildren().add(square);
        square.setX(50);
        square.setY(25);
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
        for (int i = 0; i < 26; i++) {
            new Wall(30, i * 20);
            new Wall(550, i * 20);
            new Wall(30 + i * 20, 0);
            new Wall(30 + i * 20, 525);
        }
    }

    private void makeEndGame() {
        Text gameOver = new Text("Game Over");
        gameOver.setFont(new Font(50));
        gameOver.setX(WINDOWS_WIDTH / 2 - 200);
        gameOver.setY(WINDOWS_HEIGHT / 2);
        gameOver.setFill(Color.RED);
        pane.getChildren().add(gameOver);
        gameStatus = GameStatus.Stop;
    }

    public static Tank getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }

}