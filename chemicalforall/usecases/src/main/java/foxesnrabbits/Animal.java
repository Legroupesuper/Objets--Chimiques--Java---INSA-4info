/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of libchloe.

    libchloe is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    libchloe is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with libchloe.  If not, see <http://www.gnu.org/licenses/>
*/
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