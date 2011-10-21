package fr.insa.rennes.info.chemical.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;


/*
 * This class implements an random strategy 
 */

public class RandomIncrementStrategy implements IncrementStrategy {

	/*
	 * List containing, for each type of parameters, a hashmap which associates a value to its "random" value
	 */
	ArrayList<HashMap<Integer, Integer>> mapPermuteNumbers; 
	/*
	 * A boolean needed for the first execution of increment
	 */
	boolean firstRun = true;
	
	
	public RandomIncrementStrategy(int[] maxIndex){
		mapPermuteNumbers= new ArrayList<HashMap<Integer,Integer>>(maxIndex.length);
		for (int i=0; i< maxIndex.length ; i++ ){
			List<Integer> listeValeurs = new ArrayList<Integer>();
			for (int n=0; n< maxIndex[i];n++){
				listeValeurs.add(n);
			}
			Collections.shuffle(listeValeurs);
			HashMap<Integer,Integer> map = new HashMap<Integer, Integer>();
			for (int n=0 ; n<maxIndex[i] ; n++){
				map.put(n, listeValeurs.get(n));
			}
			mapPermuteNumbers.add(map);
		}
	}

	public int[] increment(int[] _index, int[] _maxIndex) throws ChimiqueException{
		if (!firstRun){
			_index = randomToSorted(_index);
		}
		firstRun = false;
		int current = 0;
		_index[0] += 1;
		while(current<_index.length){
			if(_index[current]>=_maxIndex[current]){
				if(current+1>=_index.length){
					throw new ChimiqueException("Overflow détecté");
				}
				_index[current] = 0;
				_index[current+1]+=1;
			}
			current++;
		}
		_index = sortedToRandom(_index);

		return _index;
	}
	
	public int[] sortedToRandom(int[] tab){
		for (int i=0;i<tab.length;i++){
			tab[i]=mapPermuteNumbers.get(i).get(tab[i]);
		}
		return tab;
	}
	
	public int[] randomToSorted(int[] tab){
		
		for (int i=0;i<tab.length;i++){
			tab[i]=getKeyByValue(mapPermuteNumbers.get(i),tab[i]);
			if (tab[i]==123456789) System.out.println("haha ");
		}
		return tab;
	}
	
	public static Integer getKeyByValue(HashMap<Integer,Integer> map, Integer value) {
	    for (Entry<Integer,Integer> entry : map.entrySet()) {
	        if (value.equals(entry.getValue())) {
	            return entry.getKey();
	        }
	    }
	    return null;
	}
	
//	public static void main(String[] args){
//		int[] maxIndex = {2,4,3,5};
//		RandomIncrementStrategy inc = new RandomIncrementStrategy(maxIndex);
//		int[] index = {0,0,0,0};
//		boolean finished = false;
//		while (!finished){
//			try {
//				index = inc.increment(index,maxIndex);
//			} catch (ChimiqueException e) {
//				// TODO Auto-generated catch block
//				System.out.println("exception!");
//				finished = true;
//			}
//		}
//	
//	
//	}
}
