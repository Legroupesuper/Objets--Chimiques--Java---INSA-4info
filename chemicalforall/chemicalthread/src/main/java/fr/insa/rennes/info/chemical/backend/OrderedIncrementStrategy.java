package fr.insa.rennes.info.chemical.backend;

/**
 * This class implements an ordered strategy (standard "+1" incrementation of the counter)
 * @author Cédric Andreolli, Chloé Boulanger, Olivier Cléro, Antoine Guellier, Sébastien Guilloux, Arthur Templé
 */
class OrderedIncrementStrategy implements IncrementStrategy {
	
	/**
	 * The increment function is the function of the strategy pattern
	 * It simply increments the counter by 1 in a binary way.
	 * Index 0 is incremented until its max value is reached, then index 1
	 * is increased and index 0 is reset and etc
	 * @param _index The index array: the values of the index
	 * @param _maxIndex The maximum values of the indexes
	 * @return A table of integers that is the new value of the indexes
	 * @throws ChemicalException
	 */
	public int[] increment(int[] _index, int[] _maxIndex) throws ChemicalException{
		int current = 0;
		_index[0] += 1;
		while(current<_index.length){
			if(_index[current]>=_maxIndex[current]){
				if(current+1>=_index.length){
					throw new ChemicalException("Overflow détecté");
				}
				_index[current] = 0;
				_index[current+1]+=1;
			}
			current++;
		}
		return _index;
	}
}
