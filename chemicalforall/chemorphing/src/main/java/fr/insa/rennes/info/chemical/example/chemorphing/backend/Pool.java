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

import javax.imageio.ImageIO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Pool {

	private ArrayList<MarkedImage> _pool;

	public Pool(){
		super();
	}
	public ArrayList<MarkedImage> get_pool() {
		return _pool;
	}

	public void set_pool(ArrayList<MarkedImage> _pool) {
		this._pool = _pool;
	}
	public int size(){
		return _pool.size();
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
			if(this._pool == null){
				this._pool = new ArrayList<MarkedImage>();
			}
			
			try {
				fis.close();
			} catch (IOException e) {}
		}
		return true;
	}
	public boolean savePool(){
		Gson gson = new Gson();
		SimpleMarkedImage[] smiTab = new SimpleMarkedImage[_pool.size()];
		for(int i = 0 ; i < _pool.size() ; i++){
			smiTab[i] = new SimpleMarkedImage(_pool.get(i));
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
		if(this._pool == null){
			this._pool = new ArrayList<MarkedImage>();
		}
		this._pool.add(mi);
	}
	public void remove(MarkedImage mi){
		if(this._pool != null){
			this._pool.remove(mi);
		}
	}
	public MarkedImage get(int i){
		if(this._pool == null){
			this._pool = new ArrayList<MarkedImage>();
		}
		return this._pool.get(i); 
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
		public SimpleMarkedImage(MarkedImage im) {
			this(im.get_address(), im.get_point1().x, im.get_point1().y, im.get_point2().x, im.get_point2().y, im.get_point3().x, im.get_point3().y);
		}
	}
}
