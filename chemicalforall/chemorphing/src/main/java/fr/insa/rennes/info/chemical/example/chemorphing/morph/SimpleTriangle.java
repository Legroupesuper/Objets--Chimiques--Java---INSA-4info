package fr.insa.rennes.info.chemical.example.chemorphing.morph;

//A SimpleTriangle has a horizontal edge from v1 to v2, and
//v3 is above or below this edge. v1 is to the left (lesser x) of v2.

public class SimpleTriangle {
	int x1, x2, y;
	int x3, y3;

	SimpleTriangle(int X1, int X2, int Y, int X3, int Y3){
		x1 = X1;
		x2 = X2;
		y  = Y;
		x3 = X3;
		y3 = Y3;
	}

	public boolean test(){
		if (x1 <= x2)
			return (true);

		return (false);
	}

	public boolean type1(){
		if (y3 > y)
			return (true);

		return (false);
	}

	public boolean type2(){
		if (y3 < y)
			return (true);

		return (false);
	}
}
