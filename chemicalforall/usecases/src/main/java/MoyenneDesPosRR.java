import fr.insa.rennes.info.chemical.user.ReactionRule;


public class MoyenneDesPosRR implements ReactionRule {

	private Position posA;
	private Position posB;

	public Object[] computeResult() {
		int moyX = (int) (posA.getX()+posB.getX())/2;
		int moyY = (int) (posA.getY()+posB.getY())/2;
		return new Object[]{new Position(moyX,moyY)};
	}

	public boolean computeSelect() {
		return true;
	}
	
	public ReactionRule.Multiplicity getShotType() {
		
		return ReactionRule.Multiplicity.N_SHOT;
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
		return "MoyenneDesPosRR";
	}

}
