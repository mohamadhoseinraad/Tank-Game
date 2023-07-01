package ir.ac.kntu;

import ir.ac.kntu.scenes.GamePage;
import ir.ac.kntu.scenes.SceneHelper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Game extends Application {

    private static final Pane PANE = new Pane();

    private static final Scene SCENE = new Scene(PANE);


    private final GameData gameData = GameData.getInstance();


    public void start(Stage stage) {
        SceneHelper.conformStage(stage, PANE, SCENE);
        //StartMenu.makeMenuScene(stage);
        Button button = new Button("Start");
        PANE.getChildren().add(button);
        GamePage.countDownTimer(stage, PANE, SCENE, gameData);
        stage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }


}