package sentence;

import java.util.ArrayList;
import java.util.List;

import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.Dontuse;
import fr.insa.rennes.info.chemical.user.ReactionRule;
import fr.insa.rennes.info.chemical.user.ReactionRule.Multiplicity;

public class ChooseSubject implements ReactionRule{
	
	/*
	 * ChooseSubject prend un élément dans la solution qui contient l'identificateur SubjectType
	 */

	private SubSolution<SubSolutionElements> _subSol;
	
	public ChooseSubject() {
		super();
		SubSolutionElements e = new SubSolutionElements();
		_subSol = new SubSolution<SubSolutionElements>(e);
		List<Class<? extends Object>> l = new ArrayList<Class<? extends Object>>();
		l.add(SubjectType.class);
		_subSol.setTypeList(l);
	}
	

	public Object[] computeResult() {
		String s = (String)_subSol.getElements().get(0);

		//int a = (int)Math.random()*2; //mets au pluriel avec 50% de chances
		//if(a==1)
		//	s += "s";
		//
		return new Object[]{s};
	}

	@Dontuse
	public boolean computeSelect() {
		return false;
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.ONE_SHOT;
	}

}
