package fr.insa.rennes.info.chemical.example.chemorphing.backend;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import fr.insa.rennes.info.chemical.example.chemorphing.frontend.ChemorphWindow;

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
	
	public static void init(){
		initModel();
		initView();
	}
	
	public static void initModel(){
		
	}
	
	public static void initView(){

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new ChemorphWindow().setVisible(true);
			}
		});
		
	}

}
