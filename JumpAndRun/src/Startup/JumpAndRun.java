package Startup;

import Entities.Entity;
import Entities.HeroEntity;
import Infrastructure.*;
import Physics.Collisions;
import Sprites.LevelTile;
import Sprites.MySprite;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

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
			OpenGLInit.initGL(
					Constants.GameConstants.SCREEN_SIZE_WIDTH,
					Constants.GameConstants.SCREEN_SIZE_HEIGHT);
			initGame();
		} catch (IOException e) {
			e.printStackTrace();
			isFinished = true;
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
		HUD.initLives();
		EndScreen.initEndScreen();
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
				int newY = rand.nextInt(1, 500);
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
				int newY = rand.nextInt(1, 500);
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