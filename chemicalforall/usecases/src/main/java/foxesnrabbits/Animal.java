
package foxesnrabbits;

import java.util.Random;

public class Animal {
	protected static Random rand = new Random();
	
	protected boolean _alive;
	protected int _age;
	protected int _maxAge;
	protected double _breedingProbability;
	protected int _breedingAge;
	protected int _maxLitterSize;
	protected Cell _location;
	
	public Animal(int defaultAge, int longevity, double breedProb, int breedAge, int maxLitter, Cell c) {
		_alive = true;
		_age = 0;
		_maxAge = longevity;
		_breedingProbability = breedProb;
		_breedingAge = breedAge;
		_maxLitterSize = maxLitter;
		_location = c;
		_location.setAnimal(this);
		
		if(defaultAge == -1) {
			_age = rand.nextInt(_maxAge);
		} else
			_age = defaultAge;
	}

	public void incrementAge() {
		_age++;
		if(_age > _maxAge){
			setDead();		
		}
	}
	
	public int breed() {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= _breedingProbability) {
            births = rand.nextInt(_maxLitterSize) + 1;
        }
        return births;
    }
	
	protected boolean canBreed() {
		return _age > _breedingAge;
	}
	
	public Cell getLocation() {
		return _location;
	}
	
	public void changeLocation(Cell nextLocation) {
		_location.setAnimal(null);
		nextLocation.setAnimal(this);
		_location = nextLocation;
	}
	
	public boolean isAlive() {
		return _alive;
	}
	
	public void setDead() {
		_alive = false;
		_location.setAnimal(null);
	}
	
}