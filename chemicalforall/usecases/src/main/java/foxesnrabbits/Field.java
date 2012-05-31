
package foxesnrabbits;


import java.util.LinkedList;
import java.util.List;

public class Field {
	private int N;
	private int M;
	
	private Cell[][] _cells;
	private View view;
	
	
	
	public Field(int n, int m) {
		view = null;
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
        for(int roffset = -1; roffset <= 1; roffset++) {
            int nextRow = x + roffset;
            if(nextRow >= 0 && nextRow < N) {
                for(int coffset = -1; coffset <= 1; coffset++) {
                    int nextCol = y + coffset;
                    if(nextCol >= 0 && nextCol < M && (roffset != 0 || coffset != 0)) {
                        
                    	if(_cells[nextRow][nextCol].isFree())
                    		return _cells[nextRow][nextCol];
                    	
                    }
                }
            }
        }
        
        return null;
	}
	
	public int getNbRows(){
		return N;
	}
	
	
	public int getNbColumns(){
		return M;
	}
	
	public Cell getCell(int x, int y) {
		if(x < 0 || x >= N || y < 0 || y >= M) {
			return null;
		}
		
		return _cells[x][y];
	}
	
	public List<Cell> getCellList() {
		List<Cell> result = new LinkedList<Cell>();
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++)
				result.add(_cells[i][j]);
		}
		
		return result;
	}
	
	public void setView(View v){
		view = v;
	}

	
	public void update(){
		view.update();
	}

	public boolean isViewReady() {
		return view != null;
	}
}