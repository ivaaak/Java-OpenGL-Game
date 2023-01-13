package Entities;

import Sprites.MySprite;
import java.awt.*;

public abstract class Entity
{
	private int x;
	private int y;
	private MySprite sprite;
	private boolean visible;
	
	private final Rectangle me = new Rectangle();
	private final Rectangle him = new Rectangle();

	public Entity(MySprite sprite, int x, int y)
	{
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		this.visible = true;
	}

	public boolean collidesWith(Entity other)
	{
		me.setBounds((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
		him.setBounds((int) other.x,
					  (int) other.y,
					  other.sprite.getWidth(),
					  other.sprite.getHeight());

		return me.intersects(him);
	}

	public void draw() {
		sprite.draw(x, y);
	}


	// getters
	public int getX() { return x; }
	public int getY() { return y; }
	public int getWidth() { return sprite.getWidth(); }
	public int getHeight() { return sprite.getHeight(); }


	// setters
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) { this.y = y; }


	public boolean isVisible() { return visible; }

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public abstract void collidedWith(Entity other);
}
