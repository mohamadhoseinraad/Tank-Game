package ir.ac.kntu;

import ir.ac.kntu.gameObjects.CountDownTimer;
import ir.ac.kntu.gameObjects.Direction;
import ir.ac.kntu.gameObjects.SceneObject;
import ir.ac.kntu.gameObjects.tank.Tank;
import ir.ac.kntu.scenes.SceneHelper;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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

    public static boolean enemyFreezing = false;

    public static CountDownTimer countDownTimer;


    public void start(Stage stage) {
        SceneHelper.conformStage(stage, pane, scene);
        makeMenuScene(stage);
        stage.show();


    }

    public void countDownTimer(Stage stage, Button start) {
        start.setOnAction(actionEvent -> {
            SceneHelper.conformStage(stage, pane, scene);
            SceneHelper.makeGameScene();
            SceneHelper.readMap(map);
            countDownTimer = new CountDownTimer(Game.sceneObjects);
            new AnimationTimer() {
                private long lastUpdate = 0;

                public void handle(long currentNanoTime) {
                    if (currentNanoTime - lastUpdate >= 1_000_000_000) {
                        update();
                        pane.getChildren().clear();
                        SceneHelper.makeGameScene();
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
                    SceneHelper.makeGameScene();
                    draw();
                    if (gameStatus == GameStatus.Stop) {
                        enemyFreezing = true;
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

    public void makeMenuScene(Stage stage) {

        Stage primaryStage = stage;
        BorderPane root;
        root = new BorderPane();
        root.setStyle("-fx-background-color: #000000;");
        VBox loginBox = makeMenuLeft(primaryStage, root);
        GridPane signUpBox;
        HBox stageButtonsBox;

        TextField signUpUsernameField;
        PasswordField signUpPasswordField;
        PasswordField confirmSignUpPasswordField;


        // create sign-up box
        signUpBox = new GridPane();
        signUpBox.setHgap(10);
        signUpBox.setVgap(10);
        signUpBox.setAlignment(Pos.CENTER_LEFT);

        signUpUsernameField = new TextField();
        signUpUsernameField.setPromptText("Username");

        signUpPasswordField = new PasswordField();
        signUpPasswordField.setPromptText("Password");

        confirmSignUpPasswordField = new PasswordField();
        confirmSignUpPasswordField.setPromptText("Confirm Password");

        Button signUpConfirmButton = new Button("Sign Up");
        signUpConfirmButton.setStyle("-fx-background-color: #808080; -fx-text-fill: white;");

        signUpBox.addRow(0, new Label("Sign Up"), signUpUsernameField);
        signUpBox.addRow(1, new Label(""), signUpPasswordField);
        signUpBox.addRow(2, new Label(""), confirmSignUpPasswordField);
        signUpBox.addRow(3, new Label(""), signUpConfirmButton);

        // create stage buttons box
        stageButtonsBox = new HBox();
        stageButtonsBox.setSpacing(10);
        stageButtonsBox.setAlignment(Pos.CENTER_RIGHT);

        for (int i = 1; i <= 10; i++) {
            Button stageButton = new Button("Stage " + i);
            stageButton.setStyle("-fx-background-color: #f7dc6f; -fx-text-fill: black; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-color: black; -fx-border-width: 2px; -fx-background-radius: 20px;");
            stageButton.setOnMouseEntered(event -> stageButton.setEffect(new DropShadow()));
            stageButton.setOnMouseExited(event -> stageButton.setEffect(null));
            stageButtonsBox.getChildren().add(stageButton);
            countDownTimer(stage, stageButton);
        }

        Button customStageButton = new Button("Custom Stage");
        customStageButton.setStyle("-fx-background-color: #f7dc6f; -fx-text-fill: black; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-color: black; -fx-border-width: 2px; -fx-background-radius: 20px;");
        customStageButton.setOnMouseEntered(event -> customStageButton.setEffect(new DropShadow()));
        customStageButton.setOnMouseExited(event -> customStageButton.setEffect(null));
        stageButtonsBox.getChildren().add(customStageButton);

        root.setLeft(loginBox);
        root.setRight(stageButtonsBox);
        primaryStage.setScene(new Scene(root));
    }

    private VBox makeMenuLeft(Stage primaryStage, BorderPane root) {
        VBox loginBox;
        TextField usernameField;
        PasswordField passwordField;

        // create login box
        loginBox = new VBox();
        loginBox.setSpacing(10);
        loginBox.setAlignment(Pos.CENTER_LEFT);

        Label loginLabel = new Label("Log In");
        loginLabel.setTextFill(Color.WHITE);
        loginLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        usernameField = new TextField();
        usernameField.setPromptText("Username");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Log In");
        loginButton.setStyle("-fx-background-color: #808080; -fx-text-fill: white;");

        Label signUpLabel = new Label("Sign Up");
        signUpLabel.setTextFill(Color.WHITE);
        signUpLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        Button signUpButton = new Button("Sign Up");
        signUpButton.setStyle("-fx-background-color: #808080; -fx-text-fill: white;");

        loginBox.getChildren().addAll(loginLabel, usernameField, passwordField, loginButton, signUpLabel, signUpButton);

        return loginBox;
    }
}