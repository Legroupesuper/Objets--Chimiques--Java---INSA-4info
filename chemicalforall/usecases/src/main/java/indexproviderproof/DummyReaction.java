
package indexproviderproof;

import fr.insa.rennes.info.chemical.user.Dontreact;
import fr.insa.rennes.info.chemical.user.ReactionRule;

public class DummyReaction implements ReactionRule {
	@Dontreact
	private int nbCombination;
	
	private Integer a;
	private Integer b;
	private Integer c;
	
	public DummyReaction() {
		nbCombination = 0;
	}
	
	public Object[] computeResult() {
		return null;
	}

	public boolean computeSelect() {
		nbCombination++;
		System.out.println("Dummy 1 : Test a = "+a+", b = "+b+", c = "+c+", combination #"+nbCombination);
		
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

	public void setA(Integer a) {
		this.a = a;
	}

	public void setB(Integer b) {
		this.b = b;
	}

	public void setC(Integer c) {
		this.c = c;
	}
	
	
}