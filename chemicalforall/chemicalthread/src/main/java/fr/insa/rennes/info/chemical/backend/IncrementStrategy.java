package fr.insa.rennes.info.chemical.backend;


/**
 * <p>
 * This interface describes an increment strategy of an index provider. It implements a strategy 
 * design pattern, where the <em>context</em> is the index provider, and the target
 * algorithm is the <code>increment</code> function. This class, invisible to the library user,
 * is represented by an enum type in the {@link Solution} class.<br />
 * This increment strategy defines the way to iterate on the reagents of a solution
 * when processing to the research of reagents for a reaction rule. Basically, the 
 * two different strategies are <em>ordered</em> (first elements first), and <em>random</em>.<br /> 
 * Other strategies could be implemented, as round robin. 
 * </p>
 * <p>
 * This interface is used by the class {@link IndexProvider}, that passes its sub index provider
 * to the function {@link #increment(SubIndexProviderSolution)}.
 * </p>
 * @author Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 * @see IndexProvider
 * @see SubIndexProviderSolution
 */
interface IncrementStrategy {
	/**
	 * Increments the sub index provider's value by one. This function is supposed 
	 * to go on to the next combination of reagents of the specified {@link SubIndexProviderSolution} 
	 * object. 
	 * @param sipSol The sub index provider on a solution that has to be incremented.
	 * @throws ChemicalException
	 */
	public void increment(SubIndexProviderSolution sipSol) throws ChemicalException;
}
