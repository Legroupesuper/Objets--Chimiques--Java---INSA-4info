/**
 * This class is used to obtain an index counter.
 * We use this class during the reactives research process to try every cases (every n-uplet) : 
 * an index in the _index array corresponds to an index in the main HashMap
 * in the class Solution.  
 * @author Cédric Andreolli, Chloé Boulanger, Olivier Cléro, Antoine Guellier, Sébastien Guilloux, Arthur Templé
 *
 */
public class IndexProvider {
	/**
	 * This table represents the maximum values of the indexes
	 */
	public int[] _maxIndex;
	/**
	 * This table represents the current value for the indexes
	 */
	public int[] _index;
	
	/**
	 * Sole constructor
	 * @param maxIndex the table that represents the maximum values of the indexes
	 */
	public IndexProvider(int[] maxIndex){
		_maxIndex = maxIndex;
		_index = new int[maxIndex.length];
		for(int i=0; i<_index.length; i++){
			_index[i] = 0;
		}
	}
	
	/**
	 * Increment the counter by 1 in a binary way.
	 * Index 0 is incremented until its max value is reached, then index 1 
	 * is increased and index 0 is reset and etc
	 * @return Is still in the counter range
	 */
	public boolean increment(){
		int current = 0;
		_index[0] += 1;
		while(current<_index.length){
			if(_index[current]>=_maxIndex[current]){
				if(current+1>=_index.length){
					return false;
				}
				_index[current] = 0;
				_index[current+1]+=1;
			}
			current++;
		}
		return true;
	}
	
	/**
	 * Get the value of the given index
	 * @param i the given index number
	 * @return The value of i-th index of the counter
	 */
	public int  get(int i){
		return _index[i];
	}
	
	/**
	 * 
	 * @return 
	 */
	public String toString(){
		String result = "";
		for(int i : _index){
			result+= (i+" ");
		}
		return result;
	}
}
