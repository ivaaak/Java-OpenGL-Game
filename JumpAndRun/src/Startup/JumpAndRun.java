package Startup;

import Entities.Entity;
import Entities.HeroEntity;
import Infrastructure.*;
import Sprites.LevelTile;
import Sprites.MySprite;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.util.ArrayList;

public class JumpAndRun
{
// Game Variables
	public static boolean IsFinished;
	public static HeroEntity HeroEntityLeft, HeroEntityRight;
	public static LevelTile BackgroundTile;
	public static TrueTypeFont ScoreFont;
	public static TrueTypeFont EndScreenFont;
	public static int Score = 0;
	public static int CurrentLives = 3;
	public static int HighScore;
	public static boolean FlipAvatar;
	public static boolean NewGame;
// Data Collections
	public static ArrayList<Entity> MoneyCollection;
	public static ArrayList<Entity> BombCollection;
	public static LevelTile[] Lives = new LevelTile[5];
	public static LevelTile[] EndScreenTiles = new LevelTile[2];

// Application entrypoint
	public static void main(String[] args)
	{
		JumpAndRun myGame = new JumpAndRun();
		myGame.start();
	}

	public void start()
	{
		try {
			init();
			run();
		} catch (Exception e) {
			e.printStackTrace(System.err);
			Sys.alert(Constants.GameConstants.GAME_TITLE, "An error : " + System.err.toString());
		} finally {
			cleanup();
		}
		System.exit(0);
	}


// Main initialization method for the game
	private void init() throws Exception
	{
		try {
			OpenGLInit.initGL(
					Constants.GameConstants.SCREEN_SIZE_WIDTH,
					Constants.GameConstants.SCREEN_SIZE_HEIGHT);
			initGame();
		} catch (IOException e) {
			e.printStackTrace();
			IsFinished = true;
		}
	}

//Initialize the game
	private void initGame() throws Exception
	{
		Texture leftTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/avatar.png"));
		MySprite playerSprite = new MySprite(leftTexture);
		HeroEntityLeft = new HeroEntity(this, playerSprite, 0, Constants.GameConstants.SCREEN_SIZE_HEIGHT - playerSprite.getHeight());

		Texture rightTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/avatar_right.png"));
		MySprite playerSpriteRight = new MySprite(rightTexture);
		HeroEntityRight = new HeroEntity(this, playerSpriteRight, 0, Constants.GameConstants.SCREEN_SIZE_HEIGHT - playerSprite.getHeight());

		Background.initBackground();
		InitializeObjects.initGameObjects();
		HUD.drawLives();
		EndScreen.initEndScreen();
	}

// Run the game
	private void run() throws IOException {
		while (!IsFinished)
		{
			Display.update();

			if (Display.isCloseRequested()) {
				IsFinished = true;
			} else if (Display.isActive()) {
				Physics.Logic.logic();
				Render.render();
				Display.sync(Constants.GameConstants.FRAMERATE);
			}
		}
	}

// Do game-specific cleanup
	private void cleanup()
	{
		Display.destroy();
	}
}