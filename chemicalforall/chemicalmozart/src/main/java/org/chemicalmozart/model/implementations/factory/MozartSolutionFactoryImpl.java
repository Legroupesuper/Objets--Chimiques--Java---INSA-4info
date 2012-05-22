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
import org.chemicalmozart.model.implementations.rythme.RythmPattern;
import org.chemicalmozart.model.implementations.solutionindentification.RythmePull;
import org.chemicalmozart.model.interfaces.factory.MozartSolutionFactory;

import fr.insa.rennes.info.chemical.backend.Solution;

public class MozartSolutionFactoryImpl implements MozartSolutionFactory{

	public Solution createRythmicPull() {
		Solution result = new Solution();
		result.add(new RythmePull());
		
		EEQ2 eeq2 = new EEQ2();
		result.add(new RythmPattern(eeq2.getDuration(), eeq2.getListNotes()));
		
		EEQQQ4 eeqqq4 = new EEQQQ4();
		result.add(new RythmPattern(eeqqq4.getDuration(), eeqqq4.getListNotes()));
		
		EQE2 eqe2 = new EQE2();
		result.add(new RythmPattern(eqe2.getDuration(), eqe2.getListNotes()));
		
		EQP2 eqp2 = new EQP2();
		result.add(new RythmPattern(eqp2.getDuration(), eqp2.getListNotes()));
		
		QEE2 qee2 = new QEE2();
		result.add(new RythmPattern(qee2.getDuration(), qee2.getListNotes()));
		
		QEEQQ4 qeeqq4 = new QEEQQ4();
		result.add(new RythmPattern(qeeqq4.getDuration(), qeeqq4.getListNotes()));
		
		QPE2 qpe2 = new QPE2();
		result.add(new RythmPattern(qpe2.getDuration(), qpe2.getListNotes()));
		
		QQ2 qq2 = new QQ2();
		result.add(new RythmPattern(qq2.getDuration(), qq2.getListNotes()));
		
		QQEEQ4 qqeeq4 = new QQEEQ4();
		result.add(new RythmPattern(qqeeq4.getDuration(), qqeeq4.getListNotes()));
		
		QQQEE4 qqqee4 = new QQQEE4();
		result.add(new RythmPattern(qqqee4.getDuration(), qqqee4.getListNotes()));
		
		QQQQ4 qqqq4 = new QQQQ4();
		result.add(new RythmPattern(qqqq4.getDuration(), qqqq4.getListNotes()));
		
		return result;
	}

}