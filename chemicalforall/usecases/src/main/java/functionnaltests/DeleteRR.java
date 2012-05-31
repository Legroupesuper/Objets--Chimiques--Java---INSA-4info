
package functionnaltests;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.user.ReactionRule;

public class DeleteRR implements ReactionRule{
	Solution _sol;
	
	public Solution get_sol() {
		return _sol;
	}

	public void set_sol(Solution _sol) {
		this._sol = _sol;
	}

	public Object[] computeResult() {
		return new Object[]{new DeleteRR()};
	}

	public boolean computeSelect() {
		// TODO Auto-generated method stub
		return _sol.size() == 0;
	}

	public Multiplicity getMultiplicity() {
		// TODO Auto-generated method stub
		return Multiplicity.ONE_SHOT;
	}

}