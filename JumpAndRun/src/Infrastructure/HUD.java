package Infrastructure;

import Entities.ObjectEntity;
import Sprites.MySprite;
import Startup.JumpAndRun;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;

import static Startup.JumpAndRun.CurrentLives;

public class HUD
{
// Renders the score and lifes in the heads up display
    public static void drawScore()
    {
        JumpAndRun.ScoreFont.drawString(10, 0, String.format("Score: %d", JumpAndRun.Score), Color.white);
    }

// Visualize of the lives of the player
    public static void drawLives() throws IOException {
        Texture texture;
        for (int i = 1; i <= CurrentLives; i++)
        {
            texture = TextureLoader
                .getTexture("PNG", ResourceLoader
                    .getResourceAsStream("res/heart.png"));

            ObjectEntity heartIcon = new ObjectEntity(new MySprite(texture), 1550 - i*50, 50);
            heartIcon.draw();
        }
    }
}
