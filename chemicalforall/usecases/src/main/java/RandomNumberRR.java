
import fr.insa.rennes.info.chemical.user.ReactionRule;


public class RandomNumberRR implements ReactionRule {

	private Integer a;
	private Integer b;
	public Object[] computeResult() {

		return new Integer[]{a};
	}

	public boolean computeSelect() {
		return true;
	}
	public Integer getA() {
		return a;
	}

	public Integer getB() {
		return b;
	}

	public Multiplicity getMultiplicity() {
		// TODO Auto-generated method stub
		return Multiplicity.ONE_SHOT;
	}

	public void setA(Integer a) {
		this.a = a;
	}

	public void setB(Integer b) {
		this.b = b;
	}

}