package Entities;

import Physics.Collisions;
import Sprites.MySprite;
import Startup.JumpAndRun;

public class HeroEntity extends Entity
{
	private final Object game;

	public HeroEntity(Object game, MySprite sprite, int x, int y)
	{
		// Inherit
		super(sprite, x, y);
		// Dependency Injection
		this.game = game;
	}
	
	@Override
	public void collidedWith(Entity other)
	{
		JumpAndRun myGame = (JumpAndRun) game;
		Collisions.notifyObjectCollision(other);
	}
}
