package ir.ac.kntu;

import ir.ac.kntu.models.Player;

import java.util.ArrayList;

public class PlayerService {
    private ArrayList<Player> players;

    private static final PlayerService INSTANCE = new PlayerService();

    private PlayerService() {
        players = PlayerDAO.readDB();
    }

    public static PlayerService getINSTANCE() {
        return INSTANCE;
    }

    public void update() {
        PlayerDAO.writeDB(players);
    }

    public void singUp(String username, String password) {
        if (findPlayer(username, password) == null) {
            players.add(new Player(username, password));
        }
    }

    private Player findPlayer(String username, String password) {
        for (Player player : players) {
            if (player.login(username, password)) {
                return player;
            }
        }
        return null;
    }

    public void login(String username, String password) {
        if (findPlayer(username, password) != null) {
            GameData.getInstance().setCurrentPlayer(findPlayer(username, password));
        }
    }
}
