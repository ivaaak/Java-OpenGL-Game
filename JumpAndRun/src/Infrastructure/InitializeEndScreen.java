package Infrastructure;

import org.newdawn.slick.Color;

import static Startup.JumpAndRun.*;

public class InitializeEndScreen
{
// Renders the end screen which is shown if the player doesn't have any lifes left
    public static void drawEndscreen() {
        Color.white.bind();
        EndScreenTiles[0].draw(
                (Constants.GameConstants.SCREEN_SIZE_WIDTH - EndScreenTiles[0].getWidth())/2,
                (Constants.GameConstants.SCREEN_SIZE_HEIGHT - EndScreenTiles[0].getHeight())/2);
        Calculations.highScoreCalculate();
        ScoreFont.drawString(
                (Constants.GameConstants.SCREEN_SIZE_WIDTH / 2) - 50,
                (Constants.GameConstants.SCREEN_SIZE_HEIGHT/3) + 10,
                String.format("Final Score"), Color.black);

        EndScreenFont.drawString(
                (Constants.GameConstants.SCREEN_SIZE_WIDTH / 2) - 10,
                (Constants.GameConstants.SCREEN_SIZE_HEIGHT/3) + 40,
                String.format("%d" , Score), Color.red);

        ScoreFont.drawString(
                (Constants.GameConstants.SCREEN_SIZE_WIDTH / 2) + -50,
                (Constants.GameConstants.SCREEN_SIZE_HEIGHT/3) + 80,
                String.format("High Score"), Color.black);
        EndScreenFont.drawString(

                (Constants.GameConstants.SCREEN_SIZE_WIDTH / 2) - 10,
                (Constants.GameConstants.SCREEN_SIZE_HEIGHT/3) + 110,
                String.format("%d" , HighScore), Color.red);

        ScoreFont.drawString(
                (Constants.GameConstants.SCREEN_SIZE_WIDTH / 3) + 30,
                (Constants.GameConstants.SCREEN_SIZE_HEIGHT/3) + 150,
                String.format("Press N for new game"), Color.black);

        //draw medal if there is a new high score
        if(Score >= HighScore) {
            Color.white.bind();
            EndScreenTiles[1].draw(70,70);
        }
    }
}
