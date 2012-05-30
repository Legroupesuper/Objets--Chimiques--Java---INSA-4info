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


public class Cell {
	private int _x;
	private int _y;
	
	private Field _field;
	private Animal _animal;
	
	public Cell(Field field, int i, int j, Animal animal) {
		_field = field;
		_x = i;
		_y = j;
		_animal = animal;
	}
	
	public Cell(Field field, int i, int j) {
		this(field, i, j, null);
	}
	
	public int getX() {
		return _x;
	}

	public int getY() {
		return _y;
	}

	public Animal getAnimal() {
		return _animal;
	}
	
	public void setAnimal(Animal animal) {
		_animal = animal;
		if (_field.isViewReady())
			_field.update();
	}
	
	public Field getField() {
		return _field;
	}
	
	public boolean nextTo(Cell c) {
		return Math.abs(this._x-c._x) <= 1 && Math.abs(this._y-c._y) <= 1 && this._x != c._x && this._y != c._y;
	}
	
	public boolean isFree() {
		return _animal == null;	
	}
	
	public Cell findFreeAdjacentCell() {
		return _field.findFreeAdjacentCell(_x, _y);
	}
	
	public String toString(){
		return "Case ["+_x+","+_y+"]";
	}
}