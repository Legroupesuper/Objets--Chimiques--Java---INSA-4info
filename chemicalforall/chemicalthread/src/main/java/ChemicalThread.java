import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;


public class ChemicalThread extends Thread {
	private ReactionRule _reactionRule;
	private Solution _solutionContainer;
	
	public ReactionRule get_reactionRule() {
		return _reactionRule;
	}

	public void set_reactionRule(ReactionRule _reactionRule) {
		this._reactionRule = _reactionRule;
	}

	public Solution get_solutionContainer() {
		return _solutionContainer;
	}

	public void set_solutionContainer(Solution _solutionContainer) {
		this._solutionContainer = _solutionContainer;
	}

	public ChemicalThread(ReactionRule r, Solution s){
		_reactionRule = r;
		_solutionContainer = s;
		//this.start();
	}
	
	@Override
	public void run() {
		while(_solutionContainer.getContinuerReaction()){
			try {
				if(_solutionContainer.requestForParameters(_reactionRule)){
					Object obj[] = _reactionRule.computeResult();
					_solutionContainer.addAll(Arrays.asList(obj));
					//System.out.println(_solutionContainer.toString());
					_solutionContainer.wakeAll();
				}else{
					_solutionContainer.makeSleep();
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
