package ir.ac.kntu.services.threads;

import ir.ac.kntu.models.GameStatus;
import ir.ac.kntu.models.gameObjects.Flag;
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
                } else if (GameData.getInstance().getGameStatus() == GameStatus.Running) {
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

            Direction[] directions = Direction.values();
            Direction direction = Direction.Up;
            if (GameData.getInstance().getPlayersTank().size() != 0) {

                if (isPlayerNear(enemyTank)) {
                    direction = findDirection(GameData.getInstance().getPlayersTank().get(0), enemyTank);
                } else {
                    direction = findDirectionFlag(GameData.getInstance().getPlayersFlag(), enemyTank);
                }
            }
            if (!enemyTank.move(1, direction)) {
                direction = directions[RandGenerate.getINSTANCE().getRanBetween(0, directions.length)];
                enemyTank.move(1, direction);
            }
            enemyTank.fire();

        }

    }

    private Direction findDirection(Tank target, Tank self) {
        if (Math.abs(target.getX() - self.getX()) > Math.abs(target.getY() - self.getY())) {
            if (target.getX() > self.getX()) {
                return Direction.Right;
            }
            if (target.getX() < self.getX()) {
                return Direction.Left;
            }
        } else {
            if (target.getY() > self.getY()) {
                return Direction.Down;
            }
            if (target.getY() < self.getY()) {
                return Direction.Up;
            }
        }
        return Direction.Right;
    }

    private Direction findDirectionFlag(Flag target, Tank self) {
        if (Math.abs(target.getX() - self.getX()) > Math.abs(target.getY() - self.getY())) {
            if (target.getX() > self.getX()) {
                return Direction.Right;
            }
            if (target.getX() < self.getX()) {
                return Direction.Left;
            }
        } else {
            if (target.getY() > self.getY()) {
                return Direction.Down;
            }
            if (target.getY() < self.getY()) {
                return Direction.Up;
            }
        }
        return Direction.Right;
    }

    private boolean isPlayerNear(Tank self) {
        Tank target = GameData.getInstance().getPlayersTank().get(0);
        double distance1 = Math.pow(Math.abs(target.getX() - self.getX()), 2) +
                Math.pow(Math.abs(target.getY() - self.getY()), 2);
        Flag target2 = GameData.getInstance().getPlayersFlag();
        double distance2 = Math.pow(Math.abs(target2.getX() - self.getX()), 2) +
                Math.pow(Math.abs(target2.getY() - self.getY()), 2);

        return distance1 < distance2;

    }

}