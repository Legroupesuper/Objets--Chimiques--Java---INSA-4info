package org.chemicalmozart.utils;

import java.io.Writer;

import javax.naming.spi.DirStateFactory.Result;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.ReactionRule;

public class SolutionWriterRR implements ReactionRule{
	/**
	 * The writer object used to export in midi
	 */
	private MusicWriter _writer;
	/**
	 * The result solution
	 */
	private SubSolution<SubSolutionElements> _resultSolution;
	
	public SolutionWriterRR() {
		_resultSolution = new SubSolution<SubSolutionElements>();
		_resultSolution.addType(org.chemicalmozart.model.implementations.solutionindentification.Result.class);
	}
	
	public Object[] computeResult() {
		Solution sol = _resultSolution.getSolution();
		sol.add(_writer);
		sol.add(new StartToWriteRR());
		sol.add(new GetWriterRR());
		return new Object[]{};
	}

	/**
	 * @return the _writer
	 */
	public MusicWriter get_writer() {
		return _writer;
	}

	/**
	 * @param _writer the _writer to set
	 */
	public void set_writer(MusicWriter _writer) {
		this._writer = _writer;
	}

	/**
	 * @return the _resultSolution
	 */
	public SubSolution<SubSolutionElements> get_resultSolution() {
		return _resultSolution;
	}

	/**
	 * @param _resultSolution the _resultSolution to set
	 */
	public void set_resultSolution(SubSolution<SubSolutionElements> _resultSolution) {
		this._resultSolution = _resultSolution;
	}

	public boolean computeSelect() {
		System.out.println("On passe dans le cmpute select");
		return true;
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.ONE_SHOT;
	}

}
