import fr.insa.rennes.info.chemical.user.ReactionRule;


public class MaxPosRR implements ReactionRule {
	private Position posA;
	private Position posB;

	public Object[] computeResult() {
		if(posA.calculeNorme()>posB.calculeNorme()) return new Object[]{posA};
		else return new Object[]{posB};
	}

	public boolean computeSelect() {
		return true;//(posA.getX()%2 == 0);
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

	public void setPosA(Position posA) {
		this.posA = posA;
	}

	public void setPosB(Position posB) {
		this.posB = posB;
	}

	@Override
	public String toString(){
		return "MaxRR";
	}

}