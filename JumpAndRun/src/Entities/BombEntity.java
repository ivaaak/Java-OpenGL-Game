package Entities;

import Sprites.MySprite;

public class BombEntity extends ObjectEntity
{
	public BombEntity(MySprite sprite, int x, int y) {
		super(sprite, x, y);
	}

	@Override
	public void collidedWith(Entity other) { }
}
