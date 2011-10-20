package fr.insa.rennes.info.chemical.example.chemorphing;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.SwingUtilities;

import fr.insa.rennes.info.chemical.example.chemorphing.backend.MarkedImage;
import fr.insa.rennes.info.chemical.example.chemorphing.backend.Pool;
import fr.insa.rennes.info.chemical.example.chemorphing.backend.Tools;
import fr.insa.rennes.info.chemical.example.chemorphing.frontend.AnimationDisplayPanel;
import fr.insa.rennes.info.chemical.example.chemorphing.frontend.ChemorphWindow;
import fr.insa.rennes.info.chemical.example.chemorphing.morph.Morphing;

/**
 * 
 */

/**
 * @author thorthur
 *
 */
public class Chemorphing {



	public static void main(String[] args){



		long l1 = System.currentTimeMillis();
		Morphing momo;

		int FRAME_NUMBER = 10;

		/*
		String srcAddr = Globals.POOL_FOLDER+"/pouf.png";
		Image src = null;
		try {
			src = ImageIO.read(new File(srcAddr));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		//KEITH points
//		Point src1 = new Point(58, 71);
//		Point src2 = new Point(93, 68);
//		Point src3 = new Point(80, 87);


		//POUF points
		Point src1 = new Point(32, 59);
		Point src2 = new Point(78, 58);
		Point src3 = new Point(55, 93);



		String destAddr = Globals.POOL_FOLDER+"/lion.png";
		Image dest = null;
		try {
			dest = ImageIO.read(new File(destAddr));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		//MAGDA points
//		Point dest1 = new Point(77, 75);
//		Point dest2 = new Point(107, 79);
//		Point dest3 = new Point(93, 96);


		//LION points
		Point dest1 = new Point(24, 56);
		Point dest2 = new Point(74, 52);
		Point dest3 = new Point(54, 100);
		 */

		Pool pool = new Pool();
		pool.loadPool();



		String resultName = "morph_";

		MarkedImage[] tab = pool.selectRandom(12);

		final Image[] imgTab = new Image[tab.length * FRAME_NUMBER];
		int imgTabIndex = 0;


		//Create a folder to put the results
		String resultFolderName;
		File imageDir;
		do{
			resultFolderName = resultName+"_"+String.valueOf(System.currentTimeMillis());
			imageDir = new File(resultFolderName);
		} while(!imageDir.mkdir());
		
		for(int i=0 ; i<tab.length ; i++){
			MarkedImage mi1 = tab[i];
			MarkedImage mi2 = tab[(i+1)%tab.length];
			try{
				//Create Morphing object
				momo = new Morphing(mi1.get_image(), mi2.get_image(), FRAME_NUMBER, mi1.get_point1(), mi1.get_point2(), mi1.get_point3(), mi2.get_point1(), mi2.get_point2(), mi2.get_point3());

				//Run the effect and get its results
				Image[] result = momo.execute();



				//Store the results in files
//				boolean ok = true;
//				for(int j = 0 ; j < result.length ; j++){
//					ok = ok && Tools.store(result[j], resultFolderName+"/"+resultName+i+"_"+j+".png", "png");
//				}

				//Store the results in tab
				for(int j = 0 ; j < result.length ; j++){
					imgTab[imgTabIndex++] = result[j];
				}

				pool.savePool();
				
				//Print a confirmation message
				System.out.println("SUCCESS "+(System.currentTimeMillis() - l1)+"ms");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}


		//TODO wannabe definite main
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new ChemorphWindow().setVisible(true);
			}
		});

		 
	}

}
