package Entities;
import Sprites.MySprite;

public abstract class ObjectEntity extends Entity
{
	public ObjectEntity(MySprite sprite, int x, int y) { super(sprite, x, y); }

	@Override
	public void collidedWith(Entity other)
	{
		System.out.println("Collision detected with " + other.toString());
	}
}
