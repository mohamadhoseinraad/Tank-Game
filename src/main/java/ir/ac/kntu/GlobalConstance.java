package ir.ac.kntu;

public class GlobalConstance {
    public final static int WINDOWS_HEIGHT = 600;

    public final static int WINDOWS_WIDTH = 800;

    public final static int MAP_FIRST_X = 50;

    public final static int MAP_FIRST_Y = 25;

    public static int mapHeight = 500;

    public static int mapSize = 20;

    public static double scale = mapHeight / mapSize;

    public final static int PLAYER_FIRST_X = 500;

    public final static int PLAYER_FIRST_Y = 500;

    public final static int NORMAL_TANK_HEALTH = 1;

    public final static int STRONG_TANK_HEALTH = 2;

    public final static int PLAYER_TANK_HEALTH = 2;

    public static void updateSize() {
        if (mapHeight % mapSize != 0) {
            mapHeight = ((mapHeight / mapSize) + 1) * mapSize;
        }
        scale = mapHeight / mapSize;
    }
}
