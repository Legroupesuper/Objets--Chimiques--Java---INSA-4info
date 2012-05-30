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

public class MoveRabbit implements ReactionRule {
	private Rabbit _rabbit;
	private Cell _nextMove;
	private Fox _oneAliveFox;
	
	public Object[] computeResult() {
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		_rabbit.changeLocation(_nextMove);
		
		_rabbit.incrementAge();
		return new Object[] {_rabbit, _nextMove, _oneAliveFox};
	}

	public boolean computeSelect() {
		return _rabbit.isAlive() && 
				_rabbit.getLocation().nextTo(_nextMove) && 
				_nextMove.isFree() &&
				_oneAliveFox.isAlive();
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}

	public void set_rabbit(Rabbit _rabbit) {
		this._rabbit = _rabbit;
	}

	public void set_nextMove(Cell _nextMove) {
		this._nextMove = _nextMove;
	}
	
	public void set_oneAliveFox(Fox _oneAliveFox) {
		this._oneAliveFox = _oneAliveFox;
	}
}