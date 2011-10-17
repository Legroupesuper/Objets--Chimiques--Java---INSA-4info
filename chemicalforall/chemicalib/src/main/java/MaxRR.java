
public class MaxRR implements ReactionRule{
	private Integer a;
	private Integer b;
	@Override
	public Object[] computeResult() {
		if(a>b)
			return new Integer[]{a};
		
		return new Integer[]{b};
	}

	@Override
	public boolean computeSelect() {
		// TODO Auto-generated method stub
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
