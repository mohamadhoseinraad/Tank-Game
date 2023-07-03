package ir.ac.kntu;

import ir.ac.kntu.services.PlayerService;

import java.util.Collections;

public class Main {
    public static void main(String[] args) {


        PlayerService.getINSTANCE().getTopPlayers().forEach((player) -> {
            System.out.println(player.getUsername() + "-" + player.getHighScore());
        });



    }
}
