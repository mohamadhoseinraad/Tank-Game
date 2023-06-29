package ir.ac.kntu.scenes;

import ir.ac.kntu.Game;
import ir.ac.kntu.GameStatus;
import ir.ac.kntu.gameObjects.CountDownTimer;
import ir.ac.kntu.gameObjects.SceneObject;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import static ir.ac.kntu.GlobalConstance.WINDOWS_HEIGHT;
import static ir.ac.kntu.GlobalConstance.WINDOWS_WIDTH;


public class StartMenu {

    private static Button start = new Button("Start");

    public static Scene start(Pane pane) {
        makeScene();
        start.setOnAction(actionEvent -> {
            new CountDownTimer(Game.sceneObjects);
            new AnimationTimer() {
                private long lastUpdate = 0;

                public void handle(long currentNanoTime) {
                    pane.getChildren().clear();
                    if (currentNanoTime - lastUpdate >= 1_000_000_000) {
                        for (SceneObject sceneObject : Game.sceneObjects) {
                            sceneObject.update();
                        }
                        lastUpdate = currentNanoTime;
                    }
                    for (SceneObject sceneObject : Game.sceneObjects) {
                        pane.getChildren().add(sceneObject.getNode());
                    }
                    if (Game.gameStatus == GameStatus.Start) {
                        this.stop();
                    }
                }
            }.start();
        });

        pane.getChildren().add(start);
        return new Scene(pane);
    }

    private static void makeScene() {
        start.setLayoutX(WINDOWS_WIDTH / 2 - 50);
        start.setLayoutY(WINDOWS_HEIGHT / 3 - 25);
        start.setTextFill(Color.BLACK);
        start.setPrefSize(100, 50);
    }
}