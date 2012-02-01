import fr.insa.rennes.info.chemical.user.ReactionRule;



public class ConcatRR implements ReactionRule{
	private String s;
	private String t;
	public Object[] computeResult() {
		return new String[]{s+t};
	}
	public boolean computeSelect() {
		return true;
	}
	public Multiplicity getMultiplicity() {
		// TODO Auto-generated method stub
		return Multiplicity.INFINITY_SHOT;
	}

	public String getS() {
		return s;
	}

	public String getT() {
		return t;
	}

	public void setS(String s) {
		this.s = s;
	}

	public void setT(String t) {
		this.t = t;
	}

}
