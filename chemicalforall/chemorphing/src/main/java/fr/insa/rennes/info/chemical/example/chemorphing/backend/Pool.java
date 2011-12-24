package fr.insa.rennes.info.chemical.example.chemorphing.backend;

import java.awt.Image;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Pool {

	private List<MarkedImage> pool;

	public Pool(){
		super();
	}
	public List<MarkedImage> getPool() {
		return pool;
	}

	public void setPool(List<MarkedImage> pool) {
		this.pool = pool;
	}
	public int size(){
		return pool.size();
	}
	public boolean loadPool(){
		FileInputStream fis = null;
		SimpleMarkedImage[] smiTab;
		try {
			Gson gson = new Gson();
			Type collectionType = new TypeToken<SimpleMarkedImage[]>(){}.getType();

			fis = new FileInputStream(Globals.getSetting("POOL_FOLDER")+"/"+Globals.getSetting("POOL_INDEX"));

			BufferedReader reader = new BufferedReader(new InputStreamReader(fis, System.getProperty("file.encoding")));
			smiTab = gson.fromJson(reader, collectionType);
			
			for(SimpleMarkedImage smi : smiTab){
				Image img = null;
				try {
					img = ImageIO.read(new File(smi.address));
				} catch (IOException e1) {
					//If the image cannot get loaded (file not found, damaged file, ...) then just don't add it
					break;
				}
				this.add(new MarkedImage(img, smi.address, new Point(smi.x1, smi.y1), new Point(smi.x2, smi.y2), new Point(smi.x3, smi.y3)));
			}
			
		} catch (FileNotFoundException e) {
			return false;
		} catch (UnsupportedEncodingException e) {
			return false;
		} finally {
			
			//Create an empty Map if nothing was loaded (very own policy)
			if(this.pool == null){
				this.pool = new ArrayList<MarkedImage>();
			}
			
			try {
				fis.close();
			} catch (IOException e) {}
		}
		return true;
	}
	public boolean savePool(){
		Gson gson = new Gson();
		SimpleMarkedImage[] smiTab = new SimpleMarkedImage[pool.size()];
		for(int i = 0 ; i < pool.size() ; i++){
			smiTab[i] = new SimpleMarkedImage(pool.get(i));
		}
		String poolSave = gson.toJson(smiTab);

		try{
			File oldIndex = new File(Globals.getSetting("POOL_FOLDER")+"/"+Globals.getSetting("POOL_INDEX"));
			File newIndex = new File(Globals.getSetting("POOL_FOLDER")+"/"+Globals.getSetting("POOL_INDEX")+"_foo.txt");
			PrintStream ps = new PrintStream(newIndex);
			ps.print(poolSave);
			ps.close();
			oldIndex.delete();
			newIndex.renameTo(oldIndex);
		}catch(FileNotFoundException e){
			return false;
		}
		return true;
	}
	public void add(MarkedImage mi){
		if(this.pool == null){
			this.pool = new ArrayList<MarkedImage>();
		}
		this.pool.add(mi);
	}
	public void remove(MarkedImage mi){
		if(this.pool != null){
			this.pool.remove(mi);
		}
	}
	public MarkedImage get(int i){
		if(this.pool == null){
			this.pool = new ArrayList<MarkedImage>();
		}
		return this.pool.get(i); 
	}

	private class SimpleMarkedImage{
		private String address;
		private int x1;
		private int y1;
		private int x2;
		private int y2;
		private int x3;
		private int y3;
		public SimpleMarkedImage(String address, int x1, int y1, int x2,
				int y2, int x3, int y3) {
			super();
			this.address = address;
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
			this.x3 = x3;
			this.y3 = y3;
		}
		private SimpleMarkedImage(MarkedImage im) {
			this(im.getAddress(), im.getPoint1().x, im.getPoint1().y, im.getPoint2().x, im.getPoint2().y, im.getPoint3().x, im.getPoint3().y);
		}
	}
}
