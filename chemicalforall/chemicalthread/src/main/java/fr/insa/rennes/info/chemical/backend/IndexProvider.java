package fr.insa.rennes.info.chemical.backend;

import java.math.BigInteger;

import fr.insa.rennes.info.chemical.backend.Solution.Strategy;



/**
 * <p>
 * This class represents an index counter on set of reactives of a given solution.
 * This class is used during the reactives research process (function {@link Solution#requestForParameters(fr.insa.rennes.info.chemical.user.ReactionRule)} ).
 * When searching for a certain set of reactives for a reaction rule (using the type of the reactives objects) in a given solution,
 * all possible set of reactives must be tested. The main purpose of this class is to ensure that EVERY possibility is considered.
 * </p>
 * <p>
 * The concept of index provider is detached from the notion of solution: internally, only integers are handled. These integers represent
 * the index of the reactives in a given solution. This class "provide" a set of index, corresponding to a set of reactives in the solution.<br />
 * There is several ways to iterate over the reactives: ordered, random, round robin... (see {@link OrderedIncrementStrategy}, {@link RandomIncrementStrategy}).
 * This strategy of iteration is specified by {@link IncrementStrategy}, and the default is random (the reactives are selected in a random order).
 * </p>
 * <p>
 * With the concept of inner solutions, a reaction rule can fetch reactives in sub solutions (see {@link SubSolution}). Therefore, a structure is needed 
 * to iterate over reactives in the main solution on one hand, and inner solutions and their reactives in the other hand. This structure is realized
 * with the implementations of the interface {@link SubIndexProvider} (see the description of the class). 
 * This class basically handle a {@link SubIndexProviderSolution} object, iterating over the main solution (the solution
 * in which the reaction rule have been inserted) and does only very little by itself.
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
	
	/**
	 * Builds an index provider, with the specified initial sub index provider on a solution. 
	 * The default strategy is random.
	 * @param ipss the initial (and fully built) sub index provider on a solution.
	 * @throws ChemicalException
	 * @see IncrementStrategy
	 */
	public IndexProvider(SubIndexProviderSolution ipss) throws ChemicalException {
		this(ipss, Strategy.RANDOM);
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
			_strategy = new RandomIncrementStrategy(_subIndexProviderSub.getNumberOfElements());
		}
		
		//Initialize the sub index provider, and increment it until it reaches a valid state
		_subIndexProviderSub.init();
		while(!_subIndexProviderSub.isValid()){
			increment();
			if(_overflowReached)
				throw new ChemicalException("It's not possible to create the IndexProvider");
		}
	}
	
	/**
	 * Returns the sub index provider on a solution contained in this index provider.
	 * @return the sub index provider on a solution contained in this index provider.
	 */
	public SubIndexProviderSolution getSubIndexProvider(){
		return _subIndexProviderSub;
	}
	
	
	/**
	 * Increments the index and gives the next set of index on reactives. Actually
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
	 * Returns the number of elements/reactives on which this index provider is iterating.<br />
	 * Note: this method returns a {@link BigInteger} because of the possibly great number of elements in the solutions.
	 * @return the number of elements/reactives on which this index provider is iterating
	 */
	public BigInteger getNumberOfElements(){
		return _subIndexProviderSub.getNumberOfElements();
	}
}
