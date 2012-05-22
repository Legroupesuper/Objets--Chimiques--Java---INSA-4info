/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChemicalLibSuper.

    ChemicalLibSuper is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChemicalLibSuper is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with ChemicalLibSuper.  If not, see <http://www.gnu.org/licenses/>
*/
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