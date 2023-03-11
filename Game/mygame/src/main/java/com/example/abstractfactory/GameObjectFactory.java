package com.example.abstractfactory;
import com.example.characters.BonusRewards;
import com.example.characters.MainCharacter;
import com.example.characters.MovingEnemy;
import com.example.characters.StaticEnemy;
import com.example.characters.StaticRewards;

public interface GameObjectFactory {
    public MainCharacter createMainCharacter();
    public MovingEnemy createMovingEnemy();
    public StaticEnemy createStaticEnemy();
    public StaticRewards createStaticRewards();
    public BonusRewards createBonusRewards();
}