package ir.ac.kntu.gameobjects;

import javafx.scene.canvas.GraphicsContext;

public interface GameObject {

    boolean isColliding(GameObject b);

    boolean isPlayerCollisionFriendly();

    void draw(GraphicsContext gc);

    void removeFromScene();

    double getPositionX();

    double getPositionY();

    int getLayer();

    int getScale();

}

