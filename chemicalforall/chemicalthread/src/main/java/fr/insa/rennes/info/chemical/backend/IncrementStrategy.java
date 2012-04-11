package fr.insa.rennes.info.chemical.backend;


/**
 * This interface describes an increment strategy of an index provider. Implements a strategy 
 * design pattern, where the <em>context</em> is the index provider, and the target
 * algorithm is the <code>increment</code> function. This class, invisible to the library user,
 * is represented by an enum type in the {@link Solution} class.<br />
 * This increment strategy defines the way to iterate on the reactives of a solution
 * when processing to the research of reactives for a reaction rule. Basically, the 
 * two different strategies are <em>ordered</em> (first elements first), and <em>random</em>.<br /> 
 * Other strategies could be implemented, as a round robin. 
 * @author Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 */
interface IncrementStrategy {
	/**
	 * Increments the sub index provider's value. This function is supposed 
	 * to go on to the next combination of reactives of the specified {@link SubIndexProviderSolution} 
	 * object. 
	 * @param sipSol The sub index provider on a solution that has to be incremented.
	 * @throws ChemicalException
	 */
	public void increment(SubIndexProviderSolution sipSol) throws ChemicalException;
}
