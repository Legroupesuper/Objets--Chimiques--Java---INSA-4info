
package indexproviderproof;

import fr.insa.rennes.info.chemical.user.Dontreact;
import fr.insa.rennes.info.chemical.user.ReactionRule;

public class DummyReaction2 implements ReactionRule {
	@Dontreact
	private int nbCombination;
	
	private String a;
	private Integer b;
	
	public DummyReaction2() {
		nbCombination = 0;
	}
	
	public Object[] computeResult() {
		return null;
	}

	public boolean computeSelect() {
		nbCombination++;
		System.out.println("Dummy2 : Test a = "+a+", b = "+b+", combination #"+nbCombination);
		
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

	public void setA(String a) {
		this.a = a;
	}

	public void setB(Integer b) {
		this.b = b;
	}
}