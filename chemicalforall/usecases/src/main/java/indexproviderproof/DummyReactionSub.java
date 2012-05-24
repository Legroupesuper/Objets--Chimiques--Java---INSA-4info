package indexproviderproof;

import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.Dontreact;
import fr.insa.rennes.info.chemical.user.ReactionRule;

public class DummyReactionSub implements ReactionRule {
	@Dontreact
	private int nbCombination;
	
	private SubSolution<SubSolutionElements> a;
	private SubSolution<SubSolutionElements> b;
	
	public DummyReactionSub() {
		
		a = new SubSolution<SubSolutionElements>();
		a.addType(String.class);
		a.addType(String.class);
		
		b = new SubSolution<SubSolutionElements>();
		b.addType(Integer.class);
		
		nbCombination = 0;
	}
	
	public Object[] computeResult() {
		return null;
	}

	public boolean computeSelect() {
		nbCombination++;
		System.out.println("DummySub : Test a0 = "+a.getElements().get(0)+", a1 = "+a.getElements().get(1)+", b = "+b.getElements().get(0)+", combination #"+nbCombination);
		//System.out.println("b = "+b.getElements().get(0)+", combination #"+nbCombination);
		
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}

	public void setA(SubSolution<SubSolutionElements> a) {
		this.a = a;
	}

	public void setB(SubSolution<SubSolutionElements> b) {
		this.b = b;
	}

	public SubSolution<SubSolutionElements> getA() {
		return a;
	}

	public SubSolution<SubSolutionElements> getB() {
		return b;
	}
}
