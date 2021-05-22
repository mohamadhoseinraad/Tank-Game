package ir.ac.kntu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * @author Sina Rostami
 */
public class Main extends Application {

    public static void main(String[] args) {
        // todo: don't modify this method.
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // todo: create a CalculatorGui.
        // todo: set its EventsHandlers.
        // todo: create some Panes.
        // todo: add your nodes to the panes (use CalculatorGui's interface (addNodesToPane)).

        // todo: set your main pane to scene. (change argument of Scene constructor)
        Scene scene = new Scene(new Pane());

        primaryStage.setScene(scene);
        primaryStage.show();
        // todo: enjoy your graphical calculator.
    }
}