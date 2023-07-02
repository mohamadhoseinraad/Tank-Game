package ir.ac.kntu.threads;

import ir.ac.kntu.GameData;
import ir.ac.kntu.models.gameObjects.Direction;
import ir.ac.kntu.models.gameObjects.Flag;
import ir.ac.kntu.models.gameObjects.SceneObject;
import ir.ac.kntu.models.gameObjects.operatorGift.GifType;
import ir.ac.kntu.models.gameObjects.operatorGift.OperatorGift;
import ir.ac.kntu.models.gameObjects.tank.Tank;
import ir.ac.kntu.models.gameObjects.wall.Wall;
import ir.ac.kntu.scenes.SceneHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ir.ac.kntu.GlobalConstance.*;
import static ir.ac.kntu.GlobalConstance.scale;

public class GiveGift extends Thread {


    public GiveGift() {
    }

    @Override
    public void run() {
        while (true) {
            try {
                sleep(1000); // wait for 1 second
                if (GameData.getInstance().sendGift) {
                    Point point = findEmptyPoint();
                    GifType[] gifTypes = GifType.values();
                    GifType selected = gifTypes[new Random().nextInt(0, gifTypes.length)];
                    OperatorGift operatorGift = new OperatorGift(point.x, point.y, scale, selected);
                    GameData.getInstance().getSceneObjects().add(operatorGift);
                    GameData.getInstance().sendGift = false;
                    sleep(5000);
                    operatorGift.setExpired(true);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Point findEmptyPoint() {
        int[][] nowayMap = new int[mapSize][mapSize];
        List<SceneObject> copyOfSceneObjects = new ArrayList<>(GameData.getInstance().getSceneObjects());
        for (SceneObject sceneObject : copyOfSceneObjects) {
            if (sceneObject instanceof Wall wall) {
                applyWall(nowayMap, wall);
            } else if (sceneObject instanceof Tank tank) {
                applyTank(nowayMap, tank);
            } else if (sceneObject instanceof Flag flag) {
                applyFlag(nowayMap, flag);
            } else if (sceneObject instanceof OperatorGift gift) {
                applyGift(nowayMap, gift);
            }
        }
        int x, y;
        do {
            x = new Random().nextInt(0, mapSize);
            y = new Random().nextInt(0, mapSize);
        } while (nowayMap[y][x] == 1);
        x = (int) (MAP_FIRST_X + x * scale);
        y = (int) (MAP_FIRST_Y + y * scale);
        return new Point(x, y);
    }

    private static void applyGift(int[][] nowayMap, OperatorGift gift) {
        int j = (int) ((gift.getX() - MAP_FIRST_X) / scale);
        int i = (int) ((gift.getY() - MAP_FIRST_Y) / scale);
        nowayMap[i][j] = 1;
    }

    private static void applyFlag(int[][] nowayMap, Flag flag) {
        int j = (int) ((flag.getX() - MAP_FIRST_X) / scale);
        int i = (int) ((flag.getY() - MAP_FIRST_Y) / scale);
        nowayMap[i][j] = 1;
    }

    private static void applyTank(int[][] nowayMap, Tank tank) {
        int j = (int) ((tank.getX() - MAP_FIRST_X) / scale);
        int i = (int) ((tank.getY() - MAP_FIRST_Y) / scale);
        nowayMap[i][j] = 1;
    }

    private void applyWall(int[][] nowayMap, Wall wall) {
        if (wall.getX() > MAP_FIRST_X && wall.getX() < MAP_FIRST_X + mapHeight
                && wall.getY() > MAP_FIRST_Y && wall.getY() < MAP_FIRST_Y + mapHeight) {
            int j = (int) ((wall.getX() - MAP_FIRST_X) / scale);
            int i = (int) ((wall.getY() - MAP_FIRST_Y) / scale);
            nowayMap[i][j] = 1;
        }
    }


}