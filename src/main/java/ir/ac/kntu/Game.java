package ir.ac.kntu;

import ir.ac.kntu.gameObjects.CountDownTimer;
import ir.ac.kntu.gameObjects.Direction;
import ir.ac.kntu.gameObjects.SceneObject;
import ir.ac.kntu.gameObjects.tank.Tank;
import ir.ac.kntu.scenes.SceneHelper;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static ir.ac.kntu.GlobalConstance.NORMAL_TANK_HEALTH;
import static ir.ac.kntu.GlobalConstance.STRONG_TANK_HEALTH;


public class Game extends Application {

    public static Pane pane = new Pane();

    public static Scene scene = new Scene(pane);


    public static List<SceneObject> sceneObjects = new ArrayList<>();

    public static GameStatus gameStatus = GameStatus.Running;

    private static ArrayList<Tank> playersTank = new ArrayList<>();

    private static ArrayList<Tank> enemyTank = new ArrayList<>();

    private String[][] map = SceneHelper.readMapFile();

    public static int score = 0;


    public void start(Stage stage) {
        SceneHelper.conformStage(stage, pane, scene);
        stage.show();
        CountDownTimer countDownTimer = new CountDownTimer(sceneObjects);
        new AnimationTimer() {
            private long lastUpdate = 0;

            public void handle(long currentNanoTime) {
                if (currentNanoTime - lastUpdate >= 1_000_000_000) {
                    update();
                    pane.getChildren().clear();
                    for (SceneObject sceneObject : sceneObjects) {
                        pane.getChildren().add(sceneObject.getNode());
                    }
                    if (countDownTimer.isEnd()) {
                        this.stop();
                        gameStatus = GameStatus.Running;
                        startGame(stage);
                    }
                    lastUpdate = currentNanoTime;
                }
            }
        }.start();


    }

    private void startGame(Stage stage) {
        SceneHelper.conformStage(stage, pane, scene);
        SceneHelper.readMap(map);
        stage.show();

        EnemyTankMovement enemyTankMover = new EnemyTankMovement(Game.getEnemyTank());

        // Start a new thread with the enemyTankMover instance
        Thread enemyTankThread = new Thread(enemyTankMover);
        enemyTankThread.setDaemon(true);
        enemyTankThread.start();
        new AnimationTimer() {
            private long lastUpdate = 0;

            public void handle(long currentNanoTime) {
                update();
                if (currentNanoTime - lastUpdate >= 100_000_000) {
                    pane.getChildren().clear();
                    SceneHelper.makeGameScene();
                    for (SceneObject sceneObject : sceneObjects) {
                        pane.getChildren().add(sceneObject.getNode());
                    }
                    if (gameStatus == GameStatus.Stop) {
                        if (enemyTank.size() == 0) {
                            SceneHelper.makeEndGameWin();
                        } else {
                            SceneHelper.makeEndGameLose();
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
//        Iterator<SceneObject> iterator = sceneObjects.iterator();
//        while (iterator.hasNext()) {
//            SceneObject sceneObject = iterator.next();

        List<SceneObject> copyOfSceneObjects = new ArrayList<>(sceneObjects);
        for (SceneObject sceneObject : copyOfSceneObjects) {
            if (sceneObject.isVisible()) {
                sceneObject.update();
            } else {
                sceneObjects.remove(sceneObject);
            }
        }
        if (playersTank.size() == 0 || enemyTank.size() == 0) {
            gameStatus = GameStatus.Stop;
        }
    }

    public static ArrayList<Tank> getPlayersTank() {
        return playersTank;
    }

    public static ArrayList<Tank> getEnemyTank() {
        return enemyTank;
    }
}