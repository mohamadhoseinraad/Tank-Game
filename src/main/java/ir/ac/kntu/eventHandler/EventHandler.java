package ir.ac.kntu.eventHandler;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class EventHandler {
    private static final EventHandler INSTANCE = new EventHandler();

    public static EventHandler getInstance() {
        return INSTANCE;
    }

    private EventHandler() {}

    public void attachEventHandlers(Scene scene){
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            KeyCode code = keyEvent.getCode();
            PlayerController.getInstance().handlePlayerMovements(code);
        });
        scene.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> {
        });
    }

}
