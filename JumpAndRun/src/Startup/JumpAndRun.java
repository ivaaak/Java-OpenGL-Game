package Startup;

import Entities.Entity;
import Entities.HeroEntity;
import Infrastructure.InitializeObjects;
import Infrastructure.Render;
import Physics.Collisions;
import Sprites.LevelTile;
import Sprites.MySprite;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class JumpAndRun
{
// Game Variables
	private boolean isFinished;
	public static HeroEntity HeroEntityLeft, HeroEntityRight;

	public static LevelTile BackgroundTile;
	public static TrueTypeFont ScoreFont;
	public static TrueTypeFont EndScreenFont;
	public static int Score = 0;
	public static int CurrentLives = 3;
	public static int HighScore;
	public static boolean FlipAvatar;
	private boolean newGame;
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
			initGL(Constants.GameConstants.SCREEN_SIZE_WIDTH, Constants.GameConstants.SCREEN_SIZE_HEIGHT);
			initGame();
		} catch (IOException e) {
			e.printStackTrace();
			isFinished = true;
		}
	}

// Initialize the game display window
	private void initGL(int width, int height) throws Exception
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

		Font scoreFontDetails = new Font("Times New Roman" , Font.BOLD, 24);
		ScoreFont = new TrueTypeFont(scoreFontDetails, true);

		Font endScreenFontDetails = new Font("Times New Roman" , Font.BOLD, 24);
		EndScreenFont = new TrueTypeFont(endScreenFontDetails, true);
	}

//Initialize the game
	private void initGame() throws Exception
	{
		Texture texture;
		texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/avatar.png"));
		MySprite playerSprite = new MySprite(texture);
		HeroEntityLeft = new HeroEntity(this, playerSprite, 0, Constants.GameConstants.SCREEN_SIZE_HEIGHT - playerSprite.getHeight());

		texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/avatar_right.png"));
		MySprite playerSpriteRight = new MySprite(texture);
		HeroEntityRight = new HeroEntity(this, playerSpriteRight, 0, Constants.GameConstants.SCREEN_SIZE_HEIGHT - playerSprite.getHeight());

		initBackground();
		InitializeObjects.initGameObjects();
		initLives();
		initEndScreen();
	}


// Load the background image
	private void initBackground() throws IOException
	{
		Texture texture;
		texture = TextureLoader
			.getTexture("PNG", ResourceLoader
				.getResourceAsStream("res/background_tile.png"));

		BackgroundTile = new LevelTile(texture);
	}

// Show the end screen
	private void initEndScreen() throws IOException
	{
		//Load the frame, which shows the score in the middle of the screen
		Texture scoreFrame = TextureLoader
			.getTexture("PNG",ResourceLoader
					.getResourceAsStream("res/score_frame.png"));
		EndScreenTiles[0] = new LevelTile(scoreFrame);

		//initialize the medal (for the new high score)
		Texture medal = TextureLoader
			.getTexture("PNG",ResourceLoader
					.getResourceAsStream("res/medal.png"));
		EndScreenTiles[1] = new LevelTile(medal);
	}

// Visualize of the lives of the player
	private void initLives() throws Exception
	{
		//Loads the heart sprite in the array lives[] of type LevelTile
		for (int i = 0; i <= CurrentLives; i++)
		{
			Texture texture = TextureLoader
				.getTexture("PNG",ResourceLoader
					.getResourceAsStream("res/heart" + i + ".png"));
			Lives[i] = new LevelTile(texture);
		}
	}

// Run the game
	private void run()
	{
		while (!isFinished)
		{
			Display.update();

			if (Display.isCloseRequested()) {
				isFinished = true;
			} else if (Display.isActive()) {
				logic();
				Render.render();
				Display.sync(Constants.GameConstants.FRAMERATE);
			}
		}
	}

// Do game-specific cleanup
	private void cleanup()
	{
		// TODO: save anything you want to disk here
		// Close the window
		Display.destroy();
	}

	// Do calculations, handle input
	private void logic()
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			isFinished = true;
		}

		if(CurrentLives > 0 ) {
			logicHero();
			logicObjects();
			Collisions.checkForCollision();
		} else {
		   if (Keyboard.isKeyDown(Keyboard.KEY_N)) {
			   CurrentLives = 3;
			   Score = 0;
			   newGame = true;
			   logicObjects();
			   logicHero();
			   Collisions.checkForCollision();
	        }
		   newGame= false;
		}
	}

// Calculate the movement and position of the game objects: money and bombs
	private void logicObjects()
	{
		for (Entity moneyEntity : MoneyCollection)
		{
			moneyEntity.setY(moneyEntity.getY() + Constants.GameConstants.ITEM_MOVEMENT_SPEED);
			//when the money reaches the ground or when a new game is started the x and y of the money are reset to random values,
			//this way it is guaranteed that the number of money that can be on the game window at any given time is the number of the elements in the money array
			if (moneyEntity.getY() + moneyEntity.getHeight() > Display.getDisplayMode().getHeight() || newGame) {
				Random rand = new Random();
				int newX = rand.nextInt(Constants.GameConstants.SCREEN_SIZE_WIDTH - moneyEntity.getWidth());
				int newY = rand.nextInt(
						-moneyEntity.getHeight() - (-Constants.GameConstants.SPAWN_SPACE_HEIGHT - moneyEntity.getHeight()))
						+ (-Constants.GameConstants.SPAWN_SPACE_HEIGHT - moneyEntity.getHeight());
				moneyEntity.setX(newX);
				moneyEntity.setY(newY);
			}
		}
		// make every bomb in the array fall
		for (Entity bombEntity : BombCollection)
		{
			bombEntity.setY(bombEntity.getY() + 2 * Constants.GameConstants.ITEM_MOVEMENT_SPEED);
			//when the bomb reaches the ground or when a new game is started the x and y of the bomb are reset to random values,
			//this way it is guaranteed that the number of bombs that can be on the game window at any given time is the number of the elements in the bombs array
			if (bombEntity.getY() + bombEntity.getHeight() > Display.getDisplayMode().getHeight() || newGame) {
				Random rand = new Random();
				int newX = rand.nextInt(Constants.GameConstants.SCREEN_SIZE_WIDTH - bombEntity.getWidth());
				int newY = rand.nextInt(
						-bombEntity.getHeight() - (-Constants.GameConstants.SPAWN_SPACE_HEIGHT - bombEntity.getHeight()))
						+ (-Constants.GameConstants.SPAWN_SPACE_HEIGHT - bombEntity.getHeight());
				bombEntity.setX(newX);
				bombEntity.setY(newY);
			}
		}
	}

// Handles the User input responsible for the movement of the avatar
	private void logicHero()
	{
		// TODO move speed calculation to variable?
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			if(!FlipAvatar) {
				HeroEntityRight.setX(HeroEntityLeft.getX());
				FlipAvatar = !FlipAvatar;
			}
			if(HeroEntityRight.getX() + HeroEntityRight.getWidth() + Constants.GameConstants.HERO_MOVEMENT_SPEED < Display.getDisplayMode().getWidth()) {
				HeroEntityRight.setX(HeroEntityRight.getX() + Constants.GameConstants.HERO_MOVEMENT_SPEED);

			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			if(FlipAvatar) {
				HeroEntityLeft.setX(HeroEntityRight.getX());
				FlipAvatar = !FlipAvatar;
			}
			if(HeroEntityLeft.getX() - Constants.GameConstants.HERO_MOVEMENT_SPEED >= 0) {
				HeroEntityLeft.setX(HeroEntityLeft.getX() - Constants.GameConstants.HERO_MOVEMENT_SPEED);
			}
		}
	}
}