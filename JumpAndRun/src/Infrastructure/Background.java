package Infrastructure;

import Sprites.LevelTile;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;

import static Startup.JumpAndRun.BackgroundTile;

public class Background
{
// Load the background image
    public static void initBackground() throws IOException
    {
        Texture texture;
        texture = TextureLoader
                .getTexture("PNG", ResourceLoader
                        .getResourceAsStream("res/background_tile.png"));

        BackgroundTile = new LevelTile(texture);
    }
}
