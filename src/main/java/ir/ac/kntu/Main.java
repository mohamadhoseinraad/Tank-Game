package ir.ac.kntu;

import ir.ac.kntu.constants.GlobalConstants;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;

import static ir.ac.kntu.constants.GlobalConstants.*;


/**
 * @author Sina Rostami
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();

        TextField textField = new TextField("AAA");
        root.getChildren().add(textField);
        Scene scene = new Scene(root, CANVAS_WIDTH, CANVAS_HEIGHT);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        Image image = new Image(new FileInputStream("src/main/resources/images/tankRed/red-tank-down.gif"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        imageView.setLayoutX(300);
        imageView.setLayoutY(400);
        root.getChildren().add(imageView);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}