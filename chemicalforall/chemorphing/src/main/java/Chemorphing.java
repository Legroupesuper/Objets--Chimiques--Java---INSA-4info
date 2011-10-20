import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import demo_backend.Globals;
import demo_backend.MarkedImage;
import demo_backend.Pool;
import demo_frontend.ChemorphWindow;

import morph.Morphing;

/**
 * 
 */

/**
 * @author thorthur
 *
 */
public class Chemorphing {



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

	
	
	public static void main(String[] args){
		
		
		
		long l1 = System.currentTimeMillis();
		Morphing momo;
		
		
		
		String srcAddr = Globals.POOL_FOLDER+"/keith.gif";
		Image src = null;
		try {
			src = ImageIO.read(new File(srcAddr));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		//KEITH points
		Point src1 = new Point(58, 71);
		Point src2 = new Point(93, 68);
		Point src3 = new Point(80, 87);
		
		
		//POUF points
//		Point src1 = new Point(32, 59);
//		Point src2 = new Point(78, 58);
//		Point src3 = new Point(55, 93);
		
		

		String destAddr = Globals.POOL_FOLDER+"/magda.gif";
		Image dest = null;
		try {
			dest = ImageIO.read(new File(destAddr));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		//MAGDA points
		Point dest1 = new Point(77, 75);
		Point dest2 = new Point(107, 79);
		Point dest3 = new Point(93, 96);


		//LION points
//		Point dest1 = new Point(24, 56);
//		Point dest2 = new Point(74, 52);
//		Point dest3 = new Point(54, 100);
		

		Pool pool = new Pool();
		pool.add(new MarkedImage(src, srcAddr, src1, src2, src3));
		pool.add(new MarkedImage(dest, destAddr, dest1, dest2, dest3));
		System.out.println(pool.savePool());
		

		/*
		String resultName = "morph_";
		
		try {
			//Create Morphing object
			momo = new Morphing(src, dest, 10, src1, src2, src3, dest1, dest2, dest3);
			
			//Run the effect and get its results
			Image[] result = momo.execute();
			
			
			//Create a folder to put the results
			String resultFolderName;
			File imageDir;
			do{
				resultFolderName = resultName+"_"+String.valueOf(System.currentTimeMillis());
				imageDir = new File(resultFolderName);
			} while(!imageDir.mkdir());
			
			
			//Store the results in files
			boolean ok = true;
			for(int i = 0 ; i < result.length ; i++){
				ok = ok && store(result[i], resultFolderName+"/"+resultName+"_"+i+".gif", "gif");
			}
			
			//Print a confirmation message
			System.out.println("SUCCESS "+(System.currentTimeMillis() - l1)+"ms");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		/*
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new ChemorphWindow().setVisible(true);
			}
		});
		
		*/
	}

}
