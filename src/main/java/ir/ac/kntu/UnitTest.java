package ir.ac.kntu;

import ir.ac.kntu.models.Level;
import ir.ac.kntu.models.Player;
import ir.ac.kntu.services.GameData;
import ir.ac.kntu.services.PlayerService;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class UnitTest {

    @Test
    public void checkPlayerServiceSingUp() {

        Player player = new Player("Ali", "123");
        PlayerService.getINSTANCE().singUp("Ali", "123");
        Player player1 = PlayerService.getINSTANCE().findPlayer("Ali", "123");
        assertEquals(player1, player);
    }

    @Test
    public void checkPlayerServiceLogIn() {
        PlayerService.getINSTANCE().login("Ali", "123");
        Player player1 = PlayerService.getINSTANCE().findPlayer("Ali", "123");
        Player player = GameData.getInstance().getCurrentPlayer();
        assertEquals(player1, player);
    }

    @Test
    public void checkApplyLevelGame() {
        GameData.getInstance().resetAll(Level.Level_2);
        int enemyNumber = GameData.getInstance().getEnemyNumber();
        assertEquals(14, enemyNumber);
    }


}
