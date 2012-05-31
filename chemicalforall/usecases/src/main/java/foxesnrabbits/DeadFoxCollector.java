
package foxesnrabbits;

import fr.insa.rennes.info.chemical.user.ReactionRule;

public class DeadFoxCollector implements ReactionRule {
	private Fox _deadFox;
	
	public Object[] computeResult() {
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		_deadFox.setDead();
		return new Object[] {};
	}

	public boolean computeSelect() {
		return !_deadFox.isAlive();
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}

	public void set_deadFox(Fox _deadFox) {
		this._deadFox = _deadFox;
	}
}