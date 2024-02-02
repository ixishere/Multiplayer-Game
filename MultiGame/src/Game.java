import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public final String TITLE = "Java Game";

	private boolean running = false;
	private Thread thread;

	private int LAST_FPS = 0;
	private int LAST_TPS = 0;
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet = null;

	private BufferedImage background1 = null;
	private BufferedImage background2 = null;

	private Player player;
	private EntityController entityController;
	private Textures textures;
	
	private int backgroundIndex = 0;

	public void init() {
		BufferedImageLoader loader = new BufferedImageLoader();

		try {
			spriteSheet = loader.loadImage("/images/sprite_sheet.png");
			background1 = loader.loadImage("/images/background1.png");
			background2 = loader.loadImage("/images/background2.png");
		} catch (IOException e) {
			e.printStackTrace();
		}

		addKeyListener(new KeyInput(this));

		textures = new Textures(this);
		entityController = new EntityController(this, textures);
		player = new Player(500, 500, entityController, textures);
	}

	private synchronized void start() {
		if (running)
			return;

		running = true;
		thread = new Thread(this);
		thread.start();
	}

	private synchronized void stop() {
		if (!running)
			return;

		running = false;

		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.exit(1);
	}

	public void run() {
		init();

		long lastTime = System.nanoTime();
		final double amtOfTicks = 128;
		double ns = 1000000000 / amtOfTicks;
		double delta = 0;

		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			if (delta >= 1) {
				tick();
				updates++;
				delta--;
			}

			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames + ", TickRate: " + updates);

				LAST_FPS = frames;
				LAST_TPS = updates;

				updates = 0;
				frames = 0;
			}
		}

		stop();
	}

	private void tick() {
		player.tick();
		entityController.tick();
		
		if(backgroundIndex >= (GameValues.backgroundShiftIndex * 2)) {
			backgroundIndex = 0;
		} else {
			backgroundIndex++;
		}
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		// ----------------------------------//

		// Blank image, this clears the background to remove flashing.
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);

		// Draw background image
		if (backgroundIndex <= GameValues.backgroundShiftIndex) {
			g.drawImage(background1, 0, 0, getWidth(), getHeight(), this);
		}
		if (backgroundIndex >= GameValues.backgroundShiftIndex) {
			g.drawImage(background2, 0, 0, getWidth(), getHeight(), this);
		}

		// Draw FPS in top left of screen
		g.setColor(Color.green);
		g.drawString("FPS: " + LAST_FPS, 10, 20);
		g.drawString("TPS: " + LAST_TPS, 10, 40);
		
		// Draw Player & Entites
		player.render(g);
		entityController.render(g);
		
		// ----------------------------------//
		g.dispose();
		bs.show();
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		int amt = GameValues.playerSpeed;

		switch (key) {
			case KeyEvent.VK_W:
				player.setVelY(-amt);
				break;
			case KeyEvent.VK_A:
				player.setVelX(-amt);
				break;
			case KeyEvent.VK_S:
				player.setVelY(amt);
				break;
			case KeyEvent.VK_D:
				player.setVelX(amt);
				break;
			case KeyEvent.VK_SPACE:
				player.setShooting(true);
				break;
			default:
				break;
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		int amt = 0;
		
		switch (key) {
			case KeyEvent.VK_W:
				player.setVelY(-amt);
				break;
			case KeyEvent.VK_A:
				player.setVelX(-amt);
				break;
			case KeyEvent.VK_S:
				player.setVelY(amt);
				break;
			case KeyEvent.VK_D:
				player.setVelX(amt);
				break;
			case KeyEvent.VK_SPACE:
				player.setShooting(false);
				break;
			default:
				break;
		}
	}

	public static void main(String args[]) {
		Game game = new Game();

		game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		game.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		game.setMaximumSize(new Dimension(WIDTH, HEIGHT));

		JFrame frame = new JFrame(game.TITLE);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		game.start();
	}

	public BufferedImage getSpriteSheet() {
		return spriteSheet;
	}

}
