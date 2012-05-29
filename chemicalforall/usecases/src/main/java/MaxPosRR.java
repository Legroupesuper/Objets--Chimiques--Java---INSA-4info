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
import fr.insa.rennes.info.chemical.user.ReactionRule;


public class MaxPosRR implements ReactionRule {
	private Position posA;
	private Position posB;

	public Object[] computeResult() {
		if(posA.calculeNorme()>posB.calculeNorme()) return new Object[]{posA};
		else return new Object[]{posB};
	}

	public boolean computeSelect() {
		return true;//(posA.getX()%2 == 0);
	}

	public Multiplicity getMultiplicity() {
		// TODO Auto-generated method stub
		return Multiplicity.ONE_SHOT;
	}

	public Position getPosA() {
		return posA;
	}

	public Position getPosB() {
		return posB;
	}

	public void setPosA(Position posA) {
		this.posA = posA;
	}

	public void setPosB(Position posB) {
		this.posB = posB;
	}

	@Override
	public String toString(){
		return "MaxRR";
	}

}