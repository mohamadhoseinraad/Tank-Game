package ir.ac.kntu;

import ir.ac.kntu.models.Level;
import ir.ac.kntu.models.Player;
import ir.ac.kntu.services.PlayerService;

import java.util.Collections;

public class Main {
    public static void main(String[] args) {


        PlayerService.getINSTANCE().findPlayer("sms", "sms").setLastLevel(Level.Level_10);

        PlayerService.getINSTANCE().update();


    }
}
