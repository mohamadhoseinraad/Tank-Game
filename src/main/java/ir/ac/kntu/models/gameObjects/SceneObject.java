package ir.ac.kntu.models.gameObjects;

import javafx.scene.Node;

public interface SceneObject {

    void update();

    Node getNode();

    boolean collidesWith(SceneObject object);

    boolean isVisible();
}
