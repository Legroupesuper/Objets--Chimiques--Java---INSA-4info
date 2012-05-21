package foxesnrabbits;

import java.util.ArrayList;
import java.util.List;

import fr.insa.rennes.info.chemical.user.ReactionRule;

public class Rabbit extends Animal implements ReactionRule {
	private Fox _atLeastOneFoxAlive;
	private Cell _nextMove;
	
	public Rabbit(int defaultAge, Cell c) {
		super(defaultAge, 50, 0.15, 5, 3);
		_location.setAnimal(this);
	}
	
	public Rabbit(Rabbit r) {
		super(r._age+1, 50, 0.5, 5, 3);
		_location = r._location;
		_location.setAnimal(this);
	}
	
	public Object[] computeResult() {
		List<Object> result = new ArrayList<Object>();
		
		if(_alive) {
			//Breed
			int births = breed();
			for(int i = 0; i < births; i++) {
				Cell newLocation = _location.findFreeAdjacentCell();
				//result.add(new Rabbit(false, newLocation));
			}
			
			//The rabbit just moves
			_location = _nextMove;
			
			/*if((double)rand.nextInt(100)/100 <= this._chanceToBreed) {
				result = new Object[] { new Rabbit(this._remainingYears-1, this._chanceToBreed, _location), new Rabbit(50, 0.13, _location)};
			else
				result = new Object[] { new Rabbit(this._remainingYears-1, this._chanceToBreed, _location)};*/
			
			incrementAge();
		}
		return result.toArray();
	}

	public boolean computeSelect() {
		return _location.nextTo(_nextMove) && _nextMove.isFree();
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.ONE_SHOT;
	}
	
	public void set_atLeastOneFoxAlive(Fox f) {
		_atLeastOneFoxAlive = f;
	}
	
	public void set_nextMove(Cell c) {
		_nextMove = c;
	}
}
