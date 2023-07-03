package ir.ac.kntu.services;

import ir.ac.kntu.models.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class PlayerService {
    private ArrayList<Player> players;

    private static final PlayerService INSTANCE = new PlayerService();

    private PlayerService() {
        players = PlayerDAO.readDB();
        if (players == null) {
            players = new ArrayList<>();
        }
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
        update();
    }

    public Player findPlayer(String username, String password) {
        for (Player player : players) {
            if (player.login(username, password)) {
                return player;
            }
        }
        return null;
    }

    public boolean login(String username, String password) {
        if (findPlayer(username, password) != null) {
            GameData.getInstance().setCurrentPlayer(findPlayer(username, password));
            return true;
        }
        return false;
    }

    public ArrayList<Player> getTopPlayers() {
        ArrayList<Player> result = new ArrayList<>();
        ArrayList<Player> cpy = new ArrayList<>(players);
        Collections.sort(cpy, Collections.reverseOrder());
        int i = 1;
        for (Player player : cpy) {
            result.add(player);
            if (i == 10) {
                break;
            }
            i++;
        }
        return result;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
