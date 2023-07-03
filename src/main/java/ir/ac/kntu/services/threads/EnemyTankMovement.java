package ir.ac.kntu.services.threads;

import ir.ac.kntu.services.GameData;
import ir.ac.kntu.services.RandGenerate;
import ir.ac.kntu.models.gameObjects.Direction;
import ir.ac.kntu.models.gameObjects.tank.Tank;

import java.util.ArrayList;

public class EnemyTankMovement extends Thread {


    public EnemyTankMovement() {
    }

    @Override
    public void run() {
        while (true) {
            try {
                sleep(1000); // wait for 1 second
                if (!GameData.getInstance().isEnemyFreezing()) {
                    moveEnemyTanks();
                } else {
                    sleep(5000);
                    GameData.getInstance().setEnemyFreezing(false);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void moveEnemyTanks() {
        ArrayList<Tank> enemyTanks = new ArrayList<>(GameData.getInstance().getEnemyTank());
        for (Tank enemyTank : enemyTanks) {
            synchronized (enemyTank) {
                Direction[] directions = Direction.values();
                Direction direction = Direction.Up;
                if (GameData.getInstance().getPlayersTank().size() != 0) {
                    direction = findDirection(GameData.getInstance().getPlayersTank().get(0), enemyTank);
                }
                if (!enemyTank.move(enemyTank.getScale() / 5, direction)) {
                    direction = directions[RandGenerate.getINSTANCE().getRanBetween(0, directions.length)];
                    enemyTank.move(enemyTank.getScale() / 5, direction);
                }
                enemyTank.fire();

            }
        }

    }

    private Direction findDirection(Tank enemy, Tank self) {
        if (Math.abs(enemy.getX() - self.getX()) > Math.abs(enemy.getY() - self.getY())) {
            if (enemy.getX() > self.getX()) {
                return Direction.Right;
            }
            if (enemy.getX() < self.getX()) {
                return Direction.Left;
            }
        } else {
            if (enemy.getY() > self.getY()) {
                return Direction.Down;
            }
            if (enemy.getY() < self.getY()) {
                return Direction.Up;
            }
        }
        return Direction.Right;
    }
}