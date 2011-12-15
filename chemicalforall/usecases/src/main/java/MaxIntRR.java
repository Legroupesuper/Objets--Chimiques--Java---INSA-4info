import fr.insa.rennes.info.chemical.user.ReactionRule;



public class MaxIntRR implements ReactionRule{
	private Integer a;
	private Integer b;
	public Object[] computeResult() {
		if(a>b)
			return new Object[]{a};

		return new Object[]{b,new ConcatRR()};
	}

	public boolean computeSelect() {
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
