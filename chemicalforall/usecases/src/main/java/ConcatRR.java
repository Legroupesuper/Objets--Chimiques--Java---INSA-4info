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
import fr.insa.rennes.info.chemical.user.ReactionRule;



public class ConcatRR implements ReactionRule{
	private String s;
	private String t;
	public Object[] computeResult() {
		System.out.println("J'ai s="+s+" et t="+t);
		return new String[]{s+t};
	}
	public boolean computeSelect() {
		return true;
	}
	public Multiplicity getMultiplicity() {
		// TODO Auto-generated method stub
		return Multiplicity.INFINITY_SHOT;
	}

	public String getS() {
		return s;
	}

	public String getT() {
		return t;
	}

	public void setS(String s) {
		this.s = s;
	}

	public void setT(String t) {
		this.t = t;
	}

}