package Physics;

import Entities.Entity;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.util.Random;

import static Startup.JumpAndRun.*;

public class Logic
{
// Do calculations, handle input
    public static void logic()
    {
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) IsFinished = true;

        if(CurrentLives > 0 ) {
            logicHero();
            logicObjects();
            Collisions.checkForCollision();
        } else {
            if (Keyboard.isKeyDown(Keyboard.KEY_N)) {
                CurrentLives = 3;
                Score = 0;
                NewGame = true;
                logicObjects();
                logicHero();
                Collisions.checkForCollision();
            }
            NewGame = false;
        }
    }

    // Calculate the movement and position of the game objects: money and bombs
    public static void logicObjects()
    {
        for (Entity moneyEntity : MoneyCollection)
        {
            moneyEntity.setY(moneyEntity.getY() + Constants.GameConstants.ITEM_MOVEMENT_SPEED);
            //when the money reaches the ground or when a new game is started the x and y of the money are reset to random values,
            //this way it is guaranteed that the number of money that can be on the game window at any given time is the number of the elements in the money array
            if (moneyEntity.getY() + moneyEntity.getHeight() > Display.getDisplayMode().getHeight() || NewGame) {
                Random rand = new Random();
                int newX = rand.nextInt(Constants.GameConstants.SCREEN_SIZE_WIDTH - moneyEntity.getWidth());
                int newY = rand.nextInt(1, 500);
                moneyEntity.setX(newX);
                moneyEntity.setY(newY);
            }
        }
        // make every bomb in the array fall
        for (Entity bombEntity : BombCollection)
        {
            bombEntity.setY(bombEntity.getY() + 2 * Constants.GameConstants.ITEM_MOVEMENT_SPEED);
            //when the bomb reaches the ground or when a new game is started the x and y of the bomb are reset to random values,
            //this way it is guaranteed that the number of bombs that can be on the game window at any given time is the number of the elements in the bombs array
            if (bombEntity.getY() + bombEntity.getHeight() > Display.getDisplayMode().getHeight() || NewGame) {
                Random rand = new Random();
                int newX = rand.nextInt(Constants.GameConstants.SCREEN_SIZE_WIDTH - bombEntity.getWidth());
                int newY = rand.nextInt(1, 500);
                bombEntity.setX(newX);
                bombEntity.setY(-newY);
            }
        }
    }

    // Handles the User input responsible for the movement of the avatar
    public static void logicHero()
    {
        // TODO move speed calculation to variable?
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            if(!FlipAvatar) {
                HeroEntityRight.setX(HeroEntityLeft.getX());
                FlipAvatar = !FlipAvatar;
            }
            if(HeroEntityRight.getX() + HeroEntityRight.getWidth() + Constants.GameConstants.HERO_MOVEMENT_SPEED < Display.getDisplayMode().getWidth()) {
                HeroEntityRight.setX(HeroEntityRight.getX() + Constants.GameConstants.HERO_MOVEMENT_SPEED);

            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            if(FlipAvatar) {
                HeroEntityLeft.setX(HeroEntityRight.getX());
                FlipAvatar = !FlipAvatar;
            }
            if(HeroEntityLeft.getX() - Constants.GameConstants.HERO_MOVEMENT_SPEED >= 0) {
                HeroEntityLeft.setX(HeroEntityLeft.getX() - Constants.GameConstants.HERO_MOVEMENT_SPEED);
            }
        }
    }
}
