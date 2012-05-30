/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of libchloe.

    libchloe is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    libchloe is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with libchloe.  If not, see <http://www.gnu.org/licenses/>
*/
package org.chemicalmozart.utils;

import java.io.IOException;
import java.io.Writer;

import javax.naming.spi.DirStateFactory.Result;
import javax.sound.midi.InvalidMidiDataException;

import org.chemicalmozart.viewV2.MainView;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.backend.Utils;
import fr.insa.rennes.info.chemical.user.Dontreact;
import fr.insa.rennes.info.chemical.user.InertEvent;
import fr.insa.rennes.info.chemical.user.InertEventListener;
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
	
	
	@Dontreact private MainView _view;
	
	public SolutionWriterRR(MainView view) {
		_resultSolution = new SubSolution<SubSolutionElements>();
		_resultSolution.addType(org.chemicalmozart.model.implementations.solutionindentification.Result.class);
		_view = view;
	}
	
	public Object[] computeResult() {
		Solution sol = _resultSolution.getSolution();
		sol.add(_writer);
		sol.addInertEventListener(new InertEventListener() {
			
			public void isInert(InertEvent e) {
				Utils.logger.info("On a fait ce truc de merde");
				Utils.logger.info(""+e.getSource());
				System.out.println("C'est fini!!!");
				try {
					_writer.writeFile();
					_view.endOfReaction();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (InvalidMidiDataException e1) {
					e1.printStackTrace();
				}
			}
		});
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