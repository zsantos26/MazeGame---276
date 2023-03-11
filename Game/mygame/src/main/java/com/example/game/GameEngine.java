package com.example.game;

import javax.swing.JPanel;

import com.example.abstractfactory.GameObjectFactory;
import com.example.characters.MainCharacter;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GameEngine extends JPanel implements Runnable{
  //Set the Screen Size
  final int originalCellSize = 16; //20x20 Cells
  final int scale = 3;
//Screen Dimensions
  public final int cellSize = originalCellSize * scale;
  public final int maxScreenCol = 20;
  public final int maxScreenRow = 20;
  public final int screenWidth = cellSize * maxScreenCol;
  public final int screenHeight = cellSize * maxScreenRow;

  //Abstract Factory
  private GameObjectFactory gameObjectFactory;
  private MainCharacter mainChar;


  public String direction;

  //Keyboard Input
  Game keyBoard = new Game();
  Thread gameThread;

  GameWorld gameWorld = new GameWorld(this);

  public GameEngine(GameObjectFactory factoryMethod) {
    //Set the Screen Size
    screenSetUp();
    this.gameObjectFactory = factoryMethod;
    this.mainChar = gameObjectFactory.createMainCharacter();
  }

  public void screenSetUp(){
    setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.black);
    this.setDoubleBuffered(true);
    this.addKeyListener(keyBoard);
    setFocusable(true);
    requestFocus();
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    //Calling Main Character
    gameWorld.draw(g2d, cellSize);
    mainChar.draw(g2d, cellSize);
    g2d.dispose();
  }

  public void startGameThread(){
    gameThread = new Thread(this);
    gameThread.start();
  }

  @Override
  public void run() {
    double timePerTick = 1000000000 / 60;
    double nextDraw = System.nanoTime() + timePerTick;

    while (gameThread != null) {
      long currTime = System.nanoTime();
      System.out.println(currTime);
      System.out.println("Game Thread is running");
      update();
      repaint();
      try{
        double remainingTime = nextDraw - System.nanoTime();
        remainingTime = remainingTime/1000000;
        if(remainingTime < 0){
          remainingTime = 0;
        }
        Thread.sleep((long) remainingTime); //casting
        nextDraw += timePerTick;
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void update() {
    mainChar.update(keyBoard);
  }
}
