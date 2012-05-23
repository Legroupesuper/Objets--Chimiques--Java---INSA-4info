/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

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
package fr.insa.rennes.info.chemical.backend;


import java.util.Arrays;

import fr.insa.rennes.info.chemical.user.ReactionRule;
import fr.insa.rennes.info.chemical.user.ReactionRule.Multiplicity;



/**
 * This class executes a reaction, it uses a reaction rule object and its 
 * {@link ReactionRule#computeSelect()} and {@link ReactionRule#computeResult()} functions
 * to process to reactions. Each reaction rule in a solution is given a chemical thread that will execute its
 * reaction according to its specifications (reagents, products, ...).
 * @author Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 *
 */
public class ChemicalThread extends Thread {
	/**
	 * The reaction rule that has to be executed by this thread.
	 */
	private ReactionRule _reactionRule;
	/**
	 * The solution in which the reaction rule is placed.
	 */
	private Solution _solutionContainer;
	/**
	 * This boolean is used in the infinite loop (function run), and modified by {@link #stopTheThread()}.
	 */
	private boolean _continue;

	/**
	 * Builds a ChemicalThread object.
	 * @param r The ReactionRule to be processed by this thread
	 * @param s The solution in which the reaction rule acts
	 * @param g The ThreadGroup of this Thread (for super constructor)
	 */
	public ChemicalThread(ReactionRule r, Solution s, ThreadGroup g){
		super(g, r.getClass().getName());
		_reactionRule = r;
		_solutionContainer = s;
		_continue = true;
	}

	/**
	 * The main function of the thread, that loops until the solution is inert or until the thread is
	 * manually stopped with {@link #stopTheThread()}.
	 * The loop begins by calling {@link Solution#requestForParameters(ReactionRule)}. If the function succeeds, 
	 * the result of the reaction is computed ({@link ReactionRule#computeResult()}) and the products of the 
	 * reaction is re-inserted in the solution. Finally, it wakes all the chemical threads in the solution. If the 
	 * reaction rule is one-shot, this thread stops and the reaction rule object is deleted from the solution.
	 * In case where {@link Solution#requestForParameters(ReactionRule)} fails, {@link Solution#makeSleep()} is called
	 * in order to wait until new reagents are added to the solution or until the solution becomes inert.
	 * @see ReactionRule.Multiplicity
	 */
	@Override
	public void run() {
		//Run as long as the solution is not inert AND as long
		//as we didn't stop the thread "manually"
		while(!_solutionContainer.is_inert() && _continue){
			Utils.logger.info("Un tour de boucle "+_reactionRule);
			//If we find enough valid parameters...
			if(_solutionContainer.requestForParameters(_reactionRule)){
				Utils.logger.info("On a passé le computeSelect");
				//...we compute reaction result
				Object obj[] = _reactionRule.computeResult();
				boolean b = false;
				//add the results to the solution
				if(obj != null)
					b = _solutionContainer.addAll(Arrays.asList(obj));
				//Then wake all ReactionRules (as the solution has been modified)
				//_solutionContainer.wakeAll();
				//If the reaction rule is ONE SHOT, we must delete it and stop this thread
				if(_reactionRule.getMultiplicity().equals(Multiplicity.ONE_SHOT) && obj!=null &&  !Arrays.asList(obj).contains(_reactionRule)){
					_solutionContainer.deleteReaction(_reactionRule);
					_solutionContainer.wakeAll();
					Utils.logger.info("C'était une One-shot");
					break;
				}
			}else{
				//If we do not find valid parameters, the reaction goes to sleep
				//A sleeping reaction waits for the solution to go inert or to see its inner elements modified
				Utils.logger.info("On fait faire dodo à "+_reactionRule);
				_solutionContainer.makeSleep();
			}
		}
		Utils.logger.info("On sort");
	}
	
	/**
	 * Stops the infinite loop in the run() function.
	 */
	public void stopTheThread(){
		_continue  = false;
	}
	
	@Override
	public String toString() {
		return "Je suis le thread "+_reactionRule;
	}
}
