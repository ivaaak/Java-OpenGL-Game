package Infrastructure;

import Startup.JumpAndRun;
import org.newdawn.slick.Color;

public class InitializeHUD
{
    // Renders the score and lifes in the heads up display
    public static void drawHUD()
    {
        JumpAndRun.ScoreFont.drawString(10, 0, String.format("Score : %d", JumpAndRun.Score), Color.white);
        Color.white.bind();
        JumpAndRun.Lives[JumpAndRun.CurrentLives]
            .draw(
                Constants.GameConstants.SCREEN_SIZE_WIDTH - JumpAndRun.Lives[JumpAndRun.CurrentLives].getWidth(),
                0);
    }
}
