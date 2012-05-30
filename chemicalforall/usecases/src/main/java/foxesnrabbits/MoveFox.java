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
package foxesnrabbits;

import fr.insa.rennes.info.chemical.user.ReactionRule;

public class MoveFox implements ReactionRule {
	private Fox _fox;
	private Cell _nextMove;
	private Rabbit _oneAliveRabbit;
	
	public Object[] computeResult() {
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		_fox.changeLocation(_nextMove);
		_fox.incrementAge();
		if(_nextMove.getAnimal() instanceof Rabbit) {
			Animal eatenRabbit = _nextMove.getAnimal();
			eatenRabbit.setDead();
			return new Object[] {_fox, _nextMove};
		}
		
		return new Object[] {_fox, _nextMove, _oneAliveRabbit};
		
	}

	public boolean computeSelect() {
		return _fox.isAlive() && 
				_fox.getLocation().nextTo(_nextMove) && 
				(_nextMove.isFree() || _nextMove.getAnimal() instanceof Rabbit) &&
				_oneAliveRabbit.isAlive();
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}

	public void set_fox(Fox _fox) {
		this._fox = _fox;
	}

	public void set_nextMove(Cell _nextMove) {
		this._nextMove = _nextMove;
	}

	public void set_oneAliveRabbit(Rabbit _oneAliveRabbit) {
		this._oneAliveRabbit = _oneAliveRabbit;
	}
	
}