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
}
