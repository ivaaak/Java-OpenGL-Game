package Sprites;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class MySprite
{
	private final int width;
	private final int height;
	private final Texture texture;

	public MySprite(Texture texture)
	{
		this.width = texture.getImageWidth();
		this.height = texture.getImageHeight();
		this.texture = texture;
	}

	public void draw(int x, int y)
	{
		GL11.glPushMatrix();
		texture.bind();
		GL11.glTranslatef(x, y, 0);
		// draw a quad textured to match the sprite
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(0, 0);
		GL11.glTexCoord2f(0, texture.getHeight());
		GL11.glVertex2f(0, height);
		GL11.glTexCoord2f(texture.getWidth(), texture.getHeight());
		GL11.glVertex2f(width, height);
		GL11.glTexCoord2f(texture.getWidth(), 0);
		GL11.glVertex2f(width, 0);
		GL11.glEnd();
		GL11.glPopMatrix();
	}

// getters
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	// public Texture getTexture() { return texture; }

// setters
	// public void setWidth(int width) { this.width = width; }
	// public void setHeight(int height) { this.height = height; }
	// public void setTexture(Texture texture) { this.texture = texture; }
}
