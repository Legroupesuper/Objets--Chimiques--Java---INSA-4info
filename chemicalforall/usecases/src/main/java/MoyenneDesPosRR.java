
import fr.insa.rennes.info.chemical.user.ReactionRule;


public class MoyenneDesPosRR implements ReactionRule {

	private Position posA;
	private Position posB;

	public Object[] computeResult() {
		int moyX = (posA.getX()+posB.getX())/2;
		int moyY = (posA.getY()+posB.getY())/2;
		return new Object[]{new Position(moyX,moyY)};
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

	public ReactionRule.Multiplicity getShotType() {

		return ReactionRule.Multiplicity.ONE_SHOT;
	}

	public void setPosA(Position posA) {
		this.posA = posA;
	}

	public void setPosB(Position posB) {
		this.posB = posB;
	}

	@Override
	public String toString(){
		return "MoyenneDesPosRR";
	}

}