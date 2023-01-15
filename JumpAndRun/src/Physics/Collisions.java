package Physics;

import Entities.BombEntity;
import Entities.Entity;
import Entities.MoneyEntity;

import java.util.Random;

import static Startup.JumpAndRun.*;

public class Collisions
{
// Checks for collision of money and bombs with the avatar
    public static void checkForCollision()
    {
        for (Entity thisMoney : MoneyCollection) {
            if (FlipAvatar) {
                if (HeroEntityRight.collidesWith(thisMoney)) {
                    HeroEntityRight.collidedWith(thisMoney);
                }
            } else {
                if (HeroEntityLeft.collidesWith(thisMoney)) {
                    HeroEntityLeft.collidedWith(thisMoney);
                }
            }
        }

        for (Entity thisBomb : BombCollection) {
            if (FlipAvatar) {
                if (HeroEntityRight.collidesWith(thisBomb)) {
                    HeroEntityRight.collidedWith(thisBomb);
                }
            } else {
                if (HeroEntityLeft.collidesWith(thisBomb)) {
                    HeroEntityLeft.collidedWith(thisBomb);
                }
            }
        }
    }



    // Handles the collision of the avatar with bombs or money
    public static void notifyObjectCollision(Entity object)
    {
        Random rand = new Random();
        if(object instanceof MoneyEntity moneyEntity) {
            int newX = rand.nextInt(
                    Constants.GameConstants.SCREEN_SIZE_WIDTH - moneyEntity.getWidth());
            int newY =  rand.nextInt(
                    -moneyEntity.getHeight() - (-Constants.GameConstants.SPAWN_SPACE_HEIGHT - moneyEntity.getHeight()))
                    + (-Constants.GameConstants.SPAWN_SPACE_HEIGHT - moneyEntity.getHeight());
            moneyEntity.setX(newX);
            moneyEntity.setY(newY);
            Score += 100; //TODO add score multiplier?
        } else if (object instanceof BombEntity bombEntity) {
            int newX = rand.nextInt( Constants.GameConstants.SCREEN_SIZE_WIDTH - bombEntity.getWidth());
            int newY =  rand.nextInt( 1, 500 );
            bombEntity.setX(newX);
            bombEntity.setY(-newY);
            CurrentLives--;
        }
    }
}
