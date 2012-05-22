/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChemicalLibSuper.

    ChemicalLibSuper is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChemicalLibSuper is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with ChemicalLibSuper.  If not, see <http://www.gnu.org/licenses/>
*/
package fr.insa.rennes.info.chemical.backend;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * This class implements an random strategy iteration on reagents: 
 * the elements are randomly selected in order to simulate the non-determinism 
 * of a chemical reaction.
 * @author Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 */
class RandomIncrementStrategy implements IncrementStrategy {
	/**
	 * The current number of increments already done.
	 */
	private BigInteger _currentIndex;
	/**
	 * The maximum increments that the sub index provider can do.
	 */
	private BigInteger _maxIndex;
	/**
	 * The list that transforms the "normal" (ordered) index in a random index.
	 * It will be used with {@link #_currentIndex} to translate it to a "random" index:
	 * randomIndex = _shuffleList.get(_currentIndex)
	 */
	private List<BigInteger> _shuffleList;
	
	/**
	 * Sole constructor, with the maxI that is needed to know the
	 * maximum increments that the sub index provider can do.
	 * @param maxI The maximum index
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