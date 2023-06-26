package ir.ac.kntu.gamecontroller;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class EventHandler {
    private static EventHandler instance = new EventHandler();

    public static EventHandler getInstance() {
        return instance;
    }

    private EventHandler() {}

    public void attachEventHandlers(Scene scene){
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            KeyCode code = keyEvent.getCode();
            PlayerController.getInstance().handlePlayerMovements(code);
        });
        scene.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> {
            return;
        });
    }

}
