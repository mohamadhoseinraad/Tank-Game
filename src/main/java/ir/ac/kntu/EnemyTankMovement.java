package ir.ac.kntu;

import ir.ac.kntu.gameObjects.Direction;
import ir.ac.kntu.gameObjects.SceneObject;
import ir.ac.kntu.gameObjects.tank.Tank;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class EnemyTankMovement extends Thread {

    private ArrayList<Tank> enemyTanks;

    public EnemyTankMovement(ArrayList<Tank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    @Override
    public void run() {
        while (true) {
            try {
                sleep(1000); // wait for 1 second
                moveEnemyTanks();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void moveEnemyTanks() {
        Iterator<Tank> iterator = enemyTanks.iterator();
        while (iterator.hasNext()) {
            Tank enemyTank = iterator.next();
            synchronized (enemyTank) {
//                Direction[] directions = Direction.values();
//                int index = new Random().nextInt(directions.length);
//                Direction direction = directions[index];
                Direction direction = Direction.Up;
                if (Game.getPlayersTank().size() != 0) {
                    direction = findDirection(Game.getPlayersTank().get(0), enemyTank);
                }


                // Call the move() method on the enemy tank with the random direction
                enemyTank.move(enemyTank.getScale() / 3, direction);
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