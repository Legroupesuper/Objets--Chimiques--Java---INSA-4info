package foxesnrabbits;

import java.util.Random;

public class Field {
	private static int N = 20;
	private static int M = 20;
	private static final Random rand = new Random();
	
	private Cell[][] _cells;
	
	public Field(int n, int m) {
		N = n;
		M = m;
		_cells = new Cell[N][M];
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				_cells[i][j] = new Cell(this, i, j);
			}
		}
	}
	
	public Cell findFreeAdjacentCell(int x, int y) {
		int nextX = x + rand.nextInt(3) - 1;
        int nextY = y + rand.nextInt(3) - 1;
        
        return null;
	}
}
