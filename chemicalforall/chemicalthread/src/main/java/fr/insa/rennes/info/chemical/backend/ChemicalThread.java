package fr.insa.rennes.info.chemical.backend;


import java.util.Arrays;

import fr.insa.rennes.info.chemical.user.ReactionRule;
import fr.insa.rennes.info.chemical.user.ReactionRule.Multiplicity;



/**
 * Implementation of the class Thread, for our use only
 * @author Jean-Paul Sartre, Raymond Aron, Emmanuel Kant
 *
 */
public class ChemicalThread extends Thread {
	private ReactionRule _reactionRule;
	private Solution _solutionContainer;
	private boolean _continue = true;

	/**
	 * Constructor for ChemicalThread
	 * @param r The ReactionRule to be processed by this thread
	 * @param s The Solution where the ReactionRule acts
	 * @param g The ThreadGroup of this Thread (for super constructor)
	 * @see Thread
	 */
	public ChemicalThread(ReactionRule r, Solution s, ThreadGroup g){
		super(g, r.getClass().getName());
		_reactionRule = r;
		_solutionContainer = s;
	}

	/**
	 * Getter for the ReactionRule of this ChemicalThread
	 * @return the ReactionRule of the ChemicalThread
	 */
	public ReactionRule get_reactionRule() {
		return _reactionRule;
	}

	/**
	 * Getter for the Solution of this ChemicalThread
	 * @return the Solution of the ChemicalThread
	 */
	public Solution get_solutionContainer() {
		return _solutionContainer;
	}

	@Override
	public void run() {
		//Run as long as the solution is not inert AND as long
		//as we didn't stop the thread "manually"
		while(!_solutionContainer.is_inert() && _continue){
			//If we find enough valid parameters...
			if(_solutionContainer.requestForParameters(_reactionRule)){
				//...we compute reaction result
				Object obj[] = _reactionRule.computeResult();
				//add the results to the solution
				if(obj != null)
					_solutionContainer.addAll(Arrays.asList(obj));
				
				//Then wake all ReactionRules (as the solution has been modified)
				_solutionContainer.wakeAll();
				
				//If the reaction rule is ONE SHOT, we must delete it and stop this thread
				if(_reactionRule.getMultiplicity().equals(Multiplicity.ONE_SHOT)){
					_solutionContainer.deleteReaction(_reactionRule);
					break;
				}
			}else{
				//If we do not find valid parameters, the reaction goes to sleep
				//A sleeping reaction waits for the solution to go inert or to see its inner elements modified
				_solutionContainer.makeSleep();
			}
		}
	}

	/**
	 * Setter for ReactionRule
	 * @param _reactionRule the ReactionRule to set to our ChemicalThread
	 */
	public void set_reactionRule(ReactionRule _reactionRule) {
		this._reactionRule = _reactionRule;
	}

	/**
	 * Setter for Solution
	 * @param _solutionContainer the Solution to set to our ChemicalThread
	 */
	public void set_solutionContainer(Solution _solutionContainer) {
		this._solutionContainer = _solutionContainer;
	}

	public void stopTheThread(){
		_continue  = false;
	}
}
