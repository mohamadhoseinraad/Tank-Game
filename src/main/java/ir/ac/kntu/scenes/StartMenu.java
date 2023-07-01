package ir.ac.kntu.scenes;

import ir.ac.kntu.Game;
import ir.ac.kntu.GameStatus;
import ir.ac.kntu.gameObjects.CountDownTimer;
import ir.ac.kntu.gameObjects.SceneObject;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import static ir.ac.kntu.GlobalConstance.*;
import static ir.ac.kntu.GlobalConstance.BUTTON_STYLE;


public class StartMenu {

    public static void makeMenuScene(Stage stage) {

        Stage primaryStage = stage;
        BorderPane root;
        root = new BorderPane();
        root.setStyle("-fx-background-color: #000000;");
        VBox page = new VBox();
        HBox topOfPage = makeTitleGame();
        HBox bottomOfPage = new HBox();
        VBox loginBox = loginBox();
        HBox levelsButtons = makeLevelsButton(stage);
        bottomOfPage.getChildren().add(loginBox);
        bottomOfPage.getChildren().add(levelsButtons);
        page.getChildren().add(topOfPage);
        page.getChildren().add(bottomOfPage);
        root.setCenter(page);
        primaryStage.setScene(new Scene(root));
    }


    private static HBox makeLevelsButton(Stage stage) {
        HBox levelsButtons = new HBox();
        VBox optionButtons = makeOptionsButton();
        VBox levelsButtonsLeft = new VBox();
        VBox levelsButtonsRight = new VBox();
        levelsButtons.setAlignment(Pos.CENTER);
        levelsButtons.setPadding(new Insets(30));
        ir.ac.kntu.Stage[] levels = ir.ac.kntu.Stage.values();
        conformVbox(levelsButtonsLeft);
        conformVbox(levelsButtonsRight);
        for (int i = 0; i < levels.length; i++) {
            Button stageButton = new Button("Stage " + levels[i]);
            stageButton.setStyle(BUTTON_STYLE);
            stageButton.setOnMouseEntered(event -> stageButton.setStyle(BUTTON_STYLE_2));
            stageButton.setOnMouseExited(event -> stageButton.setStyle(BUTTON_STYLE));
            if (i < levels.length / 2) {
                levelsButtonsLeft.getChildren().add(stageButton);
            } else {
                levelsButtonsRight.getChildren().add(stageButton);
            }
            //countDownTimer(stage, stageButton);
        }
        levelsButtons.setAlignment(Pos.CENTER_RIGHT);
        levelsButtons.getChildren().add(levelsButtonsLeft);
        levelsButtons.getChildren().add(levelsButtonsRight);
        levelsButtons.getChildren().add(optionButtons);

        return levelsButtons;
    }


    private static VBox makeOptionsButton() {
        VBox optionButtons = new VBox();
        conformVbox(optionButtons);
        Button customStageButton = new Button("Custom Stage");
        customStageButton.setStyle(BUTTON_STYLE);
        customStageButton.setOnMouseEntered(event -> customStageButton.setStyle(BUTTON_STYLE_2));
        customStageButton.setOnMouseExited(event -> customStageButton.setStyle(BUTTON_STYLE));
        optionButtons.getChildren().add(customStageButton);

        return optionButtons;
    }

    private static VBox loginBox() {
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


        loginBox.getChildren().addAll(loginLabel, usernameField, passwordField);
        loginBoxButtons(loginBox);

        loginBox.setPadding(new Insets(30));
        return loginBox;
    }

    private static void loginBoxButtons(VBox loginBox) {
        Button loginButton = new Button("Log In");
        loginButton.setStyle("-fx-background-color: #808080; -fx-text-fill: white;");

        Button signUpConfirmButton = new Button("Sign Up");
        signUpConfirmButton.setStyle("-fx-background-color: #808080; -fx-text-fill: white;");

        loginBox.getChildren().addAll(loginButton, signUpConfirmButton);

    }

    private static HBox makeTitleGame() {
        HBox titleGame = new HBox();
        Label tankGame = new Label("Tank Game");
        tankGame.setTextFill(Color.WHITE);
        tankGame.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        tankGame.setAlignment(Pos.CENTER);

        titleGame.getChildren().add(tankGame);
        titleGame.setAlignment(Pos.CENTER);
        titleGame.setPadding(new Insets(40));

        return titleGame;
    }

    private static void conformVbox(VBox vBox) {
        vBox.setSpacing(5);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10));
    }

}