import java.util.ArrayList;
import java.util.List;

import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.ReactionRule;
import fr.insa.rennes.info.chemical.user.ReactionRule.Multiplicity;



public class ConcatSubSolRR implements ReactionRule{
	private SubSolution<SubSolution<SubSolutionElements>> a;
	
	public ConcatSubSolRR() {
		SubSolutionElements ea = new SubSolutionElements();
		List<Class<? extends Object>> la = new ArrayList<Class<? extends Object>>();
		la.add(String.class);
		la.add(String.class);
		ea.setTypeList(la);
		
		SubSolution<SubSolutionElements> sa = new SubSolution<SubSolutionElements>(ea);
		a = new SubSolution<SubSolution<SubSolutionElements>>(sa);
	}
	
	public Object[] computeResult() {
		String s = (String)a.getElements().get(0);
		String t = (String)a.getElements().get(1);
		
		System.out.println("J'ai s = "+s+", t = "+t);
		
		return new String[]{s+" "+t};
	}
	public boolean computeSelect() {
		return true;
	}
	public SubSolution<SubSolution<SubSolutionElements>> getA() {
		return a;
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}

	public void setA(SubSolution<SubSolution<SubSolutionElements>> a) {
		this.a = a;
	}

}
