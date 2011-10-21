package fr.insa.rennes.info.chemical.backend;

import java.util.ArrayList;
import java.util.List;

import fr.insa.rennes.info.chemical.backend.Solution.Strategy;



/**
 * This class is used to obtain an index counter.
 * We use this class during the reactives research process to try every cases (every n-uplet) :
 * an index in the _index array corresponds to an index in the main HashMap
 * in the class Solution.
 * @author Cédric Andreolli, Chloé Boulanger, Olivier Cléro, Antoine Guellier, Sébastien Guilloux, Arthur Templé
 *
 */
public class IndexProvider {
	
	
	/**
	 * This table represents the maximum values of the indexes
	 */
	private int[] _maxIndex;
	/**
	 * This table represents the current value for the indexes
	 */
	private int[] _index;

	private boolean _overflowReached = false;

	/**
	 * This list represents sets of index which can not be equals
	 */
	private List<List<Integer>> _dependantIndexes;
	
	/**
	 * This IncrementStrategy represents the strategy used for the choice of reactives (design pattern strategy)
	 */
	private IncrementStrategy _incrementStrategy;

//	/**
//	 * Default constructor
//	 * @param maxIndex the table that represents the maximum values of the indexes
//	 * @throws ChimiqueException
//	 */
//	public IndexProvider(int[] maxIndex) throws ChimiqueException{
//		_maxIndex = maxIndex;
//		_dependantIndexes =  new ArrayList<List<Integer>>();
//		_index = new int[maxIndex.length];
//		_incrementStrategy = new RandomIncrementStrategy(_maxIndex);
//		//_incrementStrategy = new OrderedIncrementStrategy();
//		for(int i=0; i<_index.length; i++){
//			if(_maxIndex[i] == 0)
//				throw new ChimiqueException("Maximum index value is invalid : 0");
//			_index[i] = 0;
//		}
//	}
//
//	/**
//	 * Constructor with constraints
//	 * @param maxIndex the table that represents the maximum values of the indexes
//	 * @throws ChimiqueException
//	 */
//	public IndexProvider(List<List<Integer>> dependantIndexes, int[] maxIndex) throws ChimiqueException{
//		_maxIndex = maxIndex;
//		_index = new int[maxIndex.length];
//		_dependantIndexes = dependantIndexes;
//		_incrementStrategy = new RandomIncrementStrategy(_maxIndex);
//		//_incrementStrategy = new OrderedIncrementStrategy();
//		for(int i=0; i<_index.length; i++){
//			if(_maxIndex[i] == 0)
//				throw new ChimiqueException("Maximum index value is invalid : 0");
//			_index[i] = 0;
//		}
//		while(!checkDuplicate()){
//			increment();
//		}
//	}
	
	/**
	 * Default constructor with strategy choice
	 * @param maxIndex the table that represents the maximum values of the indexes
	 * @param s the strategy used for the choice of reactives (random or ordered)
	 * @throws ChimiqueException
	 */
	public IndexProvider(int[] maxIndex, Strategy s) throws ChimiqueException{
		_maxIndex = maxIndex;
		_dependantIndexes =  new ArrayList<List<Integer>>();
		_index = new int[maxIndex.length];
		if (s==Strategy.RANDOM){
			_incrementStrategy = new RandomIncrementStrategy(_maxIndex);
		}
		else {
			_incrementStrategy = new OrderedIncrementStrategy();
		}
		for(int i=0; i<_index.length; i++){
			if(_maxIndex[i] == 0)
				throw new ChimiqueException("Maximum index value is invalid : 0");
			_index[i] = 0;
		}
	}

	/**
	 * Constructor with constraints
	 * @param maxIndex the table that represents the maximum values of the indexes
	 * @param s the strategy used for the choice of reactives (random or ordered)
	 * @throws ChimiqueException
	 */
	public IndexProvider(List<List<Integer>> dependantIndexes, int[] maxIndex, Strategy s) throws ChimiqueException{
		_maxIndex = maxIndex;
		_index = new int[maxIndex.length];
		_dependantIndexes = dependantIndexes;
		if (s==Strategy.RANDOM){
			_incrementStrategy = new RandomIncrementStrategy(_maxIndex);
		}
		else {
			_incrementStrategy = new OrderedIncrementStrategy();
		}
		for(int i=0; i<_index.length; i++){
			if(_maxIndex[i] == 0)
				throw new ChimiqueException("Maximum index value is invalid : 0");
			_index[i] = 0;
		}
		while(!checkDuplicate()){
			increment();
		}
	}



	private boolean checkDuplicate(){
		for(List<Integer> l : _dependantIndexes){
			List<Integer> valuesIndexProvider = new ArrayList<Integer>();
			boolean isCurrentIndexValid = true;

			for(int n : l){

				if(valuesIndexProvider.contains(_index[n])){
					isCurrentIndexValid = false;
					break;
				}else{
					valuesIndexProvider.add(_index[n]);
				}
			}
			if(!isCurrentIndexValid){
				return false;
			}
		}
		return true;
	}


	/**
	 * Get the value of the given index
	 * @param i the given index number
	 * @return The value of i-th index of the counter
	 */
	public int  get(int i){
		return _index[i];
	}

	/**
	 * Increment the counter by 1 in a binary way.
	 * Index 0 is incremented until its max value is reached, then index 1
	 * is increased and index 0 is reset and etc
	 * @return Is still in the counter range
	 */
	public void increment(){

		do{
			try {
				_index = _incrementStrategy.increment(_index, _maxIndex);
			} catch (ChimiqueException e) {
				// TODO Auto-generated catch block
				_overflowReached = true;
				return;
			}
		}while(!checkDuplicate());
	}
	public boolean is_overflowReached() {
		return _overflowReached;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public String toString(){
		String result = "";
		for(int i : _index){
			result+= (i+" ");
		}
		return result;
	}
}
