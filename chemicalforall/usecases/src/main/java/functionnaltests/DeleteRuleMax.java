package functionnaltests;

import java.util.ArrayList;
import java.util.List;

import fr.insa.rennes.info.chemical.backend.Dontputback;
import fr.insa.rennes.info.chemical.backend.ElementList;
import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.user.ReactionRule;

public class DeleteRuleMax implements ReactionRule{
	@Dontputback
	SubSolution<ElementList> _subSol;
	
	public DeleteRuleMax() {
		super();
		ElementList e = new ElementList();
		List<Class<? extends Object>> l = new ArrayList<Class<? extends Object>>();
		l.add(Integer.class);
		l.add(Integer.class);
		_subSol = new SubSolution<ElementList>(e);
		_subSol.setTypeList(l);
	}

	public SubSolution<ElementList> get_subSol() {
		return _subSol;
	}

	public void set_subSol(SubSolution<ElementList> _subSol) {
		this._subSol = _subSol;
	}

	public Object[] computeResult() {
		List<Object> l = _subSol.getElementList();
		int a = (Integer) l.get(1);
		int b = (Integer) l.get(2);
		if(a<b){
			return new Object[]{b};
		}
		return new Object[]{a};
	}

	public boolean computeSelect() {
		return true;
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}

}
