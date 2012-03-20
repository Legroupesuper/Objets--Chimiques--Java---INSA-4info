package fr.insa.rennes.info.chemical.backend;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * This class implements an random strategy iteration on reactives: 
 * the elements are randomly selected in order to simulate the non-determinism 
 * of a chemical reaction.
 * @author Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 */
class RandomIncrementStrategy implements IncrementStrategy {
	/**
	 * This interface is used to get an association between a real index and a false index.
	 * It's designed to work with a List<IndexProviderElement>
	 * @author Cédric Andreolli, Chloé Boulanger, Olivier Cléro, Antoine Guellier, Sébastien Guilloux, Arthur Templé
	 *
	 */
	/**
	 * A boolean needed for the first execution of increment
	 */
	boolean firstRun = true;
	private BigInteger _currentIndex;
	private BigInteger _numberOfIndex;
	private List<BigInteger> _shuffleList;
	/**
	 * Sole constructor, with the maxIndex array that is needed to know how many 
	 * columns there is for this index provider.
	 * @param maxIndex The maximum index array
	 */
	public RandomIncrementStrategy(SubIndexProviderSolution sipSol){
		_currentIndex = BigInteger.valueOf(0);
		_numberOfIndex = sipSol.getNumberOfElements();
		_shuffleList = new ArrayList<BigInteger>();
		
		for(BigInteger i= BigInteger.valueOf(0); _numberOfIndex.add(i.negate()).signum()>0; i=i.add(BigInteger.valueOf(1))){
			_shuffleList.add(new BigInteger(i.toByteArray()));
		}
		Collections.shuffle(_shuffleList);
	}
	
	/**
	 * The function is pretty much the same as the one in the OrderedIncrementStrategy class
	 * but it "translates" the ordered value to the "random" value with the map
	 * @param _index The index array: the values of the index
	 * @param _maxIndex The maximum values of the indexes
	 * @return A table of integers that is the new value of the indexes
	 * @throws ChemicalException
	 */	
	public void increment(SubIndexProviderSolution solution) throws ChemicalException{
		solution.init();
		
		java.util.Iterator<BigInteger> it = _shuffleList.iterator();
		BigInteger position = BigInteger.valueOf(0);
		BigInteger i = BigInteger.valueOf(0);
		
		if(_currentIndex.equals(_numberOfIndex))
			throw new ChemicalException("Overflow reached first");
		
		//We can't use the simple _shuffleList.get(_currentIndex) because we need to use BigInteger
		while(it.hasNext()){
			if(i.equals(_currentIndex)){
				_currentIndex = _currentIndex.add(BigInteger.valueOf(1));
				position = it.next();
				break;
			}
			
			i=i.add(BigInteger.valueOf(1));
			it.next();
		}
		
		
		for(i=BigInteger.valueOf(0); position.add(i.negate()).signum()>0; i=i.add(BigInteger.valueOf(1))){
			solution.increment();
		}
	}

}
