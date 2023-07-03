package ir.ac.kntu.services;

import ir.ac.kntu.models.Player;

import java.io.*;
import java.util.ArrayList;

import static ir.ac.kntu.GlobalConstance.PLAYERS_DB_FILE;

public class PlayerDAO {
    public static void writeDB(ArrayList<Player> players) {
        File file = new File(PLAYERS_DB_FILE);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(players);
        } catch (IOException e) {
            System.out.println("Fail in write playersDB!");
        }
    }

    public static ArrayList<Player> readDB() {
        File file = new File(PLAYERS_DB_FILE);
        ArrayList<Player> players = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            players = (ArrayList<Player>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Fail in read playersDB!");
        }
        return players;
    }

}
