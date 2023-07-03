package ir.ac.kntu.scenes.start;

import ir.ac.kntu.models.Level;
import ir.ac.kntu.scenes.game.GamePage;
import ir.ac.kntu.scenes.StageHelper;
import ir.ac.kntu.services.GameData;
import ir.ac.kntu.services.PlayerService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import static ir.ac.kntu.GlobalConstance.*;

public class StartMenuHelper {
    public static void makeLevelsButton(Stage stage, Pane pane, Scene scene, HBox bottomOfPage) {
        HBox levelsButtons = new HBox();
        VBox optionButtons = makeOptionsButton(stage, pane, scene);
        VBox levelsButtonsLeft = new VBox();
        VBox levelsButtonsRight = new VBox();
        levelsButtons.setAlignment(Pos.CENTER);
        levelsButtons.setPadding(new Insets(30));
        Level[] levels = Level.values();
        conformVbox(levelsButtonsLeft);
        conformVbox(levelsButtonsRight);
        for (int i = 0; i < levels.length; i++) {
            makeEachButton(levels, i, stage, pane, scene, levelsButtonsLeft, levelsButtonsRight);
            if (levels[i] == GameData.getInstance().getCurrentPlayer().getLastStage()) {
                break;
            }
        }
        levelsButtons.setAlignment(Pos.CENTER_RIGHT);
        levelsButtons.getChildren().add(levelsButtonsLeft);
        levelsButtons.getChildren().add(levelsButtonsRight);
        levelsButtons.getChildren().add(optionButtons);

        bottomOfPage.getChildren().add(levelsButtons);
    }

    public static void makeEachButton(Level[] levels, int i, Stage stage, Pane pane,
                                      Scene scene, VBox levelsButtonsLeft, VBox levelsButtonsRight) {
        Button stageButton = new Button("Stage " + levels[i]);
        stageButton.setStyle(BUTTON_STYLE);
        stageButton.setOnMouseEntered(event -> stageButton.setStyle(BUTTON_STYLE_2));
        stageButton.setOnMouseExited(event -> stageButton.setStyle(BUTTON_STYLE));
        if (i < levels.length / 2) {
            levelsButtonsLeft.getChildren().add(stageButton);
        } else {
            levelsButtonsRight.getChildren().add(stageButton);
        }
        stageButton.setOnMouseClicked(mouseEvent -> GamePage.countDownTimer(levels[i], stage,
                pane, scene, GameData.getInstance(),
                PLAYER_TANK_HEALTH));
    }

    public static VBox makeOptionsButton(Stage stage, Pane pane, Scene scene) {
        VBox optionButtons = new VBox();
        conformVbox(optionButtons);
        customModeButton(stage, pane, scene, optionButtons);
        playerModeButton(optionButtons);

        return optionButtons;
    }

    public static void customModeButton(Stage stage, Pane pane, Scene scene, VBox optionButtons) {
        Button customStageButton = new Button("Custom Stage");
        customStageButton.setStyle(BUTTON_STYLE);
        customStageButton.setOnMouseEntered(event -> customStageButton.setStyle(BUTTON_STYLE_2));
        customStageButton.setOnMouseExited(event -> customStageButton.setStyle(BUTTON_STYLE));
        optionButtons.getChildren().add(customStageButton);
        customStageButton.setOnMouseClicked(mouseEvent -> StageHelper.getFileNameStage(stage, pane, scene));
    }

    public static void playerModeButton(VBox optionButtons) {
        CheckBox player2 = new CheckBox("Player 2");
        player2.setStyle(BUTTON_STYLE);
        player2.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                player2.setStyle(BUTTON_STYLE_2);
            } else {
                player2.setStyle(BUTTON_STYLE);
            }
            GameData.getInstance().player2Mode = true;
        });
        optionButtons.getChildren().add(player2);
    }

    public static VBox loginBox(HBox bottomOfPage, Scene scene, Pane pane, Stage stage) {
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
        LoginPage.loginBoxButtons(loginBox, usernameField, passwordField, bottomOfPage, scene, pane, stage);

        loginBox.setPadding(new Insets(30));
        return loginBox;
    }

    public static void loginEvent(VBox loginBox, TextField usernameField, PasswordField passwordField,
                                  HBox bottomOfPage, Scene scene, Pane pane, Stage stage, Button loginButton,
                                  Button signOut, Button signUpConfirmButton) {
        loginButton.setOnMouseClicked(mouseEvent -> {
            if (PlayerService.getINSTANCE().login(usernameField.getText(), passwordField.getText())) {
                makeLevelsButton(stage, pane, scene, bottomOfPage);
                loginBox.getChildren().removeAll(loginButton, signUpConfirmButton);
                loginBox.getChildren().add(signOut);
            }
        });
    }

    public static void conformVbox(VBox vBox) {
        vBox.setSpacing(5);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10));
    }
}
