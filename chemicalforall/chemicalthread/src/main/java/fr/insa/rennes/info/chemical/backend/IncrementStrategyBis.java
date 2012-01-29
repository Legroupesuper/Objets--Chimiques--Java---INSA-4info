package fr.insa.rennes.info.chemical.backend;

import java.util.List;

/**
 * Strategy design pattern
 * For the choice of the reactives in the solution
 * Only the increment method needs to be reimplemented
 * @author Cédric Andreolli, Chloé Boulanger, Olivier Cléro, Antoine Guellier, Sébastien Guilloux, Arthur Templé
 */
interface IncrementStrategyBis {
	public List<List<Integer>> increment(List<IndexProviderElement> list) throws ChemicalException;
}
