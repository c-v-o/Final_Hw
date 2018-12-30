import enter.Characters.Character;
import enter.Factions.AllCharacters;
import enter.Map.Positions;

import org.junit.Assert;
import org.junit.Test;

public class AppTest {



    @Test
    public void simpleTest(){
        Positions positions = new Positions();
        Assert.assertTrue(positions.getMap()[0][0].isEmpty());
        Assert.assertTrue(positions.getMap()[14][14].getIndexx() == 14 && positions.getMap()[14][14].getIndexy() == 14);
        System.out.println("ok!");
    }
}
