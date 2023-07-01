package ir.ac.kntu;

import ir.ac.kntu.models.GameStatus;
import ir.ac.kntu.models.gameObjects.CountDownTimer;
import ir.ac.kntu.models.gameObjects.SceneObject;
import ir.ac.kntu.models.gameObjects.tank.Tank;
import ir.ac.kntu.scenes.SceneHelper;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Game extends Application {

    private static Pane pane = new Pane();


    private static Scene scene = new Scene(pane);


    private static List<SceneObject> sceneObjects = new ArrayList<>();

    private static GameStatus gameStatus = GameStatus.Running;

    private static ArrayList<Tank> playersTank = new ArrayList<>();

    private static ArrayList<Tank> enemyTank = new ArrayList<>();

    private String[][] map = SceneHelper.readMapFile();

    private static int score = 0;

    private static boolean enemyFreezing = false;

    private static CountDownTimer countDownTimer;


    public void start(Stage stage) {
        SceneHelper.conformStage(stage, pane, scene);
        //StartMenu.makeMenuScene(stage);
        Button button = new Button("Start");
        pane.getChildren().add(button);
        countDownTimer(stage, button);
        stage.show();


    }

    public void countDownTimer(Stage stage, Button start) {
        start.setOnAction(actionEvent -> {
            SceneHelper.conformStage(stage, pane, scene);
            SceneHelper.makeGameScene(pane);
            SceneHelper.readMap(map, sceneObjects);
            countDownTimer = new CountDownTimer(Game.sceneObjects);
            new AnimationTimer() {
                private long lastUpdate = 0;

                public void handle(long currentNanoTime) {
                    if (currentNanoTime - lastUpdate >= 1_000_000_000) {
                        update();
                        pane.getChildren().clear();
                        SceneHelper.makeGameScene(pane);
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
        });
    }

    private void startGame(Stage stage) {
        //SceneHelper.conformStage(stage, pane, scene);
        //SceneHelper.readMap(map);
        stage.show();

        EnemyTankMovement enemyTankMover = new EnemyTankMovement(Game.getEnemyTank());

        // Start a new thread with the enemyTankMover instance
        Thread enemyTankThread = new Thread(enemyTankMover);
        //enemyTankThread.setDaemon(true);
        enemyTankThread.start();
        new AnimationTimer() {
            private long lastUpdate = 0;

            public void handle(long currentNanoTime) {
                update();
                if (currentNanoTime - lastUpdate >= 100_000) {
                    pane.getChildren().clear();
                    SceneHelper.makeGameScene(pane);
                    draw();
                    if (gameStatus == GameStatus.Stop) {
                        enemyFreezing = true;
                        if (enemyTank.size() == 0) {
                            SceneHelper.makeEndGameWin(pane);
                        } else {
                            SceneHelper.makeEndGameLose(pane);
                        }
                    }

                    lastUpdate = currentNanoTime;
                }
            }
        }.start();
    }

    private void draw() {
        List<SceneObject> copyOfSceneObjects = new ArrayList<>(sceneObjects);
        for (SceneObject sceneObject : copyOfSceneObjects) {
            pane.getChildren().add(sceneObject.getNode());
        }
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

    public static List<SceneObject> getSceneObjects() {
        return sceneObjects;
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        Game.score = score;
    }

    public static boolean isEnemyFreezing() {
        return enemyFreezing;
    }

    public static void setEnemyFreezing(boolean enemyFreezing) {
        Game.enemyFreezing = enemyFreezing;
    }
}