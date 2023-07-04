package ir.ac.kntu.services.threads;

import ir.ac.kntu.services.GameData;
import ir.ac.kntu.services.RandGenerate;
import ir.ac.kntu.models.gameObjects.Flag;
import ir.ac.kntu.models.SceneObject;
import ir.ac.kntu.models.gameObjects.operatorGift.GifType;
import ir.ac.kntu.models.gameObjects.operatorGift.OperatorGift;
import ir.ac.kntu.models.gameObjects.tank.Tank;
import ir.ac.kntu.models.gameObjects.wall.Wall;
import org.jetbrains.annotations.NotNull;

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
                    OperatorGift operatorGift = createOperatorGift();
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

    @NotNull
    private OperatorGift createOperatorGift() {
        Point point = findEmptyPoint();
        GifType[] gifTypes = GifType.values();
        GifType selected = gifTypes[new Random().nextInt(gifTypes.length)];
        OperatorGift operatorGift = new OperatorGift(point.x, point.y, scale, selected);
        return operatorGift;
    }

    private Point findEmptyPoint() {
        int[][] nowayMap = new int[mapSize][mapSize];
        List<SceneObject> copyOfSceneObjects = new ArrayList<>(GameData.getInstance().getSceneObjects());
        updateNoWayMap(nowayMap, copyOfSceneObjects);
        int x, y;
        do {
            x = RandGenerate.getINSTANCE().getRanBetween(0, mapSize);
            y = RandGenerate.getINSTANCE().getRanBetween(0, mapSize);
        } while (nowayMap[y][x] == 1);
        x = (int) (MAP_FIRST_X + x * scale);
        y = (int) (MAP_FIRST_Y + y * scale);
        return new Point(x, y);
    }

    private void updateNoWayMap(int[][] nowayMap, List<SceneObject> copyOfSceneObjects) {
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