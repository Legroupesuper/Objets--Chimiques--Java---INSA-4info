package fr.insa.rennes.info.chemical.backend;



/**
 * This class implements an ordered strategy iteration on reactives: 
 * the elements are selected in order of appearance in the the solution. 
 * Note that this order can be different than the insert order of the elements in the solution.
 * @author Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 */
class OrderedIncrementStrategy implements IncrementStrategy {
	/**
	 * Increments the specified sub index provider of one step. Does not check
	 * the validity of the index provider, this is done in {@link IndexProvider#increment()}.
	 * @param sipSol The sub index provider on solution that has to be incremented
	 * @throws ChemicalException
	 */	
	public void increment(SubIndexProviderSolution sipSol) throws ChemicalException{
		boolean overflow = sipSol.increment();
		
		if(overflow)
			throw new ChemicalException("Overflow reached.");
	}

}

