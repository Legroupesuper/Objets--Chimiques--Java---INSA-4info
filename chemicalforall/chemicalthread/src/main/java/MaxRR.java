
public class MaxRR implements ReactionRule{
	private Integer a;
	private Integer b;
	public Object[] computeResult() {
		if(a>b)
			return new Integer[]{a};
		
		return new Integer[]{b};
	}

	public boolean computeSelect() {
		boolean ok;
		ok = a!=1 && a != 2 && b !=1 && b !=2; 
		return true;
	}
	
	
	public Integer getA() {
		return a;
	}

	public void setA(Integer a) {
		this.a = a;
	}

	public Integer getB() {
		return b;
	}

	public void setB(Integer b) {
		this.b = b;
	}

}
