/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of libchloe.

    libchloe is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    libchloe is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with libchloe.  If not, see <http://www.gnu.org/licenses/>
*/
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