import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public class EntityController {

	private LinkedList<Entity> entities = new LinkedList<Entity>();

	private long timeSinceLastShot = System.nanoTime();

	Random random = new Random();
	
	Game game;

	public EntityController(Game game, Textures textures) {
		this.game = game;
		
		addEntity(new Enemy(random.nextInt(Game.WIDTH), 0, textures));
	}

	public void tick() {
		try {
			for(Entity entity : entities) {
				entity.tick();
				
				if(entity instanceof Enemy) {
					if (entity.getY() > game.getHeight()) {
						entity.setY(0);
						entity.setX(random.nextInt(Game.WIDTH));
					}
				}
				
				if(entity instanceof Bullet) {
					if (entity.getY() < 0) {
						removeEntity(entity);
					}
				}
			}
		} catch (Exception e) {}
	}

	public void render(Graphics g) {
		for(Entity entity : entities) {
			entity.render(g);
		}
	}

	public void addEntity(Entity entity) {
		if(entity instanceof Bullet) {
			if (System.nanoTime() - timeSinceLastShot > GameValues.delayBetweenShots) {
				entities.add(entity);
				timeSinceLastShot = System.nanoTime();
			}
		} else {
			entities.add(entity);
		}
	}

	public void removeEntity(Entity entity) {
		entities.remove(entity);
	}
}
