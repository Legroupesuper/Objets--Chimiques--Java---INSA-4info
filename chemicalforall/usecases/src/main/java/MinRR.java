import fr.insa.rennes.info.chemical.user.ReactionRule;


public class MinRR implements ReactionRule {
	private Position posA;
	private Position posB;

	public Object[] computeResult() {
		if(posA.calculeNorme()<posB.calculeNorme()) return new Object[]{posA};
		else return new Object[]{posB};
	}

	public boolean computeSelect() {
		return true;
	}
	public Multiplicity getMultiplicity() {
		// TODO Auto-generated method stub
		return Multiplicity.ONE_SHOT;
	}
	public Position getPosA() {
		return posA;
	}

	public Position getPosB() {
		return posB;
	}

	public int getShotType() {
		return 1;
	}

	public void setPosA(Position posA) {
		this.posA = posA;
	}

	public void setPosB(Position posB) {
		this.posB = posB;
	}

	@Override
	public String toString(){
		return "MinRR";
	}

}
