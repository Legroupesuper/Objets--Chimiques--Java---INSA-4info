
public class Position {
	private int x;
	private int y;
	
	public Position(int a, int b) {
		super();
		this.x = a;
		this.y = b;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int a) {
		this.x = a;
	}
	public int getY() {
		return y;
	}
	public void setY(int b) {
		this.y = b;
	}
	
	public double calculeNorme(){
		return Math.sqrt((double)(x*x + y*y));
	}

	public String toString() {
		return "("+String.valueOf(x)+", "+String.valueOf(y)+")";
	}
	
	public boolean equals(Object obj){
		if(!(obj instanceof Position))
			return false;
		Position pos = (Position)obj;
		return pos.x == this.x && pos.y == this.y;
	}
}
