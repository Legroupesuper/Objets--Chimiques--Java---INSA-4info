package fr.insa.rennes.info.chemical.backend;


/**
 * ChemicalException is an exception that indicates an error inherent to the chemical library.<br />
 * This exception can be thrown for an internal error, but the user will mostly receive exception concerning
 * a mishandling of the library. 
 * 
 * 
 * @author Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 */
public class ChemicalException extends Exception {
	/**
	 * Perpetual version ID, avoid endless warnings...
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a ChemicalException with a detail message. 
	 * @param s The detail message
	 */
	public ChemicalException(String s) {
		super(s);
	}

}
