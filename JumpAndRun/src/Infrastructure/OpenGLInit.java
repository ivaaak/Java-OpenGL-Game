package Infrastructure;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

import static Startup.JumpAndRun.EndScreenFont;
import static Startup.JumpAndRun.ScoreFont;

public class OpenGLInit
{
    // Initialize the game display window
    public static void initGL(int width, int height) throws Exception
    {
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.setTitle(Constants.GameConstants.GAME_TITLE);
            Display.setFullscreen(false);
            Display.create();
            Display.setVSyncEnabled(true);

        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        // OpenGL Magic
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glViewport(0, 0, width, height);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        Font scoreFontDetails = new Font("Times New Roman" , Font.BOLD, 32);
        ScoreFont = new TrueTypeFont(scoreFontDetails, true);

        Font endScreenFontDetails = new Font("Times New Roman" , Font.BOLD, 64);
        EndScreenFont = new TrueTypeFont(endScreenFontDetails, true);
    }
}
