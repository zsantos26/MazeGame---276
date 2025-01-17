package com.example.characters;

import java.io.IOException;
import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import com.example.game.GameEngine;

public class StaticRewards extends Character {
    private int rewardAmount;
    private Random random;
    private GameEngine gameBarrier;
    private boolean isCollected;

    public StaticRewards(int rewardAmount, int x, int y, GameEngine gameEngine) {
        super(x, y, "");
        this.isCollected = false;
        this.random = new Random();
        this.gameBarrier = gameEngine;
        getStaticRewardsSprite();
    }

    public boolean checkCollision() {
        return gameBarrier.collisionDetector.checkCells(this);
    }

    // getters and setters for the visible field
    public boolean isCollected() {
        return isCollected;
    }

    public void setCollecter(boolean collected) {
        this.isCollected = collected;
    }

    public int getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(int amount) {
        rewardAmount = amount;
    }

    public boolean onReward(int charX, int charY) {
        return x == charX && y == charY;
    }

    public void claimReward(MainCharacter mainChar) {
        int scoreEarned = getRewardAmount();
        int dx = mainChar.getX() - x;
        int dy = mainChar.getY() - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        // check if enemy is already adjacent to the main character
        if (distance < gameBarrier.cellSize) {
            mainChar.score += scoreEarned;
            System.out.println("Score: " + mainChar.score);
            isCollected = true;
            spawning();
        }
    }

    public void update(MainCharacter mainChar) {
        int amount = 100;
        setRewardAmount(amount); // Set new bonus reward amount to 100;
        claimReward(mainChar);
        isCollected = false;

    }

    public void spawning() {
        relocate();
        while (gameBarrier.collisionDetector.checkCells(this) == true) {
            relocate();
            System.out.println("X: " + x + " Y: " + y);
            System.out.println("IT RELOCATE FOR STATIC");
        }
    }

    private void relocate() {
        x = random.nextInt(20);
        y = random.nextInt(20);
        x = x * gameBarrier.cellSize;
        y = y * gameBarrier.cellSize;
    }

    public void getStaticRewardsSprite() {
        try {
            staticReward1 = ImageIO.read(getClass().getResourceAsStream("/images/book/note.png"));
            staticReward2 = ImageIO.read(getClass().getResourceAsStream("/images/book/textbook.png"));
        } catch (IOException e) {
            System.out.println("FAIL FIL FAIL");
            e.printStackTrace();
            System.out.println("Error loading images: " + e.getMessage());
        }
    }

    public void draw(Graphics2D graphics) {
        BufferedImage image = null;
        int spriteGenerate = random.nextInt(1);
        if (spriteGenerate == 0) {
            image = staticReward1;
        } else {
            image = staticReward2;
        }
        graphics.drawImage(image, x, y, gameBarrier.cellSize, gameBarrier.cellSize, null);
    }
}
