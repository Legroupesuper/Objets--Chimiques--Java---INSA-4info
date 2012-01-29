import java.util.ArrayList;
import java.util.List;

import fr.insa.rennes.info.chemical.backend.ChemicalElement;
import fr.insa.rennes.info.chemical.backend.ElementList;
import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.user.ReactionRule;



public class MaxIntRR implements ReactionRule{
	private Integer a;
	private Integer b;
//	private SubSolution<SubSolution<ElementList>> sol1;
//	
//	public SubSolution<SubSolution<ElementList>> getSol1() {
//		return sol1;
//	}
//
//	public void setSol1(SubSolution<SubSolution<ElementList>> sol1) {
//		this.sol1 = sol1;
//	}

	public MaxIntRR() {
//		ElementList e = new ElementList();
//		SubSolution<ElementList> e1 = new SubSolution<ElementList>(e);
//		sol1 = new SubSolution<SubSolution<ElementList>>(e1);
//		List<Class<? extends Object>> l = new ArrayList<Class<? extends Object>>();
//		l.add(Integer.class);
//		l.add(String.class);
//		sol1.setTypeList(l);
	}
	
	public Object[] computeResult() {
		System.out.println("On tente fait");
		if(a>b)
			return new Object[]{a};

		return new Object[]{b,new ConcatRR()};
	}

	public boolean computeSelect() {
		System.out.println("On tente");
		boolean ok;
		ok = a!=1 && a != 2 && b !=1 && b !=2;
		return ok;
	}


	public Integer getA() {
		return a;
	}

	public Integer getB() {
		return b;
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}

	public void setA(Integer a) {
		this.a = a;
	}

	public void setB(Integer b) {
		this.b = b;
	}

}
