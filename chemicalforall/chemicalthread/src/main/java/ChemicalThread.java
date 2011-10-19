import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;


public class ChemicalThread extends Thread {
	private ReactionRule _reactionRule;
	private Solution _solutionContainer;

	public ChemicalThread(ReactionRule r, Solution s, ThreadGroup g){
		super(g, r.getClass().getName());
		_reactionRule = r;
		_solutionContainer = s;
	}

	public ReactionRule get_reactionRule() {
		return _reactionRule;
	}

	public Solution get_solutionContainer() {
		return _solutionContainer;
	}

	@Override
	public void run() {
		_solutionContainer.firstSleep(_reactionRule.getClass().getName());
		while(_solutionContainer.getContinuerReaction()){
			try {
				if(_solutionContainer.requestForParameters(_reactionRule)){
					Object obj[] = _reactionRule.computeResult();
					_solutionContainer.addAll(Arrays.asList(obj));
					//System.out.println(_solutionContainer.toString());
					_solutionContainer.wakeAll();
				}else{
					_solutionContainer.makeSleep(_reactionRule.getClass().getName());
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

	public void set_reactionRule(ReactionRule _reactionRule) {
		this._reactionRule = _reactionRule;
	}

	public void set_solutionContainer(Solution _solutionContainer) {
		this._solutionContainer = _solutionContainer;
	}
}
