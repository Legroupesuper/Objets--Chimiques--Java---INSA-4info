
import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.Dontreact;
import fr.insa.rennes.info.chemical.user.ReactionRule;



public class MaxIntSubSolRR implements ReactionRule{

	private SubSolution<SubSolution<SubSolutionElements>> a;
	@Dontreact
	private Integer c;
	
	public Integer getC() {
		return c;
	}

	public void setC(Integer c) {
		this.c = c;
	}


	public MaxIntSubSolRR() {
		/*SubSolutionElements ea = new SubSolutionElements();
		List<Class<? extends Object>> la = new ArrayList<Class<? extends Object>>();
		la.add(Integer.class);
		la.add(Integer.class);
		ea.setTypeList(la);
		
		SubSolution<SubSolutionElements> sa = new SubSolution<SubSolutionElements>(ea);*/
		a = new SubSolution<SubSolution<SubSolutionElements>>();
		a.addType(Integer.class);
		a.addType(Integer.class);
		
		/*SubSolutionElements eb = new SubSolutionElements();
		List<Class<? extends Object>> lb = new ArrayList<Class<? extends Object>>();
		lb.add(Integer.class);
		eb.setTypeList(lb);
		//SubSolution<SubSolutionElements> sb = new SubSolution<SubSolutionElements>(eb);
		b = new SubSolution<SubSolutionElements>(eb);*/
		
//		List<Object> lla = new ArrayList<Object>();
//		lla.add(50);lla.add(50);
//		a.setElements(lla);
//		
//		List<Object> llb = new ArrayList<Object>();
//		llb.add(12);llb.add(12);
//		a.setElements(llb);
		
//		System.out.println(a.getElements());
		
		c = 0;
	}

	public Object[] computeResult() {
		int inta = (Integer)a.getElements().get(0);
		int intb = (Integer)a.getElements().get(1);
		
		System.out.println("J'ai a = "+inta+", b = "+intb);
		if(inta > intb)
			return new Object[]{inta};

		return new Object[]{intb};
	}

	public boolean computeSelect() {
		return true;
	}


	public SubSolution<SubSolution<SubSolutionElements>> getA() {
		return a;
	}

//	public SubSolution<SubSolutionElements> getB() {
//		return b;
//	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}

	public void setA(SubSolution<SubSolution<SubSolutionElements>> a) {
		this.a = a;
	}

//	public void setB(SubSolution<SubSolutionElements> b) {
//		this.b = b;
//	}

}