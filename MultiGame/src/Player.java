import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player {

	public int x;
	public int y;
	
	private int velX = 0;
	private int velY = 0;
	
	private boolean shooting = false;
	
	private BufferedImage player;
	
	private EntityController controller;
	private Textures textures;
	
	public Player(int x, int y, EntityController controller, Textures textures) {
		this.x = x;
		this.y = y;
		this.controller = controller;
		this.textures = textures;
		
		player = textures.player;
	}
	
	public void tick() {
		int newX = x + velX;
		int newY = y + velY;
		
		if(!(newX > (Game.WIDTH - (32 * GameValues.spriteScaleFactor))) && !(newX < 0)) {
			x += velX;
		}
		
		if(!(newY > (Game.HEIGHT - (32 * GameValues.spriteScaleFactor))) && !(newY < 0)) {
			y += velY;
		}
		
		if(shooting) {
			controller.addEntity(new Bullet(x, y, textures));
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(player, x, y, null);
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setVelX(int velX) {
		this.velX = velX;
	}
	
	public int getVelX() {
		return velX;
	}
	
	public void setVelY(int velY) {
		this.velY = velY;
	}
	
	public int getVelY() {
		return velY;
	}
	
	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}
	
	public boolean isShooting() {
		return shooting;
	}
}
