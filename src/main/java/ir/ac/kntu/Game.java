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


public class Game extends Application {

    public static List<SceneObject> sceneObjects = new ArrayList<>();

    public static GameStatus gameStatus = GameStatus.Stop;

    public void start(Stage stage) {
        Pane pane = new Pane();
        Scene scene = new Scene(pane);
        pane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        StartMenu.start(pane);
        stage.setScene(scene);
        conformStage(stage);
        stage.show();
        new AnimationTimer() {
            private long lastUpdate = 0;

            public void handle(long currentNanoTime) {
                if (currentNanoTime - lastUpdate >= 1_000_000_000) {
                    if (gameStatus == GameStatus.Start) {
                        gameStatus = GameStatus.Stop;
                        pane.getChildren().clear();
                        Button start = new Button("Stop");
                        start.setLayoutX(WINDOWS_WIDTH / 2 - 50);
                        start.setLayoutY(WINDOWS_HEIGHT / 3 - 25);
                        start.setTextFill(Color.WHITE);
                        start.setPrefSize(100, 50);
                        pane.getChildren().add(start);
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
        stage.setTitle("Tank Game");
        stage.setHeight(WINDOWS_HEIGHT);
        stage.setWidth(WINDOWS_WIDTH);
        stage.setMaxHeight(WINDOWS_HEIGHT);
        stage.setMaxWidth(WINDOWS_WIDTH);
        stage.setMinHeight(WINDOWS_HEIGHT);
        stage.setMinWidth(WINDOWS_WIDTH);
    }
}