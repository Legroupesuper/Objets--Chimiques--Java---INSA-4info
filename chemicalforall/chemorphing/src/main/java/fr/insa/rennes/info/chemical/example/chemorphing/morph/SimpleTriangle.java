package fr.insa.rennes.info.chemical.example.chemorphing.morph;

//A SimpleTriangle has a horizontal edge from v1 to v2, and
//v3 is above or below this edge. v1 is to the left (lesser x) of v2.

public class SimpleTriangle {
	protected int x1, x2, y;
	protected int x3, y3;

	SimpleTriangle(int xx1, int xx2, int yy12, int xx3, int yy3){
		x1 = xx1;
		x2 = xx2;
		y  = yy12;
		x3 = xx3;
		y3 = yy3;
	}

	public boolean test(){
		if (x1 <= x2){
			return (true);
		}
		return (false);
	}

	public boolean type1(){
		if (y3 > y){
			return (true);
		}
		return (false);
	}

	public boolean type2(){
		if (y3 < y){
			return (true);
		}
		return (false);
	}
}
