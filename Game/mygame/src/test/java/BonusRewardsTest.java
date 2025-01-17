import com.example.abstractfactory.AbstractFactory;
import com.example.abstractfactory.GameObjectFactory;
import com.example.characters.MainCharacter;
import com.example.characters.BonusRewards;
import com.example.game.GameEngine;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
public class BonusRewardsTest {
    private BonusRewards bonusRewards;
    private MainCharacter mainChar;

    public BonusRewardsTest(){
    }
    @Before
    public void setUp() {
        GameObjectFactory gameObjectFactory = new AbstractFactory();
        GameEngine gameEngine = new GameEngine(gameObjectFactory);
        gameEngine.startGameThread();
        this.mainChar = gameEngine.mainChar;
        bonusRewards = new BonusRewards(50, 50, 30, 30, gameEngine);
    }

    @Test
    public void testSetRewardAmount() {
        bonusRewards.setRewardAmount(200);
        Assert.assertEquals(200, this.bonusRewards.getRewardAmount());
    }

    @Test
    public void testSpawning() {
        bonusRewards.spawning();
        assertFalse(bonusRewards.checkCollision()); // should not collide with any barriers
    }

    @Test
    public void testGetBonusRewardsSprite() {
        assertNotNull(bonusRewards.bonusReward);

    }
    @Test
    public void testCheckCollision() {
        bonusRewards.setX(50); // move rewards to (50, 0)
        bonusRewards.setY(50); // move rewards to (50, 50)
        mainChar.setX(50); // move main character to (50, 0)
        mainChar.setY(50); // move main character to (50, 50)
        assertTrue(bonusRewards.checkCollision()); // should collide with the main character
    }



    @Test
    public void testClaimReward() {

        bonusRewards.setX(50); // move rewards to (50, 0)
        bonusRewards.setY(50); // move rewards to (50, 50)
        mainChar.setX(50); // move main character to (50, 0)
        mainChar.setY(50); // move main character to (50, 50)
        mainChar.score = 0;
        Assert.assertEquals(0, mainChar.score);
        bonusRewards.setRewardAmount(200);
        bonusRewards.update(mainChar);
        assertTrue(mainChar.score == 200);
    }

    @Test
    public void reAllocateClaimReward() {

        bonusRewards.setX(50); // move rewards to (50, 0)
        bonusRewards.setY(50); // move rewards to (50, 50)
        mainChar.setX(50); // move main character to (50, 0)
        mainChar.setY(50); // move main character to (50, 50)
        mainChar.score = 0;
        Assert.assertEquals(0, mainChar.score);
        bonusRewards.setRewardAmount(200);
        bonusRewards.update(mainChar);
        bonusRewards.spawning();
        assertTrue(mainChar.score == 200);
    }

    @Test
    public void testDraw() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/images/book/specialRewards.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Graphics2D graphics = image.createGraphics();
        this.bonusRewards.draw(graphics);
        Assert.assertNotNull(image);
    }













}
