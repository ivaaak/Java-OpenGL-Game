package Infrastructure;

import Sprites.LevelTile;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;
import java.io.IOException;

import static Startup.JumpAndRun.*;

public class EndScreen
{
// Renders the end screen (no lives left)
    public static void drawEndscreen() {
        EndScreenTiles[0].draw(
            (Constants.GameConstants.SCREEN_SIZE_WIDTH - EndScreenTiles[0].getWidth())/2,
            (Constants.GameConstants.SCREEN_SIZE_HEIGHT - EndScreenTiles[0].getHeight())/2);

        Calculations.highScoreCalculate();

        EndScreenFont.drawString(
            (Constants.GameConstants.SCREEN_SIZE_WIDTH / 2)-150,
            (Constants.GameConstants.SCREEN_SIZE_HEIGHT / 3) + 260,
            String.format("Final Score: %d", Score), Color.white);

        EndScreenFont.drawString(
            (Constants.GameConstants.SCREEN_SIZE_WIDTH / 2)-150,
            (Constants.GameConstants.SCREEN_SIZE_HEIGHT/3) + 330,
            String.format("High Score: %d", HighScore), Color.white);

        EndScreenFont.drawString(
            (Constants.GameConstants.SCREEN_SIZE_WIDTH / 3)+50,
            (Constants.GameConstants.SCREEN_SIZE_HEIGHT/3) + 400,
            String.format("Press N for a new game"), Color.white);

        //draw highscore pic if there is a new high score
        if(Score >= HighScore) {
            EndScreenTiles[1].draw(450,100);
        }
    }

    public static void initEndScreen() throws IOException
    {
        Font scoreFontDetails = new Font("Times New Roman" , Font.BOLD, 32);
        ScoreFont = new TrueTypeFont(scoreFontDetails, true);

        Font endScreenFontDetails = new Font("Times New Roman" , Font.BOLD, 48);
        EndScreenFont = new TrueTypeFont(endScreenFontDetails, true);

        //Load the score frame
        Texture scoreFrame = TextureLoader
            .getTexture("PNG", ResourceLoader
                .getResourceAsStream("res/score_frame.png"));
        EndScreenTiles[0] = new LevelTile(scoreFrame);

        //initialize the high score pic
        Texture highscorePicture = TextureLoader
            .getTexture("PNG",ResourceLoader
                .getResourceAsStream("res/medal.png"));
        EndScreenTiles[1] = new LevelTile(highscorePicture);
    }
}
