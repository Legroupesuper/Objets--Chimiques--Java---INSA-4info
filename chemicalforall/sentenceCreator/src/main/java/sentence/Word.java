package sentence;

public class Word {
	private String s;

	public Word(String s) {
		super();
		this.s = s;
	}

	@Override
	public String toString() {
		return s;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}
	

}
