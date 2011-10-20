package fr.insa.rennes.info.chemical.example.chemorphing.middleend;

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
	
	
	
}
