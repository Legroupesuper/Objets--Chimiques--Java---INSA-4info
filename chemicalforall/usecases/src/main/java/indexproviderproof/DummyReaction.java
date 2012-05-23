package indexproviderproof;

import fr.insa.rennes.info.chemical.user.ReactionRule;

public class DummyReaction implements ReactionRule {
	
	
	
	public Object[] computeResult() {
		return null;
	}

	public boolean computeSelect() {
		return false;
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}

}
