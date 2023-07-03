package ir.ac.kntu.scenes;

import ir.ac.kntu.GlobalConstance;
import ir.ac.kntu.models.gameObjects.Flag;
import ir.ac.kntu.models.gameObjects.SceneObject;
import ir.ac.kntu.models.gameObjects.tank.Tank;
import ir.ac.kntu.models.gameObjects.tank.TankSide;
import ir.ac.kntu.models.gameObjects.tank.TankType;
import ir.ac.kntu.models.gameObjects.wall.Wall;
import ir.ac.kntu.models.gameObjects.wall.WallType;
import ir.ac.kntu.services.GameData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static ir.ac.kntu.GlobalConstance.*;

public class GameEnvironmentHelper {
    public static void readMap(String[][] map, List<SceneObject> sceneObjects, int health) {
        mapSize = map.length;
        GlobalConstance.updateSize();
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                if (map[i][j] != null) {
                    double x = MAP_FIRST_X + j * scale;
                    double y = MAP_FIRST_Y + i * scale;
                    switch (map[i][j]) {
                        case "B" -> sceneObjects.add(new Wall(WallType.Normal, x, y, scale));
                        case "M" -> sceneObjects.add(new Wall(WallType.Iron, x, y, scale));
                        case "P", "F" -> attachPlayers(map[i][j], x, y, sceneObjects, health);
                        case "O", "A", "c", "C" -> attachEnemy(map[i][j], x, y, sceneObjects);
                        default -> {

                        }
                    }

                }
            }
        }
        addMapWall(sceneObjects);
    }

    private static void attachPlayers(String s, double x, double y, List<SceneObject> sceneObjects, int health) {
        switch (s) {
            case "P" -> {
                Tank tank = new Tank(TankType.Player, TankSide.Player, x, y, scale);
                tank.setHealth(health);
                sceneObjects.add(tank);
                GameData.getInstance().getPlayersTank().add(tank);
            }
            case "F" -> {
                Flag flag = new Flag(x, y, scale);
                sceneObjects.add(flag);
                GameData.getInstance().setPlayersFlag(flag);
            }
            default -> {

            }
        }
    }

    private static void attachEnemy(String s, double x, double y, List<SceneObject> sceneObjects) {
        Tank sceneObject = null;
        switch (s) {
            case "O" -> {
                sceneObject = new Tank(TankType.NormalEnemy, TankSide.Enemy, x, y, scale);
                sceneObjects.add(sceneObject);
            }
            case "A" -> {
                sceneObject = new Tank(TankType.StrongEnemy, TankSide.Enemy, x, y, scale);
                sceneObjects.add(sceneObject);
            }
            case "c" -> {
                sceneObject = new Tank(TankType.RandomEnemy, TankSide.Enemy, x, y, scale);
                sceneObject.setHealth(NORMAL_TANK_HEALTH);
                sceneObjects.add(sceneObject);
            }
            case "C" -> {
                sceneObject = new Tank(TankType.RandomEnemy, TankSide.Enemy, x, y, scale);
                sceneObject.setHealth(STRONG_TANK_HEALTH);
                sceneObjects.add(sceneObject);
            }
            default -> {

            }
        }
        GameData.getInstance().getEnemyTank().add(sceneObject);
    }

    private static void addMapWall(List<SceneObject> sceneObjects) {
        int mapWallSize = 14;
        for (int i = 0; i < mapHeight / mapWallSize; i++) {
            //Left wall
            sceneObjects.add(new Wall(WallType.Iron, MAP_FIRST_X - mapWallSize,
                    (MAP_FIRST_Y) + (i) * mapWallSize, mapWallSize));
            //Right wall
            sceneObjects.add(new Wall(WallType.Iron, mapHeight + MAP_FIRST_X,
                    (MAP_FIRST_Y) + (i) * mapWallSize, mapWallSize));
            //Up wall
            sceneObjects.add(new Wall(WallType.Iron, (MAP_FIRST_X) + (i) * mapWallSize,
                    MAP_FIRST_Y - mapWallSize, mapWallSize));
            //Button
            sceneObjects.add(new Wall(WallType.Iron, (MAP_FIRST_X) + (i) * mapWallSize,
                    MAP_FIRST_Y + mapHeight, mapWallSize));
        }
    }

    public static String[][] readMapFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int numRows = 0;
            int numCols = 0;
            while ((line = reader.readLine()) != null) {
                numRows++;
                numCols = Math.max(numCols, line.length());
            }
            String[][] array = new String[numRows][numCols];
            reader.close();

            BufferedReader read = new BufferedReader(new FileReader(fileName));
            int row = 0;
            while ((line = read.readLine()) != null) {
                for (int col = 0; col < line.length(); col++) {
                    array[row][col] = String.valueOf(line.charAt(col));
                }
                row++;
            }
            read.close();
            return array;
        } catch (IOException e) {
            return readMapFile(DEFAULT_MAP_ONE_PLAYER);
        }
    }
}
