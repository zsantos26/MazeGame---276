package com.example.abstractfactory;
import com.example.characters.BonusRewards;
import com.example.characters.MainCharacter;
import com.example.characters.MovingEnemy;
import com.example.characters.StaticEnemy;
import com.example.characters.StaticRewards;
import com.example.game.GameEngine;

public interface GameObjectFactory {
    public MainCharacter createMainCharacter(GameEngine gameEngine);
    public MovingEnemy createMovingEnemy(GameEngine gameEngine);
    public StaticEnemy createStaticEnemy(GameEngine gameEngine);
    public StaticRewards createStaticRewards(GameEngine gameEngine);
    public BonusRewards createBonusRewards(GameEngine gameEngine);
}
