/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of chemicalmozart.

    chemicalmozart is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    chemicalmozart is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with chemicalmozart.  If not, see <http://www.gnu.org/licenses/>
*/

package org.chemicalmozart.utils;

import org.chemicalmozart.model.implementations.BarNumber;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.Dontreact;
import fr.insa.rennes.info.chemical.user.ReactionRule;

public class StartToWriteRR implements ReactionRule {
	private MusicWriter _writer;
	
	@Dontreact private int _barNumber;
	
	private SubSolution<SubSolutionElements> _barSubSol;
	
	public StartToWriteRR() {
		_barNumber = 0;
		_barSubSol = new SubSolution<SubSolutionElements>();
		_barSubSol.addType(BarNumber.class);
	}

	public MusicWriter get_writer() {
		return _writer;
	}

	public void set_writer(MusicWriter _writer) {
		this._writer = _writer;
	}

	public int get_barNumber() {
		return _barNumber;
	}

	public void set_barNumber(int _barNumber) {
		this._barNumber = _barNumber;
	}

	public SubSolution<SubSolutionElements> get_barSubSol() {
		return _barSubSol;
	}

	public void set_barSubSol(SubSolution<SubSolutionElements> _barSubSol) {
		this._barSubSol = _barSubSol;
	}

	public Object[] computeResult() {
		_barNumber++;
		Solution sol = _barSubSol.getSolution();
		sol.add(_barSubSol.getElements().get(0));
		sol.add(_writer);
		sol.add(new WriteMelodyRR());
		sol.add(new WriteChordRR());
		return new Object[]{};
	}

	public boolean computeSelect() {
		BarNumber bn = (BarNumber) _barSubSol.getElements().get(0);
		return _barNumber == bn.getValue();
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
}