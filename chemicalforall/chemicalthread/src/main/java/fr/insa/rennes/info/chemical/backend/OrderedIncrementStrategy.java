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
class OrderedIncrementStrategy implements IncrementStrategy {
	/**
	 * This interface is used to get an association between a real index and a false index.
	 * It's designed to work with a List<IndexProviderElement>
	 * @author Cédric Andreolli, Chloé Boulanger, Olivier Cléro, Antoine Guellier, Sébastien Guilloux, Arthur Templé
	 *
	 */
	/**
	 * A boolean needed for the first execution of increment
	 */
	private BigInteger _currentIndex;
	private BigInteger _numberOfIndex;
	
	/**
	 * Sole constructor, with the maxIndex array that is needed to know how many 
	 * columns there is for this index provider.
	 * @param maxIndex The maximum index array
	 */
	public OrderedIncrementStrategy(BigInteger maxI){
		_currentIndex = BigInteger.valueOf(0);
		_numberOfIndex = maxI;
	}
	
	/**
	 * The function is pretty much the same as the one in the OrderedIncrementStrategy class
	 * but it "translates" the ordered value to the "random" value with the map
	 * @param _index The index array: the values of the index
	 * @param _maxIndex The maximum values of the indexes
	 * @return A table of integers that is the new value of the indexes
	 * @throws ChemicalException
	 */	
	public void increment(SubIndexProviderSolution sipSol) throws ChemicalException{
		boolean overflow = sipSol.increment();
		
		if(overflow)
			throw new ChemicalException("Overflow reached.");
	}

}

