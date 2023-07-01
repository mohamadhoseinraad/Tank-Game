package ir.ac.kntu.models.gameObjects.wall;

import ir.ac.kntu.models.gameObjects.GameObjectHelper;
import ir.ac.kntu.models.gameObjects.SceneObject;
import ir.ac.kntu.models.gameObjects.tank.Tank;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class Wall implements SceneObject {

    private ImageView imageView;

    private int health = 2;

    private WallType wallType;

    private double x;

    private double y;

    private double scale;

    public Wall(WallType wallType, double x, double y, double scale) {
        this.wallType = wallType;
        this.x = x;
        this.y = y;
        this.scale = scale;
        imageView = new ImageView(GameObjectHelper.attachWallImage(wallType));
        imageView.setFitWidth(scale);
        imageView.setFitHeight(scale);
    }


    public void takeDamage(int damage) {
        if (wallType != WallType.Iron) {
            health -= damage;
            imageView.setImage(GameObjectHelper.normalWallDamaged);
        }
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

    public double getScale() {
        return scale;
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
        if (object instanceof Tank) {
            Tank tank = (Tank) object;
            double tankScale = tank.getScale();
            double wallLeft = x;
            double wallRight = x + scale;
            double wallTop = y;
            double wallBottom = y + scale;
            double tankLeft = tank.getX();
            double tankRight = tank.getX() + tankScale;
            double tankTop = tank.getY();
            double tankBottom = tank.getY() + tankScale;
            return wallLeft < tankRight && wallRight > tankLeft && wallTop < tankBottom && wallBottom > tankTop;
        }
        return false;
    }

    @Override
    public boolean isVisible() {
        if (health <= 0) {
            return false;
        }
        return true;
    }
}
