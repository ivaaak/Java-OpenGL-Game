package Infrastructure;

import Sprites.LevelTile;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;

import static Startup.JumpAndRun.*;

public class EndScreen
{
// Renders the end screen which is shown if the player doesn't have any lifes left
    public static void drawEndscreen() {
        Color.white.bind();
        EndScreenTiles[0].draw(
            (Constants.GameConstants.SCREEN_SIZE_WIDTH - EndScreenTiles[0].getWidth())/2,
            (Constants.GameConstants.SCREEN_SIZE_HEIGHT - EndScreenTiles[0].getHeight())/2);

        Calculations.highScoreCalculate();

        EndScreenFont.drawString(
            (Constants.GameConstants.SCREEN_SIZE_WIDTH / 2) - 50,
            (Constants.GameConstants.SCREEN_SIZE_HEIGHT / 3) + 10,
            String.format("Final Score"), Color.white);

        EndScreenFont.drawString(
            (Constants.GameConstants.SCREEN_SIZE_WIDTH / 2) - 10,
            (Constants.GameConstants.SCREEN_SIZE_HEIGHT/3) + 40,
            String.format("%d" , Score), Color.white);

        EndScreenFont.drawString(
            (Constants.GameConstants.SCREEN_SIZE_WIDTH / 2) + -50,
            (Constants.GameConstants.SCREEN_SIZE_HEIGHT/3) + 80,
            String.format("High Score"), Color.white);

        EndScreenFont.drawString(
            (Constants.GameConstants.SCREEN_SIZE_WIDTH / 2) - 10,
            (Constants.GameConstants.SCREEN_SIZE_HEIGHT/3) + 110,
            String.format("%d" , HighScore), Color.white);

        EndScreenFont.drawString(
            (Constants.GameConstants.SCREEN_SIZE_WIDTH / 3) + 30,
            (Constants.GameConstants.SCREEN_SIZE_HEIGHT/3) + 150,
            String.format("Press N for new game"), Color.white);

        //draw medal if there is a new high score
        if(Score >= HighScore) {
            Color.white.bind();
            EndScreenTiles[1].draw(70,70);
        }
    }

// Show the end screen
    public static void initEndScreen() throws IOException
    {
        //Load the frame, which shows the score in the middle of the screen
        Texture scoreFrame = TextureLoader
            .getTexture("PNG", ResourceLoader
                .getResourceAsStream("res/score_frame.png"));
        EndScreenTiles[0] = new LevelTile(scoreFrame);

        //initialize the medal (for the new high score)
        Texture medal = TextureLoader
            .getTexture("PNG",ResourceLoader
                .getResourceAsStream("res/medal.png"));
        EndScreenTiles[1] = new LevelTile(medal);
    }
}
