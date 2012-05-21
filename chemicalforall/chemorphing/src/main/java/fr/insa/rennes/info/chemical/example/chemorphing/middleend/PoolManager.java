/* 
	Copyright (C) 2012 Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChemicalLibSuper.

    ChemicalLibSuper is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChemicalLibSuper is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with ChemicalLibSuper.  If not, see <http://www.gnu.org/licenses/>
*/
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
	
	private PoolManager(){}	// Overriding/disabling default constructor in order to prevent from abusive accessibility

	private static Pool pool;

	public static Pool getPool(){
		if(pool == null){
			pool = new Pool();
			pool.loadPool();
		}
		return pool;
	}
	
	public static boolean savePool(){
		return getPool().savePool();
	}

	public static boolean addToPool(MarkedImage mi, Point[] points, Dimension visibleDimension){
		boolean pointsOK = true;
		for(int i = 0 ; i<points.length ; i++){
			pointsOK = pointsOK && points[i] != null;
		}
		if(pointsOK){
			//Correct control points coordinates
			mi.setPoint1(new Point((int)(points[0].x * mi.getImage().getWidth(null) / visibleDimension.getWidth()),
					(int)(points[0].y * mi.getImage().getHeight(null) / visibleDimension.getHeight())));
			mi.setPoint2(new Point((int)(points[1].x * mi.getImage().getWidth(null) / visibleDimension.getWidth()),
					(int)(points[1].y * mi.getImage().getHeight(null) / visibleDimension.getHeight())));
			mi.setPoint3(new Point((int)(points[2].x * mi.getImage().getWidth(null) / visibleDimension.getWidth()),
					(int)(points[2].y * mi.getImage().getHeight(null) / visibleDimension.getHeight())));

			//Copy image file to the pool folder
			try {
				File src = new File(mi.getAddress());
				File dest = new File(Globals.getSetting("POOL_FOLDER")+"/"+src.getName());
				InputStream is = new FileInputStream(src);
				OutputStream os = new FileOutputStream(dest);
				byte[] buffer = new byte[1024];
				int bytesRead;
				while ((bytesRead = is.read(buffer)) != -1) {   
					os.write(buffer, 0, bytesRead);
				}

				//Set correct address for MarkedImage
				mi.setAddress(Globals.getSetting("POOL_FOLDER")+"/"+src.getName());


			} catch (IOException e) {
				return false;
			}

			//Add current MarkedImage to the pool
			PoolManager.getPool().add(mi);
		}
		return pointsOK;
	}


}