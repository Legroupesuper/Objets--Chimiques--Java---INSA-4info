
public class ConcatRR implements ReactionRule{
	private String s;
	private String t;
	public Object[] computeResult() {
		return new String[]{s+t};
	}

	public boolean computeSelect() {
		// TODO Auto-generated method stub
		return true;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

}
