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
