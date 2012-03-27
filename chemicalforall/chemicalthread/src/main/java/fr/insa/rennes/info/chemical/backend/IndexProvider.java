package fr.insa.rennes.info.chemical.backend;

import java.math.BigInteger;

import fr.insa.rennes.info.chemical.backend.Solution.Strategy;



/**
 * <p>
 * This class represents an index counter on set of reactives of a given solution.
 * This class is used during the reactives research process (function {@link Solution#requestForParameters(fr.insa.rennes.info.chemical.user.ReactionRule)} ).
 * When searching for a certain set of reactives for a reaction rule (using the type of the reactives objects),
 * all possible set of object must be tested. The main purpose of this class is to ensure that EVERY
 * one of these possibilities is considered.
 * </p>
 * <p>
 * c'est fait exprès qu'il soit détaché de la solution, c'est un concept à part
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
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
	
	public IndexProvider(SubIndexProviderSolution solution) throws ChemicalException {
		this(solution, Strategy.RANDOM);
	}
	
	public IndexProvider(SubIndexProviderSolution ipss, Strategy s) throws ChemicalException {
		super();
		_subIndexProviderSub = ipss;
		_overflowReached = false;
		
		if (s.equals(Strategy.ORDERED)){
			_strategy = new OrderedIncrementStrategy(_subIndexProviderSub.getNumberOfElements());
		}
		else {
			_strategy = new RandomIncrementStrategy(_subIndexProviderSub.getNumberOfElements());
		}
		
		_subIndexProviderSub.init();
		while(!_subIndexProviderSub.isValid()){
			increment();
			if(_overflowReached)
				throw new ChemicalException("It's not possible to create the IndexProvider");
		}
	}
	
	public SubIndexProviderSolution getSubIndexProvider(){
		return _subIndexProviderSub;
	}
	
	
	
	public SubIndexProviderSolution increment(){		
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
	
	
	public String toString(){
		return _subIndexProviderSub.toString();
	}
	
	public boolean is_overflowReached(){
		return _overflowReached;
	}
	
	public BigInteger getNumberOfElements(){
		return _subIndexProviderSub.getNumberOfElements();
	}
}
