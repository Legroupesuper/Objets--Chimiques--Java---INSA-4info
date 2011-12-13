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
	
	public Position getPosA() {
		return posA;
	}

	public void setPosA(Position posA) {
		this.posA = posA;
	}

	public Position getPosB() {
		return posB;
	}

	public void setPosB(Position posB) {
		this.posB = posB;
	}
	
	public String toString(){
		return "MaxRR";
	}

}