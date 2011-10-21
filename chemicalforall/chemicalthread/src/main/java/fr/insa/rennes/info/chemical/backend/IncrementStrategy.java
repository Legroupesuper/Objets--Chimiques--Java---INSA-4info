package fr.insa.rennes.info.chemical.backend;

/*
 * Strategy design pattern
 * For the choice of the reactives in the solution
 * Only the increment method needs to be reimplemented
 */

public interface IncrementStrategy {

	public int[] increment(int[] _index, int[] _maxIndex) throws ChimiqueException;
}
