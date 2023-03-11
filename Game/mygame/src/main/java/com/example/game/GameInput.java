package com.example.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameInput implements KeyListener{
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public void start() {
        //EMPTY STATE
    }
    /*
    This method is called when the player presses a key to move the character
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int pressed = e.getKeyCode();
        switch (pressed) {
            case KeyEvent.VK_UP:
                upPressed = true;
                break;
            case KeyEvent.VK_DOWN:
                downPressed = true;
                break;
            case KeyEvent.VK_LEFT:
                leftPressed = true;
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = true;
                break;
        }
    }

    /*
    This method is called when the player releases a key to stop moving the character
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int pressed = e.getKeyCode();
        switch (pressed) {
            case KeyEvent.VK_UP:
                upPressed = false;
                break;
            case KeyEvent.VK_DOWN:
                downPressed = false;
                break;
            case KeyEvent.VK_LEFT:
                leftPressed = false;
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = false;
                break;
        }
    }

    // Don't need this method since we're not typing anything
    @Override
    public void keyTyped(KeyEvent e) {
        // Don't need this method since we're not typing anything
    }
}