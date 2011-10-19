
public class ChimiqueException extends Exception {
	String _s;
	public ChimiqueException(String s) {
		// TODO Auto-generated constructor stub
		_s = s;
	}
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		
		return _s + super.getMessage();
		
	}
	
//AYON!
}
