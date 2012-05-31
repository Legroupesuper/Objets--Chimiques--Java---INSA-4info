
package foxesnrabbits;

import fr.insa.rennes.info.chemical.user.ReactionRule;

public class DeadRabbitCollector implements ReactionRule {
	private Rabbit _deadRabbit;
	
	public Object[] computeResult() {
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		_deadRabbit.setDead();
		return new Object[] {};
	}

	public boolean computeSelect() {
		return !_deadRabbit.isAlive();
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}

	public void set_deadRabbit(Rabbit _deadRabbit) {
		this._deadRabbit = _deadRabbit;
	}
}