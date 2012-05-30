/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of libchloe.

    libchloe is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    libchloe is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with libchloe.  If not, see <http://www.gnu.org/licenses/>
*/
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