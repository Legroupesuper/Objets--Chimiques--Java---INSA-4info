package fr.insa.rennes.info.chemical.backend;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * This class implements an random strategy iteration on reactives: 
 * the elements are randomly selected in order to simulate the non-determinism 
 * of a chemical reaction.
 * @author Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 */
class RandomIncrementStrategy implements IncrementStrategy {
	/**
	 * The current index (corresponding to an "ordered" index)
	 */
	private BigInteger _currentIndex;
	/**
	 * The maximum value of the index
	 */
	private BigInteger _maxIndex;
	/**
	 * The list that transforms the "normal" (ordered) index in a random index.
	 * It will be used with {@link #_currentIndex} to translate it to a "random" index:
	 * randomIndex = _shuffleList.get(_currentIndex)
	 */
	private List<BigInteger> _shuffleList;
	
	/**
	 * Sole constructor, with the maxIndex array that is needed to know how many 
	 * columns there is for this index provider.
	 * @param maxI The maximum index array
	 */
	public RandomIncrementStrategy(BigInteger maxI){
		_currentIndex = BigInteger.valueOf(0);
		_maxIndex = maxI;
		_shuffleList = new ArrayList<BigInteger>();
		
		for(BigInteger i= BigInteger.valueOf(0); _maxIndex.add(i.negate()).signum()>0; i=i.add(BigInteger.valueOf(1))){
			_shuffleList.add(new BigInteger(i.toByteArray()));
		}
		
		//Then shuffle the list
		Collections.shuffle(_shuffleList);
	}
	
	/**
	 * Increments the specified sub index provider of one step. Does not check
	 * the validity of the index provider, this is done in {@link IndexProvider#increment()}.
	 * This function basically process to the same operation as {@link OrderedIncrementStrategy#increment(SubIndexProviderSolution)},
	 * but it "translates" the ordered value to the "random" value with the list {@link #_shuffleList}.
	 * @param sipSol The sub index provider on solution that has to be incremented.
	 * @throws ChemicalException
	 */	
	public void increment(SubIndexProviderSolution sipSol) throws ChemicalException{
		//At every increment, we start all over again...
		sipSol.init();
		
		Iterator<BigInteger> it = _shuffleList.iterator();
		BigInteger position = BigInteger.valueOf(0);
		BigInteger i = BigInteger.valueOf(0);
		
		if(_currentIndex.equals(_maxIndex))
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
		
		//...and we increment n times the sub index provider
		//n being the value of _shuffleList.get(_currentValue)
		for(i=BigInteger.valueOf(0); position.add(i.negate()).signum()>0; i=i.add(BigInteger.valueOf(1))){
			sipSol.increment();
		}
	}

}
