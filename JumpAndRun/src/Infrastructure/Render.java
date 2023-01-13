package Infrastructure;

import Entities.Entity;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import static Startup.JumpAndRun.*;

public class Render
{
// Renders the objects
    public static void drawObjects()
    {
        for (Entity entity : MoneyCollection) {
            if (entity.isVisible()) { entity.draw(); }
        }

        for (Entity entity : BombCollection) {
            if (entity.isVisible()) { entity.draw(); }
        }
        //TODO add baddies
    }


// Render the current frame
    public static void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
        Color.white.bind();

        InitializeBackground.drawBackground();

        if(CurrentLives == 0) {
            InitializeEndScreen.drawEndscreen();
            return;
        }

        if(FlipAvatar) {
            HeroEntityRight.draw();
        } else {
            HeroEntityLeft.draw();
        }

        Render.drawObjects();
        InitializeHUD.drawHUD();
    }
}
