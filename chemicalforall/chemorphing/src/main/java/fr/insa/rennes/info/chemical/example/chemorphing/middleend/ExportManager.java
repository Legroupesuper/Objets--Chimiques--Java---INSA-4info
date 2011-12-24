package fr.insa.rennes.info.chemical.example.chemorphing.middleend;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.IIOException;

import fr.insa.rennes.info.chemical.example.chemorphing.backend.GifSequenceWriter;
import fr.insa.rennes.info.chemical.example.chemorphing.backend.Globals;

public class ExportManager {

	private ExportManager(){}	// Overriding/disabling default constructor in order to prevent from abusive accessibility

	public static void export(File outputFile) {
		
		// grab the output image type from the first image in the sequence
		BufferedImage firstImage = imgToBuffImg(MorphManager.getImgTab()[0]);

		// create a gif sequence with the type of the first image, which loops continuously
		GifSequenceWriter writer;
		try {
			writer = new GifSequenceWriter(outputFile, firstImage.getType(), Integer.parseInt(Globals.getSetting("EXPORT_SECONDS_BETWEEN_FRAMES")), false);


			// write out the first image to our sequence...
			writer.writeToSequence(firstImage);
			for(int i=1; i<MorphManager.getImgTab().length; i++) {
				BufferedImage nextImage = imgToBuffImg(MorphManager.getImgTab()[i]);
				writer.writeToSequence(nextImage);
			}

			writer.close();

		} catch (IIOException e) {
			Globals.logger.warning(e.getLocalizedMessage());
		} catch (IOException e) {
			Globals.logger.warning(e.getLocalizedMessage());
		}
	}

	private static BufferedImage imgToBuffImg(Image img){
		BufferedImage bufImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		bufImage.getGraphics().drawImage(img, 0, 0, null);
		return bufImage;
	}
	
	



}
