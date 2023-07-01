package ir.ac.kntu.models.gameObjects;

import javafx.scene.Node;

public interface SceneObject {

    public void update();

    public Node getNode();

    public boolean collidesWith(SceneObject object);

    public boolean isVisible();
}
