package fr.insa.rennes.info.chemical.example.chemorphing.middleend;

import java.awt.Image;
import java.io.IOException;

import fr.insa.rennes.info.chemical.example.chemorphing.backend.MarkedImage;
import fr.insa.rennes.info.chemical.example.chemorphing.morph.Morphing;

public class MorphManager {
	
	private static Image[] imgTab;
	
	public static Image[] get_imgTab() {
		return imgTab;
	}

	public static void morph(int nb){

		// TODO Launch morphing (MUST be chemical !)

		Morphing momo;

		int FRAME_NUMBER = 10;

		MarkedImage[] tab = PoolManager.getPool().selectRandom(12);

		imgTab = new Image[tab.length * FRAME_NUMBER];
		int imgTabIndex = 0;
		
		for(int i=0 ; i<tab.length ; i++){
			MarkedImage mi1 = tab[i];
			MarkedImage mi2 = tab[(i+1)%tab.length];
				//Create Morphing object
				try {
					momo = new Morphing(mi1.get_image(), mi2.get_image(), FRAME_NUMBER, mi1.get_point1(), mi1.get_point2(), mi1.get_point3(), mi2.get_point1(), mi2.get_point2(), mi2.get_point3());
				} catch (IOException e) {
					e.printStackTrace();
					break;
				}

				//Run the effect and get its results
				Image[] result = momo.execute();


				//Store the results in tab
				for(int j = 0 ; j < result.length ; j++){
					imgTab[imgTabIndex++] = result[j];
				}

		}
		
		WindowManager.addAnimation(imgTab);
		
	}

}
