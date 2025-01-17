package com.example.characters;

import com.example.game.GameEngine;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class MovingEnemy extends Character {
    private int damageAmount;
    GameEngine gameBarrier;
    Random random = new Random();

    public MovingEnemy(int x, int y, int damage, GameEngine gameEngine) {
        super(x, y, "");
        this.gameBarrier = gameEngine; // This is the gameBarrier object
        getMovingEnemySprite();
    }

    public boolean checkCollision() {
        return gameBarrier.collisionDetector.checkCells(this);
    }

    public int getDamageAmount() {
        return damageAmount;
    }

    public void setDamageAmount(int amount) {
        damageAmount = amount;
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

    public String moveTowards(MainCharacter mainchar) {
        // calculate distance between the enemy and the main character
        int dx = mainchar.getX() - x;
        int dy = mainchar.getY() - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        // check if enemy is already adjacent to the main character
        if (distance <= gameBarrier.cellSize) {
            gameBarrier.gameOver();
        }

        // determine distance of enemy from main character if they were to move in each
        // direction
        double distanceRight = Math.sqrt((dx - 1) * (dx - 1) + dy * dy);
        double distanceLeft = Math.sqrt((dx + 1) * (dx + 1) + dy * dy);
        double distanceDown = Math.sqrt((dx * dx) + (dy - 1) * (dy - 1));
        double distanceUp = Math.sqrt((dx * dx) + (dy + 1) * (dy + 1));

        // find the direction to move that makes them closest to the current position of
        // the main character
        double minDistance = Math.min(Math.min(distanceRight, distanceLeft), Math.min(distanceDown, distanceUp));

        // return direction that makes moving enemy closest to the current position of
        // the main character
        if (minDistance == distanceRight) {
            return "right";
        } else if (minDistance == distanceLeft) {
            return "left";
        } else if (minDistance == distanceDown) {
            return "down";
        } else {
            return "up";
        }

    }

    public String moveTowardsAlt(MainCharacter mainchar) {
        // calculate distance between the enemy and the main character
        int dx = mainchar.getX() - x;
        int dy = mainchar.getY() - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        // check if enemy is already adjacent to the main character
        if (distance <= gameBarrier.cellSize) {
            // enemy has caught the main character, do something here
            return null;
        }

        // determine distance of enemy from main character if they were to move in each
        // direction
        double distanceRight = Math.sqrt((dx - 1) * (dx - 1) + dy * dy);
        double distanceLeft = Math.sqrt((dx + 1) * (dx + 1) + dy * dy);
        double distanceDown = Math.sqrt((dx * dx) + (dy - 1) * (dy - 1));
        double distanceUp = Math.sqrt((dx * dx) + (dy + 1) * (dy + 1));

        // find the direction to move that makes them closest to the current position of
        // the main character. this direction was already tried in moveTowards()
        double minDistance = Math.min(Math.min(distanceRight, distanceLeft), Math.min(distanceDown, distanceUp));

        // check if first direction determined was either moving in x-axis, or y-axis.
        // return new direction if it moves enemy closer to main character
        if (minDistance == distanceRight || minDistance == distanceLeft) {
            double minDistanceAlt = Math.min(distanceUp, distanceDown); // determine direction on y-axis that makes them
                                                                        // closest to the current position of the main
                                                                        // character.
            // Check if new direction move would decrease distance to main character.
            if (minDistanceAlt < distance) {
                // return new determined direction
                if (minDistanceAlt == distanceUp) {
                    return "up";
                } else {
                    return "down";
                }
            } else {
                // if new direction does not decrease distance from main character, return
                // previously determined direction (from moveTowards())
                if (minDistance == distanceRight) {
                    return "right";
                } else {
                    return "left";
                }
            }
        } else {
            double minDistanceAlt = Math.min(distanceLeft, distanceRight); // determine direction on x-axis that makes
                                                                           // them closest to the current position of
                                                                           // the main character.
            // Check if new direction move would decrease distance to main character.
            if (minDistanceAlt < distance) {
                // return new determined direction
                if (minDistanceAlt == distanceLeft) {
                    return "left";
                } else {
                    return "right";
                }
            } else {
                // if new direction does not decrease distance from main character, return
                // previously determined direction (from moveTowards())
                if (minDistance == distanceUp) {
                    return "up";
                } else {
                    return "down";
                }
            }
        }
    }

    public void update(double elapsed, MainCharacter mainChar) {
        int distance = gameBarrier.cellSize;
        direction = moveTowards(mainChar);
        collisionOn = false;
        gameBarrier.collisionDetector.checkCells(this);

        // Check if determined move direction leads to collision.
        if (collisionOn == false) {
            switch (direction) {
                case "up":
                    y -= distance;
                    break;
                case "down":
                    y += distance;
                    break;
                case "left":
                    x -= distance;
                    break;
                case "right":
                    x += distance;
                    break;
                default:
                    break;
            }
        }
        // If determined direction leads to collision, use moveTowardsAlt() to set try
        // to find alternate direction that makes them closest to the current position
        // of the main character
        else {
            direction = moveTowardsAlt(mainChar);
            collisionOn = false;
            gameBarrier.collisionDetector.checkCells(this);
            if (collisionOn == false) {
                switch (direction) {
                    case "up":
                        y -= distance;
                        break;
                    case "down":
                        y += distance;
                        break;
                    case "left":
                        x -= distance;
                        break;
                    case "right":
                        x += distance;
                        break;
                    default:
                        break;
                }
            }
        }

        spriteCounter++;
        if (spriteCounter > 0) {
            if (spriteMovement == 1) {
                spriteMovement = 2;
            } else if (spriteMovement == 2) {
                spriteMovement = 1;
            }
            spriteCounter = 0;
        }
    }

    /*
     * This method is called when the character is created to load the character's
     * sprite
     */
    public void getMovingEnemySprite() {
        try {
            // if it is too hard to make two enemies we can go for one of it
            // For AQ
            if (gameBarrier.gameWorld.map == 1) {
                up1 = ImageIO.read(getClass().getResourceAsStream("/images/Raccoon/raccooon back-1.png.png"));
                up2 = ImageIO.read(getClass().getResourceAsStream("/images/Raccoon/raccooon back-1.png.png"));
                down1 = ImageIO.read(getClass().getResourceAsStream("/images/Raccoon/raccoon front-1.png.png"));
                down2 = ImageIO.read(getClass().getResourceAsStream("/images/Raccoon/raccoon front-1.png.png"));
                left1 = ImageIO.read(getClass().getResourceAsStream("/images/Raccoon/raccoon_left-1.png.png"));
                left2 = ImageIO.read(getClass().getResourceAsStream("/images/Raccoon/raccoon_left-1.png.png"));
                right1 = ImageIO.read(getClass().getResourceAsStream("/images/Raccoon/raccoon_right-1.png.png"));
                right2 = ImageIO.read(getClass().getResourceAsStream("/images/Raccoon/raccoon_right-1.png.png"));
            } else {
                // For ClassRoom
                up1 = ImageIO
                        .read(getClass().getResourceAsStream("/images/professor/prof_back_right-1.png.png"));
                up2 = ImageIO.read(getClass().getResourceAsStream("/images/professor/prof_back_left-1.png.png"));
                down1 = ImageIO.read(getClass().getResourceAsStream("/images/professor/prof-front-left-1.png.png"));
                down2 = ImageIO
                        .read(getClass().getResourceAsStream("/images/professor/prof-front-right-1.png.png"));
                left1 = ImageIO
                        .read(getClass().getResourceAsStream("/images/professor/prof_left_walk-1.png.png"));
                left2 = ImageIO.read(getClass().getResourceAsStream("/images/professor/prof_left_stand-1.png.png"));
                right1 = ImageIO
                        .read(getClass().getResourceAsStream("/images/professor/prof_right_walk-1.png.png"));
                right2 = ImageIO
                        .read(getClass().getResourceAsStream("/images/professor/prof_right_stand-1.png.png"));
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading images: " + e.getMessage());
        }
    }

    /*
     * This method is called every frame to update the enemy's state
     */
    public void draw(Graphics2D graphics) {
        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (spriteMovement == 1) {
                    image = up1;
                } else if (spriteMovement == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteMovement == 1) {
                    image = down1;
                } else if (spriteMovement == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteMovement == 1) {
                    image = left1;
                } else if (spriteMovement == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteMovement == 1) {
                    image = right1;
                } else if (spriteMovement == 2) {
                    image = right2;
                }
                break;
            default:
                image = down1;
                break;
        }
        graphics.drawImage(image, x, y, gameBarrier.cellSize, gameBarrier.cellSize, null);
    }
}
