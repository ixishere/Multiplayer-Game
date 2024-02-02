import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy implements Entity {

	private int x;
	private int y;
	private BufferedImage image;
	
	Random random = new Random();
	
	public Enemy(int x, int y, Textures textures) {
		this.x = x;
		this.y = y;
		this.image = textures.enemy;
	}
	
	public void tick() {
		y += 1;
	}
	
	public void render(Graphics g) {
		g.drawImage(image, x, y, null);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
}
