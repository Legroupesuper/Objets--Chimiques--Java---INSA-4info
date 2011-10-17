package morph;


import morph.SimpleTriangle;

public class Triangle {
	int            x1, y1;
	int            x2, y2;
	int            x3, y3;
	SimpleTriangle t1 = null;
	SimpleTriangle t2 = null;

	Triangle(int X1, int Y1, int X2, int Y2, int X3, int Y3){
		// sort vertices by y coordinate

		if (Y1 <= Y2){
			if (Y2 <= Y3){
				// order v1, v2, v3
				x1 = X1;
				y1 = Y1;
				x2 = X2;
				y2 = Y2;
				x3 = X3;
				y3 = Y3;
				return;
			}

			if (Y1 <= Y3){
				// order v1, v3, v2
				x1 = X1;
				y1 = Y1;
				x2 = X3;
				y2 = Y3;
				x3 = X2;
				y3 = Y2;
				return;
			}

			// order v3, v1, v2
			x1 = X3;
			y1 = Y3;
			x2 = X1;
			y2 = Y1;
			x3 = X2;
			y3 = Y2;
			return;
		}

		if (Y1 <= Y3){
			// order v2, v1, v3
			x1 = X2;
			y1 = Y2;
			x2 = X1;
			y2 = Y1;
			x3 = X3;
			y3 = Y3;
			return;
		}

		if (Y2 <= Y3){
			// order v2, v3, v1
			x1 = X2;
			y1 = Y2;
			x2 = X3;
			y2 = Y3;
			x3 = X1;
			y3 = Y1;
			return;
		}

		// order v3, v2, v1
		x1 = X3;
		y1 = Y3;
		x2 = X2;
		y2 = Y2;
		x3 = X1;
		y3 = Y1;
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
