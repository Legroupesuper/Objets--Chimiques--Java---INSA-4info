package fr.insa.rennes.info.chemical.example.chemorphing.middleend;

import java.awt.Image;
import java.io.IOException;

import fr.insa.rennes.info.chemical.example.chemorphing.backend.Globals;
import fr.insa.rennes.info.chemical.example.chemorphing.backend.MarkedImage;
import fr.insa.rennes.info.chemical.example.chemorphing.morph.Morphing;

public class MorphManager {
	
	private MorphManager(){}	// Overriding/disabling default constructor in order to prevent from abusive accessibility

	private static Image[] imgTab;

	public static Image[] getImgTab() {
		return imgTab;
	}

	public static void morph(int nb){

		// TODO Launch morphing (MUST be chemical !)
		//And of course, it is not chemical at all for the moment

		Morphing morpher;

		int frameNumber = 10;

		MarkedImage[] tab = selectRandom(nb);

		imgTab = new Image[tab.length * frameNumber];
		int imgTabIndex = 0;

		for(int i=0 ; i<tab.length ; i++){
			MarkedImage mi1 = tab[i];
			MarkedImage mi2 = tab[(i+1)%tab.length];
			//Create Morphing object
			try {
				morpher = new Morphing(mi1.getImage(), mi2.getImage(), frameNumber, mi1.getPoint1(), mi1.getPoint2(), mi1.getPoint3(), mi2.getPoint1(), mi2.getPoint2(), mi2.getPoint3());
			} catch (IOException e) {
				Globals.logger.warning(e.getLocalizedMessage());
				break;
			}

			//Run the effect and get its results
			Image[] result = morpher.execute();


			//Store the results in tab
			for(int j = 0 ; j < result.length ; j++){
				imgTab[imgTabIndex++] = result[j];
			}

		}

		WindowManager.addAnimation(imgTab);

	}

	//TODO Should disappear, replaced by proper chemical pseudo-random selection
	private static MarkedImage[] selectRandom(int nb){
		MarkedImage[] resulTab = new MarkedImage[nb];
		for(int i = 0 ; i < nb ; i++){
			resulTab[i] = PoolManager.getPool().get(i);
		}
		return resulTab;
	}


}
