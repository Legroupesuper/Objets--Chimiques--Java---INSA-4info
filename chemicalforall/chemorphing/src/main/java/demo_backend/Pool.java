package demo_backend;

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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Pool {

	private ArrayList<MarkedImage> _pool;

	public Pool(){
		super();
//		this._pool = new ArrayList<MarkedImage>();
	}
	public ArrayList<MarkedImage> get_pool() {
		return _pool;
	}

	public void set_pool(ArrayList<MarkedImage> _pool) {
		this._pool = _pool;
	}
	public boolean loadPool(){
		FileInputStream fis = null;
		try {
		Gson gson = new Gson();
		Type collectionType = new TypeToken<ArrayList<MarkedImage>>(){}.getType();
		
		fis = new FileInputStream(Globals.POOL_INDEX);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis, System.getProperty("file.encoding")));
		this.set_pool((ArrayList<MarkedImage>) gson.fromJson(reader, collectionType));
		} catch (FileNotFoundException e) {
			return false;
		} catch (UnsupportedEncodingException e) {
			return false;
		} finally {
			try {
				fis.close();
			} catch (IOException e) {}
		}
		return true;
	}
	public boolean savePool(){
		Gson gson = new Gson();
		String poolSave = gson.toJson(this.get_pool());
		
		try{
			File oldIndex = new File(Globals.POOL_INDEX);
			File newIndex = new File(Globals.POOL_INDEX+"_foo.txt");
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
}
