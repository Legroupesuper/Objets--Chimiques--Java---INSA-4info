package indexproviderproof;

import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.Dontreact;
import fr.insa.rennes.info.chemical.user.ReactionRule;

public class DummyReactionSubSub implements ReactionRule {
	private SubSolution<SubSolutionElements> a;
	private SubSolution<SubSolutionElements> b;
	private SubSolution<SubSolution<SubSolutionElements>> c;
	//	private SubSolution<SubSolution<SubSolutionElements>> d;
	@Dontreact
	private int nbCombination;

	public DummyReactionSubSub() {

		this.a = new SubSolution<SubSolutionElements>();
		this.a.addType(String.class);
		this.a.addType(String.class);

		this.b = new SubSolution<SubSolutionElements>();
		this.b.addType(Integer.class);

		this.c = new SubSolution<SubSolution<SubSolutionElements>>();
		this.c.addType(String.class);

		//		this.d = new SubSolution<SubSolution<SubSolutionElements>>();
		//		this.d.addType(String.class);
		//		this.d.addType(Integer.class);

		this.nbCombination = 0;
	}

	public Object[] computeResult() {
		return null;
	}

	public boolean computeSelect() {
		this.nbCombination++;
		System.out.println("DummySub : Test a0 = "+this.a.getElements().get(0)+", a1 = "+this.a.getElements().get(1)+", b = "+this.b.getElements().get(0)+", c = "+this.c.getElements().get(0)+/*", d0 = "+this.d.getElements().get(0)+", d1 = "+this.d.getElements().get(1)+*/", combination #"+this.nbCombination);

		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return false;
	}

	public SubSolution<SubSolutionElements> getA() {
		return this.a;
	}

	public SubSolution<SubSolutionElements> getB() {
		return this.b;
	}

	public SubSolution<SubSolution<SubSolutionElements>> getC() {
		return this.c;
	}

	//	public SubSolution<SubSolution<SubSolutionElements>> getD() {
	//		return this.d;
	//	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}

	public void setA(SubSolution<SubSolutionElements> a) {
		this.a = a;
	}

	public void setB(SubSolution<SubSolutionElements> b) {
		this.b = b;
	}

	public void setC(SubSolution<SubSolution<SubSolutionElements>> c) {
		this.c = c;
	}

	//	public void setD(SubSolution<SubSolution<SubSolutionElements>> d) {
	//		this.d = d;
	//	}
}
