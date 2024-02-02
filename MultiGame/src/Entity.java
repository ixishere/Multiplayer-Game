import java.awt.Graphics;

public interface Entity {

	public void tick();
	public void render(Graphics g);
	
	public int getX();
	public int getY();
	
	public void setX(int x);
	public void setY(int y);
}
