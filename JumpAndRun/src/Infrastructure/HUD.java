package Infrastructure;

import Sprites.LevelTile;
import Startup.JumpAndRun;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import static Startup.JumpAndRun.CurrentLives;
import static Startup.JumpAndRun.Lives;

public class HUD
{
// Renders the score and lifes in the heads up display
    public static void drawHUD()
    {
        JumpAndRun.ScoreFont.drawString(10, 0, String.format("Score: %d", JumpAndRun.Score), Color.white);
        Color.white.bind();
        JumpAndRun.Lives[JumpAndRun.CurrentLives]
            .draw(Constants.GameConstants.SCREEN_SIZE_WIDTH - JumpAndRun.Lives[JumpAndRun.CurrentLives].getWidth(), 0);
    }

// Visualize of the lives of the player
    public static void initLives() throws Exception
    {
        //Loads the heart sprite in the array lives[] of type LevelTile
        for (int i = 0; i <= CurrentLives; i++)
        {
            Texture texture = TextureLoader
                    .getTexture("PNG", ResourceLoader
                            .getResourceAsStream("res/heart" + i + ".png"));
            Lives[i] = new LevelTile(texture);
        }
    }
}
