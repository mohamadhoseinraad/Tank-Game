package ir.ac.kntu;

public class GlobalConstance {
    public final static int WINDOWS_HEIGHT = 600;

    public final static int WINDOWS_WIDTH = 800;

    public final static int MAP_FIRST_X = 50;

    public final static int MAP_FIRST_Y = 25;

    public static int mapHeight = 500;

    public static int mapSize = 20;

    public static double scale = mapHeight / mapSize;

    public final static int NORMAL_TANK_HEALTH = 1;

    public final static int STRONG_TANK_HEALTH = 2;

    public final static int PLAYER_TANK_HEALTH = 3;

    public static int playerShotDamage = 1;

    public static final String PLAYERS_DB_FILE = "src/main/resources/PlayerDB.txt";

    public static final String DEFAULT_MAP_ONE_PLAYER = "src/main/resources/map.txt";

    public static final String DEFAULT_MAP_TWO_PLAYER = "src/main/resources/map2.txt";

    public static String customMap = "src/main/resources/";

    public static final String BUTTON_STYLE = "-fx-background-color: #FFA07A;" +
            " -fx-text-fill: black; -fx-font-size: 16px;" +
            " -fx-font-weight: bold; -fx-padding: 10px 20px;" +
            " -fx-border-color: black; -fx-border-width: 2px;" +
            " -fx-background-radius: 20px;";

    public static final String BUTTON_STYLE_2 = "-fx-background-color: #20B2AA;" +
            " -fx-text-fill: black; -fx-font-size: 16px;" +
            " -fx-font-weight: bold; -fx-padding: 10px 20px;" +
            " -fx-border-color: black; -fx-border-width: 2px;" +
            " -fx-background-radius: 20px;";

    public static void updateSize() {
        if (mapHeight % mapSize != 0) {
            mapHeight = ((mapHeight / mapSize) + 1) * mapSize;
        }
        scale = mapHeight / mapSize;
    }
}
