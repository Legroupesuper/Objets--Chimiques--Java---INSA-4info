package fr.insa.rennes.info.chemical.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;


/**
 * This class implements an random strategy: the elements are randomly selected in order
 * to simulate a chemical reaction.
 * @author Cédric Andreolli, Chloé Boulanger, Olivier Cléro, Antoine Guellier, Sébastien Guilloux, Arthur Templé
 */
class RandomIncrementStrategy implements IncrementStrategy {

	/**
	 * List giving to each index a hashmap 
	 * that associates a random value to the "normal" value
	 */
	ArrayList<HashMap<Integer, Integer>> mapPermuteNumbers; 
	/**
	 * A boolean needed for the first execution of increment
	 */
	boolean firstRun = true;
	
	/**
	 * Sole constructor, with the maxIndex array that is needed to know how many 
	 * columns there is for this index provider.
	 * @param maxIndex The maximum index array
	 */
	public RandomIncrementStrategy(int[] maxIndex){
		mapPermuteNumbers= new ArrayList<HashMap<Integer,Integer>>(maxIndex.length);
		
		//Construct the permutation map, index by index
		for (int i=0; i< maxIndex.length ; i++ ){
			//Construct the list 0, 1, 2, 3, ...
			List<Integer> listeValeurs = new ArrayList<Integer>();
			for (int n=0; n< maxIndex[i];n++){
				listeValeurs.add(n);
			}
			
			//Shuffle this list
			Collections.shuffle(listeValeurs);
			
			//Reassociate 0 to the 0th value of the first list, 1 to the 1st value, ...
			HashMap<Integer,Integer> map = new HashMap<Integer, Integer>();
			for (int n=0 ; n<maxIndex[i] ; n++){
				map.put(n, listeValeurs.get(n));
			}
			
			mapPermuteNumbers.add(map);
		}
	}
	
	/**
	 * The function is pretty much the same as the one in the OrderedIncrementStrategy class
	 * but it "translates" the ordered value to the "random" value with the map
	 * @param _index The index array: the values of the index
	 * @param _maxIndex The maximum values of the indexes
	 * @return A table of integers that is the new value of the indexes
	 * @throws ChimiqueException
	 */
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
	
	/*
	 * Takes the indexes array with the "normal"/ordered values and translates it to the 
	 * random values with map
	 */
	private int[] sortedToRandom(int[] tab){
		for (int i=0;i<tab.length;i++){
			tab[i]=mapPermuteNumbers.get(i).get(tab[i]);
		}
		return tab;
	}
	
	/*
	 * Do the opposite of sortedToRandom
	 */
	private int[] randomToSorted(int[] tab){
		
		for (int i=0;i<tab.length;i++){
			tab[i]=getKeyByValue(mapPermuteNumbers.get(i),tab[i]);
			if (tab[i]==123456789) System.out.println("haha ");
		}
		return tab;
	}
	
	private  Integer getKeyByValue(HashMap<Integer,Integer> map, Integer value) {
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
