package fr.insa.rennes.info.chemical.backend;

/*
 * Our very own exception class
 */
public class ChemicalException extends Exception {
	/**
	 * Perpetual version ID, avoid endless warnings...
	 */
	private static final long serialVersionUID = 1L;
	
	String _s;
	public ChemicalException(String s) {
		_s = s;
	}
	@Override
	public String getMessage() {
		return _s + super.getMessage();

	}


}