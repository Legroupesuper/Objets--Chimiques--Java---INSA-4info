
//////////////////////////////////////////////////////////////////
//                                                              //
//Morph 0.9 - My peculiar image morphing algorithm              //
//                                                              //
//David Tompkins -- 4/22/96                                     // 
//                                                              //
//tompkins@cs.stanford.edu                                      //
//http://www-cs-students.stanford.edu/~tompkins/                //
//                                                              //
//Copyright (c) 1996 by David Tompkins. All Rights Reserved.    //
//                                                              //
//Permission to use, copy, modify, and distribute this          //
//software and its documentation for NON-COMMERCIAL             //
//purposes and without fee is hereby granted provided that      //
//this copyright notice appears in all copies.                  //
//                                                              //
//////////////////////////////////////////////////////////////////


package morph;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Morphing{

	private static final int NUM_TRIANGLES = 14;	//This value should not be modified (otherwise it crashes anyway)

	private Image frames[] = null;
	private int framecnt;
	private ControlPoint cp1, cp2, cp3;
	private Image imagesrc, imagedst;

	public Morphing(Image srcImg, Image dstImg, int frameNb, ControlPoint cpt1, ControlPoint cpt2, ControlPoint cpt3) throws IOException{
		super();
		this.imagesrc = srcImg;
		this.imagedst = dstImg;

		this.framecnt = frameNb;
		this.cp1 = cpt1;
		this.cp2 = cpt2;
		this.cp3 = cpt3;

	}

	public Morphing(Image srcImg, Image dstImg, int frameNb, int srcX1, int srcY1, int srcX2, int srcY2, int srcX3, int srcY3, int destX1, int destY1, int destX2, int destY2, int destX3, int destY3) throws IOException{
		this(srcImg, dstImg, frameNb, new ControlPoint(srcX1, srcY1, destX1, destY1), new ControlPoint(srcX2, srcY2, destX2, destY2), new ControlPoint(srcX3, srcY3, destX3, destY3));
	}

	public Morphing(Image srcImg, Image dstImg, int frameNb, Point src1, Point src2, Point src3, Point dest1, Point dest2, Point dest3) throws IOException{
		this(srcImg, dstImg, frameNb, new ControlPoint(src1.x, src1.y, dest1.x, dest1.y), new ControlPoint(src2.x, src2.y, dest2.x, dest2.y), new ControlPoint(src3.x, src3.y, dest3.x, dest3.y));
	}

	public Image[] execute(){
		int          iw, ih;
		int          from[], to[];
		PixelGrabber pg;

		iw = imagesrc.getWidth(null);
		ih = imagesrc.getHeight(null);

		from = new int[iw * ih];
		to   = new int[iw * ih];

		pg = new PixelGrabber(imagesrc, 0, 0, iw, ih, from, 0, iw);
		try {
			pg.grabPixels();
		} catch (InterruptedException e){
			e.printStackTrace();
		}
		pg = null;

		pg = new PixelGrabber(imagedst, 0, 0, iw, ih, to, 0, iw);
		try {
			pg.grabPixels();
		} catch (InterruptedException e){
			e.printStackTrace();
		}
		pg = null;

		frames             = new Image[framecnt];
		frames[0]          = imagesrc;
		frames[framecnt-1] = imagedst;

		morph(from, to, iw, ih);

		return frames;

	}


	private void morph(int[] from, int[] to, int iw, int ih){
		int              f;
		int[]            step;
		Triangle         t;
		SimpleTriangle[] t1, t2, t3;

		t1 = new SimpleTriangle[NUM_TRIANGLES];
		t2 = new SimpleTriangle[NUM_TRIANGLES];
		t3 = new SimpleTriangle[NUM_TRIANGLES];

		// Create static triangle sets

		t = new Triangle(cp1.x1, cp1.y1, cp2.x1, cp2.y1, cp3.x1, cp3.y1);
		t.convert();
		t1[0] = t.t1;
		t1[1] = t.t2;

		t = new Triangle(cp1.x1, cp1.y1, cp3.x1, cp3.y1, 0, ih-1);
		t.convert();
		t1[2] = t.t1;
		t1[3] = t.t2;

		t = new Triangle(cp2.x1, cp2.y1, cp3.x1, cp3.y1, iw-1, ih-1);
		t.convert();
		t1[4] = t.t1;
		t1[5] = t.t2;

		t = new Triangle(0, 0, cp1.x1, cp1.y1, 0, ih-1);
		t.convert();
		t1[6] = t.t1;
		t1[7] = t.t2;

		t = new Triangle(iw-1, 0, cp2.x1, cp2.y1, iw-1, ih-1);
		t.convert();
		t1[8] = t.t1;
		t1[9] = t.t2;

		t = new Triangle(iw-1, 0, cp1.x1, cp1.y1, cp2.x1, cp2.y1);
		t.convert();
		t1[10] = t.t1;
		t1[11] = t.t2;

		t1[12] = new SimpleTriangle(0, iw-1, 0, cp1.x1, cp1.y1);
		t1[13] = new SimpleTriangle(0, iw-1, ih-1, cp3.x1, cp3.y1);

		t = new Triangle(cp1.x2, cp1.y2, cp2.x2, cp2.y2, cp3.x2, cp3.y2);
		t.convert();
		t2[0] = t.t1;
		t2[1] = t.t2;

		t = new Triangle(cp1.x2, cp1.y2, cp3.x2, cp3.y2, 0, ih-1);
		t.convert();
		t2[2] = t.t1;
		t2[3] = t.t2;

		t = new Triangle(cp2.x2, cp2.y2, cp3.x2, cp3.y2, iw-1, ih-1);
		t.convert();
		t2[4] = t.t1;
		t2[5] = t.t2;

		t = new Triangle(0, 0, cp1.x2, cp1.y2, 0, ih-1);
		t.convert();
		t2[6] = t.t1;
		t2[7] = t.t2;

		t = new Triangle(iw-1, 0, cp2.x2, cp2.y2, iw-1, ih-1);
		t.convert();
		t2[8] = t.t1;
		t2[9] = t.t2;

		t = new Triangle(iw-1, 0, cp1.x2, cp1.y2, cp2.x2, cp2.y2);
		t.convert();
		t2[10] = t.t1;
		t2[11] = t.t2;

		t2[12] = new SimpleTriangle(0, iw-1, 0, cp1.x2, cp1.y2);
		t2[13] = new SimpleTriangle(0, iw-1, ih-1, cp3.x2, cp3.y2);

		for (f = 1 ; f < (framecnt-1) ; f++){
			int i, x1, y1, x2, y2, x3, y3;
			double ratio;

			step  = new int[iw * ih];
			ratio = (double)f / (double)(framecnt-1);

			x1 = (int)Math.round((double)cp1.x1 + ((double)(cp1.x2 - cp1.x1) * ratio));
			y1 = (int)Math.round((double)cp1.y1 + ((double)(cp1.y2 - cp1.y1) * ratio));
			x2 = (int)Math.round((double)cp2.x1 + ((double)(cp2.x2 - cp2.x1) * ratio));
			y2 = (int)Math.round((double)cp2.y1 + ((double)(cp2.y2 - cp2.y1) * ratio));
			x3 = (int)Math.round((double)cp3.x1 + ((double)(cp3.x2 - cp3.x1) * ratio));
			y3 = (int)Math.round((double)cp3.y1 + ((double)(cp3.y2 - cp3.y1) * ratio));

			t = new Triangle(x1, y1, x2, y2, x3, y3);
			t.convert();
			t3[0] = t.t1;
			t3[1] = t.t2;

			t = new Triangle(x1, y1, x3, y3, 0, ih-1);
			t.convert();
			t3[2] = t.t1;
			t3[3] = t.t2;

			t = new Triangle(x2, y2, x3, y3, iw-1, ih-1);
			t.convert();
			t3[4] = t.t1;
			t3[5] = t.t2;

			t = new Triangle(0, 0, x1, y1, 0, ih-1);
			t.convert();
			t3[6] = t.t1;
			t3[7] = t.t2;

			t = new Triangle(iw-1, 0, x2, y2, iw-1, ih-1);
			t.convert();
			t3[8] = t.t1;
			t3[9] = t.t2;

			t = new Triangle(iw-1, 0, x1, y1, x2, y2);
			t.convert();
			t3[10] = t.t1;
			t3[11] = t.t2;

			t3[12] = new SimpleTriangle(0, iw-1, 0, x1, y1);
			t3[13] = new SimpleTriangle(0, iw-1, ih-1, x3, y3);

			for (i = 0 ; i < NUM_TRIANGLES ; i+=2){
				morph_triangles1(t1[i], t2[i], t3[i], from, to, step, iw, ratio);
				morph_triangles2(t1[i+1],t2[i+1],t3[i+1],from, to, step, iw, ratio);
			}

			frames[f] = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(iw, ih, step, 0, iw));
		}

		t1 = null;
		t2 = null;
		t3 = null;
	}

	private void morph_triangles1(SimpleTriangle t1, SimpleTriangle t2, 
			SimpleTriangle t3, int[] from, int[] to,
			int[] step, int iw, double ratio){
		int    x, y;
		double m11, m12, m21, m22, m31, m32;
		double x11, x12, x21, x22, x31, x32, y1, dy1, y2, dy2;

		m11 = (double)(t1.x3 - t1.x1) / (double)(t1.y3 - t1.y);
		m12 = (double)(t1.x3 - t1.x2) / (double)(t1.y3 - t1.y);

		m21 = (double)(t2.x3 - t2.x1) / (double)(t2.y3 - t2.y);
		m22 = (double)(t2.x3 - t2.x2) / (double)(t2.y3 - t2.y);

		m31 = (double)(t3.x3 - t3.x1) / (double)(t3.y3 - t3.y);
		m32 = (double)(t3.x3 - t3.x2) / (double)(t3.y3 - t3.y);

		x11 = (double)(t1.x1);
		x12 = (double)(t1.x2);
		y1  = (double)(t1.y);
		dy1 = (double)(t1.y3 - t1.y) / (double)(t3.y3 - t3.y);

		x21 = (double)(t2.x1);
		x22 = (double)(t2.x2);
		y2  = (double)(t2.y);
		dy2 = (double)(t2.y3 - t2.y) / (double)(t3.y3 - t3.y);

		x31 = (double)(t3.x1);
		x32 = (double)(t3.x2);

		for (y = t3.y ; y < t3.y3 ; y++){
			for (x = (int)x31 ; x <= (int)x32 ; x++){
				int p1, p2, a1, a2, a3, r1, r2, r3, g1, g2, g3, b1, b2, b3;

				double fract = ((double)x - x31) / (x32 - x31);

				p1 = from[((int)Math.round(y1) * iw) + (int)Math.round(x11 + (fract * (x12 - x11)))];
				p2 = to  [((int)Math.round(y2) * iw) + (int)Math.round(x21 + (fract * (x22 - x21)))];

				a1 = (p1 >> 24) & 0xFF;
				r1 = (p1 >> 16) & 0xFF;
				g1 = (p1 >>  8) & 0xFF;
				b1 = (p1)       & 0xFF;
				a2 = (p2 >> 24) & 0xFF;
				r2 = (p2 >> 16) & 0xFF;
				g2 = (p2 >>  8) & 0xFF;
				b2 = (p2)       & 0xFF;

				a3 = (int)((double)a1 + ((double)(a2 - a1) * ratio));
				r3 = (int)((double)r1 + ((double)(r2 - r1) * ratio));
				g3 = (int)((double)g1 + ((double)(g2 - g1) * ratio));
				b3 = (int)((double)b1 + ((double)(b2 - b1) * ratio));

				step[(y * iw) + x] = (a3 << 24) | (r3 << 16) | (g3 << 8) | b3;
			}

			x11 += m11;
			x12 += m12;
			x21 += m21;
			x22 += m22;
			x31 += m31;
			x32 += m32;
			y1  += dy1;
			y2  += dy2;
		}
	}

	private void morph_triangles2(SimpleTriangle t1, SimpleTriangle t2, 
			SimpleTriangle t3, int[] from, int[] to,
			int[] step, int iw, double ratio){
		int    x, y;
		double m11, m12, m21, m22, m31, m32;
		double x11, x12, x21, x22, x31, x32, y1, dy1, y2, dy2;

		m11 = (double)(t1.x3 - t1.x1) / (double)(t1.y - t1.y3);
		m12 = (double)(t1.x3 - t1.x2) / (double)(t1.y - t1.y3);

		m21 = (double)(t2.x3 - t2.x1) / (double)(t2.y - t2.y3);
		m22 = (double)(t2.x3 - t2.x2) / (double)(t2.y - t2.y3);

		m31 = (double)(t3.x3 - t3.x1) / (double)(t3.y - t3.y3);
		m32 = (double)(t3.x3 - t3.x2) / (double)(t3.y - t3.y3);

		x11 = (double)(t1.x1);
		x12 = (double)(t1.x2);
		y1  = (double)(t1.y);
		dy1 = (double)(t1.y - t1.y3) / (double)(t3.y - t3.y3);

		x21 = (double)(t2.x1);
		x22 = (double)(t2.x2);
		y2  = (double)(t2.y);
		dy2 = (double)(t2.y - t2.y3) / (double)(t3.y - t3.y3);

		x31 = (double)(t3.x1);
		x32 = (double)(t3.x2);

		for (y = t3.y ; y > t3.y3 ; y--){
			for (x = (int)x31 ; x <= (int)x32 ; x++){
				int p1, p2, a1, a2, a3, r1, r2, r3, g1, g2, g3, b1, b2, b3;

				double fract = ((double)x - x31) / (x32 - x31);

				p1 = from[((int)Math.round(y1) * iw) + (int)Math.round(x11 + (fract * (x12 - x11)))];
				p2 = to  [((int)Math.round(y2) * iw) + (int)Math.round(x21 + (fract * (x22 - x21)))];

				a1 = (p1 >> 24) & 0xFF;
				r1 = (p1 >> 16) & 0xFF;
				g1 = (p1 >>  8) & 0xFF;
				b1 = (p1)       & 0xFF;
				a2 = (p2 >> 24) & 0xFF;
				r2 = (p2 >> 16) & 0xFF;
				g2 = (p2 >>  8) & 0xFF;
				b2 = (p2)       & 0xFF;

				a3 = (int)((double)a1 + ((double)(a2 - a1) * ratio));
				r3 = (int)((double)r1 + ((double)(r2 - r1) * ratio));
				g3 = (int)((double)g1 + ((double)(g2 - g1) * ratio));
				b3 = (int)((double)b1 + ((double)(b2 - b1) * ratio));

				step[(y * iw) + x] = (a3 << 24) | (r3 << 16) | (g3 << 8) | b3;
			}

			x11 += m11;
			x12 += m12;
			x21 += m21;
			x22 += m22;
			x31 += m31;
			x32 += m32;
			y1  -= dy1;
			y2  -= dy2;
		}
	}

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
		
		String srcAddr = "pouf.png";
		Image src = null;
		try {
			src = ImageIO.read(new File(srcAddr));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Point src1 = new Point(32, 59);
		Point src2 = new Point(78, 58);
		Point src3 = new Point(55, 93);

		String destAddr = "lion.png";
		Image dest = null;
		try {
			dest = ImageIO.read(new File(destAddr));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Point dest1 = new Point(24, 56);
		Point dest2 = new Point(74, 52);
		Point dest3 = new Point(54, 100);
		
		
		String resultName = srcAddr+"_to_"+destAddr;
		
		try {
			//Create Morphing object
			momo = new Morphing(src, dest, 20, src1, src2, src3, dest1, dest2, dest3);
			
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
				ok = ok && store(result[i], resultFolderName+"/"+resultName+"_"+i+".png", "png");
			}
			
			//Print a confirmation message
			System.out.println("SUCCESS "+(System.currentTimeMillis() - l1)+"ms");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	

}
