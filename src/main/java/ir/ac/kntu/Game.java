package ir.ac.kntu;

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
        SceneHelper.conformStage(stage);
        SceneHelper.readMap(map);
        stage.show();
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
        Iterator<SceneObject> iterator = sceneObjects.iterator();
        while (iterator.hasNext()) {
            SceneObject sceneObject = iterator.next();
            if (sceneObject.isVisible()) {
                sceneObject.update();
            } else {
                iterator.remove();
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