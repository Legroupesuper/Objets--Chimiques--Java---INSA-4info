import java.util.ArrayList;
import java.util.List;

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
	private int[] _maxIndex;
	/**
	 * This table represents the current value for the indexes
	 */
	private int[] _index;

	private boolean _overflowReached = false;

	/**
	 * This list represents sets of index which can not be equals
	 */
	private List<List<Integer>> _dependantIndexes;

	/**
	 * Default constructor
	 * @param maxIndex the table that represents the maximum values of the indexes
	 * @throws ChimiqueException
	 */
	public IndexProvider(int[] maxIndex) throws ChimiqueException{
		_maxIndex = maxIndex;
		_dependantIndexes =  new ArrayList<List<Integer>>();
		_index = new int[maxIndex.length];
		for(int i=0; i<_index.length; i++){
			if(_maxIndex[i] == 0)
				throw new ChimiqueException("Maximum index value is invalid : 0");
			_index[i] = 0;
		}
	}

	/**
	 * Constructor with constraints
	 * @param maxIndex the table that represents the maximum values of the indexes
	 * @throws ChimiqueException
	 */
	public IndexProvider(List<List<Integer>> dependantIndexes, int[] maxIndex) throws ChimiqueException{
		_maxIndex = maxIndex;
		_index = new int[maxIndex.length];
		_dependantIndexes = dependantIndexes;
		for(int i=0; i<_index.length; i++){
			if(_maxIndex[i] == 0)
				throw new ChimiqueException("Maximum index value is invalid : 0");
			_index[i] = 0;
		}
		while(!checkDuplicate()){
			increment();
		}
	}



	private boolean checkDuplicate(){
		for(List<Integer> l : _dependantIndexes){
			List<Integer> valuesIndexProvider = new ArrayList<Integer>();
			boolean isCurrentIndexValid = true;

			for(int n : l){

				if(valuesIndexProvider.contains(_index[n])){
					isCurrentIndexValid = false;
					break;
				}else{
					valuesIndexProvider.add(_index[n]);
				}
			}
			if(!isCurrentIndexValid){
				return false;
			}
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
	 * Increment the counter by 1 in a binary way.
	 * Index 0 is incremented until its max value is reached, then index 1
	 * is increased and index 0 is reset and etc
	 * @return Is still in the counter range
	 */
	public void increment(){
		int current = 0;


		do{
			current = 0;
			_index[0] += 1;
			while(current<_index.length){
				if(_index[current]>=_maxIndex[current]){
					if(current+1>=_index.length){
						_overflowReached = true;
						return;
					}
					_index[current] = 0;
					_index[current+1]+=1;
				}
				current++;
			}
		}while(!checkDuplicate());
	}
	public boolean is_overflowReached() {
		return _overflowReached;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public String toString(){
		String result = "";
		for(int i : _index){
			result+= (i+" ");
		}
		return result;
	}
}
