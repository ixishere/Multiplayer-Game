import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageLoader {

	public BufferedImage loadImage(String path) throws IOException {
		BufferedImage image = ImageIO.read(getClass().getResource(path));
		return image;
	}
	
}
