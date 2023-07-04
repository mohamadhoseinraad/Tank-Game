package ir.ac.kntu.services;

import ir.ac.kntu.GlobalConstance;

import java.util.Random;

import static ir.ac.kntu.GlobalConstance.mapSize;

public class RandGenerate {

    private final static RandGenerate INSTANCE = new RandGenerate();

    private final Random random;

    private RandGenerate() {
        random = new Random();
    }

    public int getRanBetween(int from, int to) {
        int result;
        do {
            result = random.nextInt(to);
        } while (!(result >= from && result < to));
        return result;
    }

    public int getRanEnemyLoc() {
        int result;
        result = random.nextInt(5);
        if (result == 1) {
            return 0;
        } else if (result == 2) {
            return mapSize / 4;
        } else if (result == 3) {
            return mapSize / 2;
        } else {
            return mapSize - 1;
        }
    }

    public static RandGenerate getINSTANCE() {
        return INSTANCE;
    }
}
