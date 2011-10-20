package demo_backend;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tools {

	public static boolean store(Image image, String fileName, String fileType){
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
				image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bufferedImage.createGraphics();
		g2.drawImage(image, null, null);
		File imageFile = new File(fileName);
		try {
			ImageIO.write(bufferedImage, fileType, imageFile);
		} catch (IOException e) {
			return false;
		}

		return true;
	}

}
