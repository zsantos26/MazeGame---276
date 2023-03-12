package com.example.abstractfactory;
import com.example.characters.BonusRewards;
import com.example.characters.MainCharacter;
import com.example.characters.MovingEnemy;
import com.example.characters.StaticEnemy;
import com.example.characters.StaticRewards;

public class AbstractFactory implements GameObjectFactory {
    @Override
    public MainCharacter createMainCharacter() {
        return new MainCharacter(0, 0);
    }

    @Override
    public MovingEnemy createMovingEnemy() {
        return new MovingEnemy(3,3,10);
    }

    @Override
    public StaticEnemy createStaticEnemy() {
        return new StaticEnemy(10,10,20);
    }

    @Override
    public StaticRewards createStaticRewards() {
        return new StaticRewards(20, 3, 4);
    }

    @Override
    public BonusRewards createBonusRewards() {
        return new BonusRewards(50, 5, 5, 2);
    }
}
