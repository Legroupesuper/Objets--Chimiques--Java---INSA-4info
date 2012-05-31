/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChLoe.

    ChLoe is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChLoe is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with ChLoe.  If not, see <http://www.gnu.org/licenses/>
*/

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
	
	public int hashCode(){
		return (int)(x/y*1000);
	}
}