package com.example.characters;

import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.example.game.CollisionDetector;
import com.example.game.GameEngine;

public class StaticEnemy extends Character {
    private int damageAmount;
    private GameEngine gameBarrier;
    private Random random;
    private boolean visible;
    public StaticEnemy(int x, int y, int damage, GameEngine gameEngine) {
        super(x, y, "");
        this.random = new Random();
        this.gameBarrier = gameEngine;
        getStaticEnemySprite();
    }

    // getters and setters for the visible field
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getDamageAmount() {
        return damageAmount;
    }

    public void setDamageAmount(int amount) {
        damageAmount = amount;
    }

    public void update() {
        //Still thinking if I should make the Static Rewards Fixed or Randomly Spawned?????
        visible = true;
        if(gameBarrier.collisionDetector.checkCells(this)==true){
            x = random.nextInt(20);
            y = random.nextInt(20);
            x = x * gameBarrier.cellSize;
            y = y * gameBarrier.cellSize;
            System.out.println("X: " + x + " Y: " + y);
            System.out.println("IT RELOCATE FOR STATIC");
        }
        int amount = random.nextInt(200);
        setDamageAmount(amount);  // Set new bonus reward amount to 100;
    }

        public void getStaticEnemySprite() {
        try{
            staticEnemy1 = ImageIO.read(getClass().getResourceAsStream("/images/book/note.png"));
            staticEnemy2 = ImageIO.read(getClass().getResourceAsStream("/images/book/textbook.png"));
        }catch(IOException e){
            System.out.println("FAIL FIL FAIL");
            e.printStackTrace();
            System.out.println("Error loading images: " + e.getMessage());
        }
    }
}
