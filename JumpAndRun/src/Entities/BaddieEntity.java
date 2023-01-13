package Entities;

import Sprites.MySprite;

public class BaddieEntity extends ObjectEntity
{
	public BaddieEntity(MySprite sprite, int x, int y)
	{
		super(sprite, x, y);
	}

	@Override
	public void collidedWith(Entity other) { }
}
