
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