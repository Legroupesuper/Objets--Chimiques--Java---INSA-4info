package fr.insa.rennes.info.chemical.backend;

/**
 * This class represents a simple pair of objects.
 * @author Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 * 
 * @param <A> Type of the first element of the pair.
 * @param <B> Type of the second element of the pair.
 */
class Pair<A, B> {
	/**
	 * First element of the pair.
	 */
	private A _first;
	/**
	 * Second element of the pair.
	 */
	private B _second;
	
	/**
	 * Constructs a pair of 2 elements.
	 * @param _first The first element.
	 * @param _second The second element.
	 */
	public Pair(A _first, B _second) {
		super();
		this._first = _first;
		this._second = _second;
	}
	
	/**
	 * Getter for the first element of the pair
	 * @return The first element of the pair
	 */
	public A get_first() {
		return _first;
	}
	
	/**
	 * Setter for the first element of the pair
	 * @param _first The new value of the first element of the pair
	 */
	public void set_first(A _first) {
		this._first = _first;
	}
	
	/**
	 * Getter for the second element of the pair
	 * @return The second element of the pair
	 */
	public B get_second() {
		return _second;
	}
	
	/**
	 * Setter for the second element of the pair
	 * @param _second The new value of the second element of the pair
	 */
	public void set_second(B _second) {
		this._second = _second;
	}	
}


