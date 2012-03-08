import java.util.ArrayList;
import java.util.List;

import fr.insa.rennes.info.chemical.user.Dontreact;
import fr.insa.rennes.info.chemical.user.ReactionRule;



public class MaxIntRR implements ReactionRule{

	private Integer a;
	private Integer b;
	@Dontreact
	private Integer c;
	
	public Integer getC() {
		return c;
	}

	public void setC(Integer c) {
		this.c = c;
	}

//	private SubSolution<SubSolution<SubSolutionElements>> sol1;
//	
//	public SubSolution<SubSolution<SubSolutionElements>> getSol1() {
//		return sol1;
//	}
//
//	public void setSol1(SubSolution<SubSolution<SubSolutionElements>> sol1) {
//		this.sol1 = sol1;
//	}

	public MaxIntRR() {
//		SubSolutionElements e = new SubSolutionElements();
//		SubSolution<SubSolutionElements> e1 = new SubSolution<SubSolutionElements>(e);
//		sol1 = new SubSolution<SubSolution<SubSolutionElements>>(e1);
//		List<Class<? extends Object>> l = new ArrayList<Class<? extends Object>>();
//		l.add(Integer.class);
//		l.add(String.class);
//		sol1.setTypeList(l);
		c = 0;
	}
	
	public MaxIntRR(int val) {
		this();
		c = val;
	}
	public Object[] computeResult() {
		if(a>b)
			return new Object[]{a};

		return new Object[]{b};
	}

	public boolean computeSelect() {
		return a>c && b>c;
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
