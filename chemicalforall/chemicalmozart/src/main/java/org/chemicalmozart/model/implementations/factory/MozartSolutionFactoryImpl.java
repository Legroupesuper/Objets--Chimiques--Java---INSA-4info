/* 
	Copyright (C) 2012 Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChemicalLibSuper.

    ChemicalLibSuper is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChemicalLibSuper is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with ChemicalLibSuper.  If not, see <http://www.gnu.org/licenses/>
*/
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