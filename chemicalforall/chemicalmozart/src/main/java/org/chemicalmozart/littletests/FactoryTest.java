package org.chemicalmozart.littletests;

import org.chemicalmozart.model.implementations.factory.MozartSolutionFactoryImpl;

import fr.insa.rennes.info.chemical.backend.Solution;

public class FactoryTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MozartSolutionFactoryImpl factory = new MozartSolutionFactoryImpl();
		Solution s = factory.createRythmicPull();

	}

}
