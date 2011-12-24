package fr.insa.rennes.info.chemical.example.chemorphing.morph;


import fr.insa.rennes.info.chemical.example.chemorphing.morph.SimpleTriangle;

public class Triangle {
	private int            x1, y1;
	private int            x2, y2;
	private int            x3, y3;
	protected SimpleTriangle t1 = null;
	protected SimpleTriangle t2 = null;

	Triangle(int xx1, int yy1, int xx2, int yy2, int xx3, int yy3){
		// sort vertices by y coordinate

		if (yy1 <= yy2){
			if (yy2 <= yy3){
				// order v1, v2, v3
				x1 = xx1;
				y1 = yy1;
				x2 = xx2;
				y2 = yy2;
				x3 = xx3;
				y3 = yy3;
				return;
			}

			if (yy1 <= yy3){
				// order v1, v3, v2
				x1 = xx1;
				y1 = yy1;
				x2 = xx3;
				y2 = yy3;
				x3 = xx2;
				y3 = yy2;
				return;
			}

			// order v3, v1, v2
			x1 = xx3;
			y1 = yy3;
			x2 = xx1;
			y2 = yy1;
			x3 = xx2;
			y3 = yy2;
			return;
		}

		if (yy1 <= yy3){
			// order v2, v1, v3
			x1 = xx2;
			y1 = yy2;
			x2 = xx1;
			y2 = yy1;
			x3 = xx3;
			y3 = yy3;
			return;
		}

		if (yy2 <= yy3){
			// order v2, v3, v1
			x1 = xx2;
			y1 = yy2;
			x2 = xx3;
			y2 = yy3;
			x3 = xx1;
			y3 = yy1;
			return;
		}

		// order v3, v2, v1
		x1 = xx3;
		y1 = yy3;
		x2 = xx2;
		y2 = yy2;
		x3 = xx1;
		y3 = yy1;
	}

	public boolean test(){
		return ((y1 == y2) || (y2 == y3) || (y1 == y3));
	}

	public void convert(){
		double m; /* dx/dy */
		int    x;

		m = (double)(x3 - x1) / (double)(y3 - y1);
		x = x1 + (int)Math.round(m * (double)(y2 - y1));

		if (x2 <= x)
		{
			t1 = new SimpleTriangle(x2, x, y2, x3, y3);
			t2 = new SimpleTriangle(x2, x, y2, x1, y1);
			return;
		}

		t1 = new SimpleTriangle(x, x2, y2, x3, y3);
		t2 = new SimpleTriangle(x, x2, y2, x1, y1);
	}
}
