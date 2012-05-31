/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChLOE.

    ChLOE is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChLOE is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with ChLOE.  If not, see <http://www.gnu.org/licenses/>
*/
package fr.insa.rennes.info.chemical.backend;

import java.math.BigInteger;

import fr.insa.rennes.info.chemical.backend.Solution.Strategy;



/**
 * <p>
 * This class represents an index counter on a set of reagents of a given solution.
 * This class is used during the reagents research process (function {@link Solution#requestForParameters(fr.insa.rennes.info.chemical.user.ReactionRule)} ).
 * When searching for a certain set of reagents for a reaction rule (using the type of the reagent objects) in a given solution,
 * all possible set of reagents must be tested. The main purpose of this class is to ensure that EVERY possibility is considered.
 * </p>
 * <p>
 * The concept of index provider is detached from the notion of solution: internally, only integers are handled. These integers represent
 * the indexes pointing to reagents in a given solution. This class "provides" a set of index corresponding to a set of reagents in the solution.<br />
 * There is several ways to iterate over the reagents: ordered, random, round robin... (see {@link OrderedIncrementStrategy}, {@link RandomIncrementStrategy}).
 * This strategy of iteration is specified by {@link IncrementStrategy}, and the default is random (the reagents are selected in a random order).
 * </p>
 * <p>
 * With the concept of inner solutions, a reaction rule can fetch reagents in sub solutions (see {@link SubSolution}). Therefore, a structure is needed 
 * to iterate on reagents in the main solution on one hand, and on inner solutions and their own reagents in the other hand. This structure is realized
 * with the implementations of the interface {@link SubIndexProvider} (see the description of the class). 
 * This class basically handles a {@link SubIndexProviderSolution} object, that iterates over the main solution and does only very little by itself.
 * The term "main solution" is relative: we call "main" the solution in which was added the reaction rule that requested for reagents - and lead to 
 * the creation of this index provider.
 * </p>
 * 
 * @author Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 * @see IncrementStrategy
 * @see SubIndexProvider
 * @see SubIndexProviderElement
 * @see SubIndexProviderSolution
 */
class IndexProvider {
	/**
	 * The main {@link SubIndexProviderSolution}, iterating on the main solution. Actually
	 * does (almost) all the job.
	 */
	private SubIndexProviderSolution _subIndexProviderSub;
	/**
	 * The increment strategy, chosen by the user of the library. Can be random or ordered.
	 * @see IncrementStrategy
	 */
	private IncrementStrategy _strategy;
	/**
	 * Indicates if an overflow was reached.
	 */
	private boolean _overflowReached;
	
	/**
	 * Builds an index provider, with the specified initial sub index provider on a solution. 
	 * The default strategy is random.
	 * @param sipSol the initial (and fully built) sub index provider on a solution.
	 * @throws ChemicalException
	 * @see IncrementStrategy
	 */
	public IndexProvider(SubIndexProviderSolution sipSol) throws ChemicalException {
		this(sipSol, Strategy.RANDOM);
	}
	
	/**
	 * Builds an index provider, with the specified initial sub index provider on a solution and the chosen 
	 * increment strategy.
	 * @param ipss the initial (and fully built) sub index provider on a solution.
	 * @param s the chosen strategy.
	 * @throws ChemicalException
	 */
	public IndexProvider(SubIndexProviderSolution ipss, Strategy s) throws ChemicalException {
		super();
		_subIndexProviderSub = ipss;
		_overflowReached = false;
		
		if (s.equals(Strategy.ORDERED)){
			_strategy = new OrderedIncrementStrategy();
		}
		else {
			_strategy = new RandomIncrementStrategy(_subIndexProviderSub.getNumberOfIncrements());
		}
		
		//Initialize the sub index provider, and increment it until it reaches a valid state
		increment();
		/*_subIndexProviderSub.init();
		while(!_subIndexProviderSub.isValid()){
			increment();
			if(_overflowReached)
				throw new ChemicalException("It's not possible to initialize the IndexProvider");
		}*/
	}
	
	/**
	 * Returns the sub index provider on a solution contained in this index provider.
	 * @return the sub index provider on a solution contained in this index provider.
	 */
	public SubIndexProviderSolution getSubIndexProvider(){
		return _subIndexProviderSub;
	}
	
	
	/**
	 * Increments the index and gives the next set of index on reagents. Actually
	 * this functions only calls {@link IncrementStrategy#increment(SubIndexProviderSolution)} 
	 * on the {@link SubIndexProviderSolution} object.<br />
	 * @return the new sub index provider on a solution (with the new indexes).
	 */
	public SubIndexProviderSolution increment(){		
		//Loop until we reach a valid state or an overflow is detected
		do{
			try{
				_strategy.increment(_subIndexProviderSub);
			} catch (ChemicalException e) {
				_overflowReached = true;
				return null;
			}
		}while(!_subIndexProviderSub.isValid());
		
		return _subIndexProviderSub;
	}
	
	/**
	 * Returns a string description of the index provider.
	 * @return a string description of the index provider.
	 */
	public String toString(){
		return _subIndexProviderSub.toString();
	}
	
	/**
	 * Returns <code>true</code> if an overflow was reached.
	 * @return <code>true</code> if an overflow was reached.
	 */
	public boolean is_overflowReached(){
		return _overflowReached;
	}
	
	/**
	 * Returns the number of elements/reagents on which this index provider is iterating.<br />
	 * Note: this method returns a {@link BigInteger} because of the possibly great number of elements in the solutions.
	 * @return the number of elements/reagents on which this index provider is iterating
	 */
	public BigInteger getNumberOfElements(){
		return _subIndexProviderSub.getNumberOfElements();
	}
}