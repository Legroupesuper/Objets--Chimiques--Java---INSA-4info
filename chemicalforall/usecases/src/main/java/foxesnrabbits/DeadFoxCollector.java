package foxesnrabbits;

import fr.insa.rennes.info.chemical.user.ReactionRule;

public class DeadFoxCollector implements ReactionRule {
	private Fox _deadFox;
	
	public Object[] computeResult() {
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
