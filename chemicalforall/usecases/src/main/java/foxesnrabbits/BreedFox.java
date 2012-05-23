package foxesnrabbits;

import java.util.ArrayList;
import java.util.List;

import fr.insa.rennes.info.chemical.user.ReactionRule;

public class BreedFox implements ReactionRule {
	private Fox _fox;
	private Rabbit _oneAliveRabbit;

	public Object[] computeResult() {
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		List<Object> result = new ArrayList<Object>();
		int nbBirths = _fox.breed();
		
		Cell locationNewFox;
		for(int i = 0; i < nbBirths; i++) {
			locationNewFox = _fox.getLocation().findFreeAdjacentCell();
			if(locationNewFox != null) {
				result.add(new Rabbit(0, locationNewFox));
			}
		}
		
		_fox.incrementAge();
		result.add(_fox);
		return result.toArray();
	}

	public boolean computeSelect() {
		return _fox.isAlive() && 
				_fox.canBreed() &&
				_oneAliveRabbit.isAlive();
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}

	public void set_fox(Fox _fox) {
		this._fox = _fox;
	}
	
	public void set_oneAliveRabbit(Rabbit _oneAliveRabbit) {
		this._oneAliveRabbit = _oneAliveRabbit;
	}
}
