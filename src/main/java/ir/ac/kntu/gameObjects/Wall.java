package ir.ac.kntu.gameObjects;

import ir.ac.kntu.Game;
import ir.ac.kntu.gameObjects.tank.Tank;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import static ir.ac.kntu.GlobalConstance.scale;

public class Wall implements SceneObject {

    private ImageView imageView;

    private int health = 2;

    private WallType wallType;

    private int x;

    private int y;

    public Wall(WallType wallType, int x, int y) {
        this.wallType = wallType;
        this.x = x;
        this.y = y;
        imageView = new ImageView(GameObjectHelper.attachWallImage(wallType));
        imageView.setFitWidth(scale);
        imageView.setFitHeight(scale);
        Game.sceneObjects.add(this);
    }

    public Wall(int x, int y) {
        this.wallType = WallType.Iron;
        this.x = x;
        this.y = y;
        imageView = new ImageView(GameObjectHelper.attachWallImage(wallType));
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        Game.sceneObjects.add(this);
    }

    public void takeDamage(int damage) {
        if (wallType != WallType.Iron) {
            health -= damage;
        }
    }

    @Override
    public void update() {
        imageView.setX(x);
        imageView.setY(y);
        if (health <= 0) {
            Game.sceneObjects.remove(this);
        }
    }

    @Override
    public Node getNode() {
        return imageView;
    }

    @Override
    public boolean collidesWith(SceneObject object) {
        if (object instanceof Tank) {
            Tank tank = (Tank) object;
            double scale = imageView.getFitHeight();
            double tankLeft = x - scale / 2;
            double tankRight = x + scale / 2;
            double tankTop = y - scale / 2;
            double tankBottom = y + scale / 2;
            double shotLeft = tank.getX() - scale / 2;
            double shotRight = tank.getX() + scale / 2;
            double shotTop = tank.getY() - scale / 2;
            double shotBottom = tank.getY() + scale / 2;
            return tankLeft < shotRight && tankRight > shotLeft && tankTop < shotBottom && tankBottom > shotTop;
        }
        return false;
    }
}
