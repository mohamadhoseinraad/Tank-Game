package ir.ac.kntu.scenes;

import ir.ac.kntu.services.GameData;
import ir.ac.kntu.services.PlayerService;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class LoginPage {
    public static void loginBoxButtons(VBox loginBox, TextField usernameField, PasswordField passwordField,
                                       HBox bottomOfPage, Scene scene, Pane pane, Stage stage) {

        Button signOut = getSignOut(scene, pane, stage);
        Button rankPlayers = getRankPlayers();
        Button signUpConfirmButton = getSignUpConfirmButton(usernameField, passwordField);
        Button loginButton = getLoginButton(loginBox, usernameField, passwordField, bottomOfPage,
                scene, pane, stage, signOut, signUpConfirmButton);


        loginBox.getChildren().addAll(loginButton, signUpConfirmButton, rankPlayers);

    }

    @NotNull
    public static Button getLoginButton(VBox loginBox, TextField usernameField, PasswordField passwordField,
                                        HBox bottomOfPage, Scene scene, Pane pane, Stage stage, Button signOut,
                                        Button signUpConfirmButton) {
        Button loginButton = new Button("Log In");
        loginButton.setStyle("-fx-background-color: #808080; -fx-text-fill: white;");
        StartMenuHelper.loginEvent(loginBox, usernameField, passwordField, bottomOfPage, scene, pane, stage,
                loginButton, signOut, signUpConfirmButton);
        return loginButton;
    }

    @NotNull
    public static Button getSignUpConfirmButton(TextField usernameField, PasswordField passwordField) {
        Button signUpConfirmButton = new Button("Sign Up");
        signUpConfirmButton.setStyle("-fx-background-color: #808080; -fx-text-fill: white;");
        singUpEvent(usernameField, passwordField, signUpConfirmButton);
        return signUpConfirmButton;
    }

    public static void singUpEvent(TextField usernameField, PasswordField passwordField, Button signUpConfirmButton) {
        signUpConfirmButton.setOnMouseClicked(mouseEvent -> PlayerService.getINSTANCE().singUp(usernameField.getText(), passwordField.getText()));
    }

    @NotNull
    public static Button getSignOut(Scene scene, Pane pane, Stage stage) {
        Button signOut = new Button("Sing out");
        signOut.setStyle("-fx-background-color: #808080; -fx-text-fill: white;");
        singOutEvent(scene, pane, stage, signOut);
        return signOut;
    }

    @NotNull
    public static Button getRankPlayers() {
        Button rankPlayers = new Button("Rank Players");
        rankPlayers.setStyle("-fx-background-color: #808080; -fx-text-fill: white;");
        rankPlayerEvent(rankPlayers);
        return rankPlayers;
    }

    public static void rankPlayerEvent(Button rankPlayers) {
        rankPlayers.setOnMouseClicked(mouseEvent -> StageHelper.rankPlayersStage());
    }

    public static void singOutEvent(Scene scene, Pane pane, Stage stage, Button signOut) {
        signOut.setOnMouseClicked(mouseEvent -> {
            GameData.getInstance().setCurrentPlayer(null);
            StartMenu.makeMenuScene(stage, pane, scene);
        });
    }
}
