package fr.insa.rennes.info.chemical.backend;

import java.math.BigInteger;

public interface IndexProviderElement {
	public void init();
	public int getValue();
	public BigInteger getNumberOfElements();
	public boolean isValide();
	public void setValue(int v);
	/*
	 * Indicate if an overflow has been reached
	 */
	public boolean increment();
	public String toString();
}
