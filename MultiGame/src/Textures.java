import java.awt.image.BufferedImage;

public class Textures {

	private SpriteSheet ss = null;
	
	public BufferedImage player;
	public BufferedImage bullet;
	public BufferedImage enemy;
	
	public Textures(Game game) {
		ss = new SpriteSheet(game.getSpriteSheet());
		
		getTextures();
	}
	
	
	private void getTextures() {
		player = ss.grabImage(1, 1, 32, 32);
		bullet = ss.grabImage(2, 1, 32, 32);
		enemy = ss.grabImage(3, 1, 32, 32);
	}
}
