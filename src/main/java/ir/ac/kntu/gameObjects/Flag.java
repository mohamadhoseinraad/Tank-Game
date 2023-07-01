package ir.ac.kntu.gameObjects;

import ir.ac.kntu.Game;
import ir.ac.kntu.gameObjects.tank.TankSide;
import ir.ac.kntu.gameObjects.tank.TankType;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class Flag implements SceneObject {

    private int health = 3;

    private double x;

    private double y;

    private ImageView imageView;

    private double scale;

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

    public void setScale(double scale) {
        this.scale = scale;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
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
        if (health > 0) {
            return true;
        }
        return false;
    }
}
