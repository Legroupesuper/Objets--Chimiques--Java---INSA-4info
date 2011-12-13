import fr.insa.rennes.info.chemical.user.ReactionRule;



public class TrucRR implements ReactionRule {
	private Integer i;
	private String s;

	public Object[] computeResult() {
		return new Object[] {s.length()+i};
	}

	public boolean computeSelect() {
		return true;
	}

	public int getI() {
		return i;
	}

	public String getS() {
		return s;
	}

	public void setI(int i) {
		this.i = i;
	}

	public void setS(String s) {
		this.s = s;
	}


}
