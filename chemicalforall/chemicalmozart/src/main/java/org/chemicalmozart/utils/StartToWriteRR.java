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
	
}
