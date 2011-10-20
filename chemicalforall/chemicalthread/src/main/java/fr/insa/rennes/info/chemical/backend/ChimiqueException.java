package fr.insa.rennes.info.chemical.backend;


public class ChimiqueException extends Exception {
	String _s;
	public ChimiqueException(String s) {
		_s = s;
	}
	@Override
	public String getMessage() {
		return _s + super.getMessage();

	}


}
