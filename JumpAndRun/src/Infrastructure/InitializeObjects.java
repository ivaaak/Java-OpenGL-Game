package Infrastructure;

import Entities.BombEntity;
import Entities.Entity;
import Entities.MoneyEntity;
import Sprites.MySprite;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static Startup.JumpAndRun.BombCollection;
import static Startup.JumpAndRun.MoneyCollection;

public class InitializeObjects {
// Init the money and bombs
    public static void initGameObjects() throws IOException
    {
        int moneyObjectX, moneyObjectY, bombObjectX, bombObjectY;

        Texture money = TextureLoader
            .getTexture("PNG", ResourceLoader
                .getResourceAsStream("res/money.png"));

        BombCollection = new ArrayList<Entity>();
        MoneyCollection = new ArrayList<Entity>();

        Random randomInt1 = new Random();

        for (int m = 0; m < Constants.GameConstants.MAX_MONEYSTACKS_COUNT; m++) {
            moneyObjectX = randomInt1.nextInt(Constants.GameConstants.SCREEN_SIZE_WIDTH - money.getImageWidth());
            moneyObjectY = randomInt1.nextInt( 1, 500 );
            MoneyEntity objectEntity = new MoneyEntity(new MySprite(money), moneyObjectX, moneyObjectY);
            MoneyCollection.add(objectEntity);
        }

        Texture bomb = TextureLoader
            .getTexture("PNG", ResourceLoader
                .getResourceAsStream("res/bomb.png"));
        Random randomInt2 = new Random();

        for (int m = 0; m < Constants.GameConstants.MAX_BOMBS_COUNT; m++) {
            bombObjectX = randomInt2.nextInt(Constants.GameConstants.SCREEN_SIZE_WIDTH - bomb.getImageWidth());
            bombObjectY = randomInt2.nextInt( 0, 500 );
            BombEntity objectEntity = new BombEntity(new MySprite(bomb), bombObjectX, -bombObjectY);
            BombCollection.add(objectEntity);
        }
    }
}
