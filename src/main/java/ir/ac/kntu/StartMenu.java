package ir.ac.kntu;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static ir.ac.kntu.GlobalConstance.WINDOWS_HEIGHT;
import static ir.ac.kntu.GlobalConstance.WINDOWS_WIDTH;


public class StartMenu {

    private static Button start = new Button("Start");

    public static void start(Pane pane) {
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
    }

    private static void makeScene() {
        start.setLayoutX(WINDOWS_WIDTH / 2 - 50);
        start.setLayoutY(WINDOWS_HEIGHT / 3 - 25);
        start.setTextFill(Color.BLACK);
        start.setPrefSize(100, 50);
    }
}