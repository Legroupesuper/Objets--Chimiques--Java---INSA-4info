package fr.insa.rennes.info.chemical.example.chemorphing.middleend;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import fr.insa.rennes.info.chemical.example.chemorphing.backend.Globals;
import fr.insa.rennes.info.chemical.example.chemorphing.backend.MarkedImage;
import fr.insa.rennes.info.chemical.example.chemorphing.backend.Pool;

public class PoolManager {

	static private Pool pool;

	public static Pool getPool(){
		if(pool == null){
			pool = new Pool();
			pool.loadPool();
		}
		return pool;
	}

	public static boolean addToPool(MarkedImage mi, Point[] points, Dimension visibleDimension){
		boolean pointsOK = true;
		for(int i = 0 ; i<points.length ; i++){
			pointsOK = pointsOK && points[i] != null;
		}
		if(pointsOK){
			//Correct control points coordinates
			mi.set_point1(new Point((int)(points[0].x * mi.get_image().getWidth(null) / visibleDimension.getWidth()),
					(int)(points[0].y * mi.get_image().getHeight(null) / visibleDimension.getHeight())));
			mi.set_point2(new Point((int)(points[1].x * mi.get_image().getWidth(null) / visibleDimension.getWidth()),
					(int)(points[1].y * mi.get_image().getHeight(null) / visibleDimension.getHeight())));
			mi.set_point3(new Point((int)(points[2].x * mi.get_image().getWidth(null) / visibleDimension.getWidth()),
					(int)(points[2].y * mi.get_image().getHeight(null) / visibleDimension.getHeight())));

			System.out.println(mi.get_point1()+" : "+mi.get_point2()+" : "+mi.get_point3());

			//Copy image file to the pool folder
			try {
				File src = new File(mi.get_address());
				File dest = new File(Globals.POOL_FOLDER+"/"+src.getName());
				InputStream is = new FileInputStream(src);
				OutputStream os = new FileOutputStream(dest);
				byte[] buffer = new byte[1024];
				int bytesRead;
				while ((bytesRead = is.read(buffer)) != -1) {   
					os.write(buffer, 0, bytesRead);
				}

				//Set correct address for MarkedImage
				mi.set_address(Globals.POOL_FOLDER+"/"+src.getName());


			} catch (IOException e) {
				return false;
			}

			//Add current MarkedImage to the pool
			PoolManager.getPool().add(mi);
		} else {
			return false;
		}
		return true;
	}


}
