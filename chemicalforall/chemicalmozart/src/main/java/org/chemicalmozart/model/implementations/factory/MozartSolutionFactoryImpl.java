package org.chemicalmozart.model.implementations.factory;

import org.chemicalmozart.model.implementations.rythme.EEQ2;
import org.chemicalmozart.model.implementations.rythme.EEQQQ4;
import org.chemicalmozart.model.implementations.rythme.EQE2;
import org.chemicalmozart.model.implementations.rythme.EQP2;
import org.chemicalmozart.model.implementations.rythme.QEE2;
import org.chemicalmozart.model.implementations.rythme.QEEQQ4;
import org.chemicalmozart.model.implementations.rythme.QPE2;
import org.chemicalmozart.model.implementations.rythme.QQ2;
import org.chemicalmozart.model.implementations.rythme.QQEEQ4;
import org.chemicalmozart.model.implementations.rythme.QQQEE4;
import org.chemicalmozart.model.implementations.rythme.QQQQ4;
import org.chemicalmozart.model.implementations.solutionindentification.RythmePull;
import org.chemicalmozart.model.interfaces.factory.MozartSolutionFactory;

import fr.insa.rennes.info.chemical.backend.Solution;

public class MozartSolutionFactoryImpl implements MozartSolutionFactory{

	public Solution createRythmicPull() {
		Solution result = new Solution();
		result.add(new RythmePull());
		result.add(new EEQ2());
		result.add(new EEQQQ4());
		result.add(new EQE2());
		result.add(new EQP2());
		result.add(new QEE2());
		result.add(new QEEQQ4());
		result.add(new QPE2());
		result.add(new QQ2());
		result.add(new QQEEQ4());
		result.add(new QQQEE4());
		result.add(new QQQQ4());
		return result;
	}

}
