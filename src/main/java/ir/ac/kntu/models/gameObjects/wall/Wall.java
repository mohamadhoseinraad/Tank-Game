package ir.ac.kntu.models.gameObjects.wall;

import ir.ac.kntu.models.gameObjects.GameObjectHelper;
import ir.ac.kntu.models.gameObjects.SceneObject;
import ir.ac.kntu.models.gameObjects.tank.Tank;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class Wall implements SceneObject {

    private final ImageView imageView;

    private int health = 2;

    private final WallType wallType;

    private final double x;

    private final double y;

    private final double scale;

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

    public double getY() {
        return y;
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
        if (object instanceof Tank tank) {
            double tankScale = tank.getScale();
            double wallRight = x + scale;
            double wallBottom = y + scale;
            double tankLeft = tank.getX();
            double tankRight = tank.getX() + tankScale;
            double tankTop = tank.getY();
            double tankBottom = tank.getY() + tankScale;
            return x < tankRight && wallRight > tankLeft && y < tankBottom && wallBottom > tankTop;
        }
        return false;
    }

    @Override
    public boolean isVisible() {
        return health > 0;
    }
}
