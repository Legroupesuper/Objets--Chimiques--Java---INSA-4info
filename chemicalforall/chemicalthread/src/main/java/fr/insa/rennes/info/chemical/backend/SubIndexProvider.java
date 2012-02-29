package fr.insa.rennes.info.chemical.backend;

import java.math.BigInteger;

interface SubIndexProvider {
	public void init();
	public int getValue();
	public BigInteger getNumberOfElements();
	public boolean isValid();
	public void setValue(int v);
	/*
	 * Indicate if an overflow has been reached
	 */
	public boolean increment();
	public String toString();
}
