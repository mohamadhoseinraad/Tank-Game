package ir.ac.kntu.models.gameObjects;

import ir.ac.kntu.models.GameObjectHelper;
import ir.ac.kntu.models.SceneObject;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class Flag implements SceneObject {

    private int health = 3;

    private final double x;

    private final double y;

    private final ImageView imageView;

    private final double scale;

    public Flag(double x, double y, double scale) {
        imageView = new ImageView(GameObjectHelper.flag1);
        this.scale = scale;
        imageView.setFitWidth(scale);
        imageView.setFitHeight(scale);
        this.x = x;
        this.y = y;
    }

    public double getScale() {
        return scale;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    @Override
    public void update() {
        imageView.setX(x);
        imageView.setY(y);

    }

    @Override
    public Node getNode() {
        return imageView;
    }

    @Override
    public boolean collidesWith(SceneObject object) {
        return false;
    }

    @Override
    public boolean isVisible() {
        return health > 0;
    }
}
