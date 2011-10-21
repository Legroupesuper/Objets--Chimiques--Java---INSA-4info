package fr.insa.rennes.info.chemical.backend;

/*
 * This class implements an ordered strategy (standard "+1" incrementation of the counter)
 */
public class OrderedIncrementStrategy implements IncrementStrategy {

	public int[] increment(int[] _index, int[] _maxIndex) throws ChimiqueException{
		int current = 0;
		_index[0] += 1;
		while(current<_index.length){
			if(_index[current]>=_maxIndex[current]){
				if(current+1>=_index.length){
					throw new ChimiqueException("Overflow détecté");
				}
				_index[current] = 0;
				_index[current+1]+=1;
			}
			current++;
		}
		return _index;
	}
}
