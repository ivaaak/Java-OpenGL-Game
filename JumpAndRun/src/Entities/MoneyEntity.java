package Entities;

import Sprites.MySprite;

public class MoneyEntity extends ObjectEntity
{
	public MoneyEntity(MySprite sprite, int x, int y) { super(sprite, x, y); }
	@Override
	public void collidedWith(Entity other) { }
}