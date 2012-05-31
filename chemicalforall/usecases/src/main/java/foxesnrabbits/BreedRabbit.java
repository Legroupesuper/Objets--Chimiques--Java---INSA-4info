
package foxesnrabbits;

import java.util.ArrayList;
import java.util.List;

import fr.insa.rennes.info.chemical.user.ReactionRule;

public class BreedRabbit implements ReactionRule {
	private Rabbit _rabbit;
	private Fox _oneAliveFox;

	public Object[] computeResult() {
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		List<Object> result = new ArrayList<Object>();
		int nbBirths = _rabbit.breed();
		
		Cell locationNewRabbit;
		for(int i = 0; i < nbBirths; i++) {
			locationNewRabbit = _rabbit.getLocation().findFreeAdjacentCell();
			if(locationNewRabbit != null) {
				result.add(new Rabbit(0, locationNewRabbit));
			}
		}
		
		_rabbit.incrementAge();
		result.add(_rabbit);
		return result.toArray();
	}

	public boolean computeSelect() {
		return _rabbit.isAlive() && 
				_rabbit.canBreed() &&
				_oneAliveFox.isAlive();
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}

	public void set_rabbit(Rabbit _rabbit) {
		this._rabbit = _rabbit;
	}

	public void set_oneAliveFox(Fox _oneAliveFox) {
		this._oneAliveFox = _oneAliveFox;
	}
	
	
}