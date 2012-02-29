package fr.insa.rennes.info.chemical.backend;


import java.lang.reflect.InvocationTargetException;
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
		//TODO Following line looks irrelevant... What about that ?
		//		_solutionContainer.firstSleep(_reactionRule.getClass().getName());

		//Run as long as the solution is not inert
		while(!_solutionContainer.is_inert() && _continue){
			try {
				//If we find enough valid parameters...
				if(_solutionContainer.requestForParameters(_reactionRule)){
					//...we compute reaction result
					Object obj[] = _reactionRule.computeResult();
					//add the results to the solution
					if(obj != null)
						_solutionContainer.addAll(Arrays.asList(obj));
					//If the reaction rule is ONE SHOT, we must delete it !
					if(_reactionRule.getMultiplicity()==Multiplicity.ONE_SHOT){
						_solutionContainer.deleteReaction(_reactionRule);
					}
					//then wake all ReactionRules (as the solution has been modified)
					_solutionContainer.wakeAll();
				}else{
					//If we do not find valid parameters, the reaction goes to sleep
					//A sleeping reaction waits for the solution to go inert or to see its inner elements modified
					_solutionContainer.makeSleep();
				}
				//TODO let us have a rethink about Exception handling ! (Some / all of those could be handled previously)
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
