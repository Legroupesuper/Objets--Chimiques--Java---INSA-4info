package fr.insa.rennes.info.chemical.backend;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a specific case of IndexProviderElement.
 * It is used to represent a subsolution.
 * An IndexProviderSubSolution assign for each solutions of a lower level, a List<IndexProviderElement>.
 * @author Cédric Andreolli, Chloé Boulanger, Olivier Cléro, Antoine Guellier, Sébastien Guilloux, Arthur Templé
 *
 */
class SubIndexProviderSolution implements SubIndexProvider{
	private List<List<SubIndexProvider>> _listElements;
	private int _currentValue;
	private int _currentSubSol;
	private List<List<Integer>> _dependantIndexes;
	/**
	 * Constructor
	 * @param _listElements The first level list correspond to all subsolutions contained in the current level
	 * of a solution. 
	 */
	public SubIndexProviderSolution(List<List<SubIndexProvider>> _listElements, List<List<Integer>> dependantIndexes) {
		super();
		this._listElements = _listElements;
		this._dependantIndexes = dependantIndexes;
	}

	public List<List<Integer>> get_dependantIndexes() {
		return _dependantIndexes;
	}

	public void set_dependantIndexes(List<List<Integer>> _dependantIndexes) {
		this._dependantIndexes = _dependantIndexes;
	}

	public List<SubIndexProvider> get_listElements(int position) {
		return _listElements.get(position);
	}
	
	public List<SubIndexProvider> get_listElements() {
		return _listElements.get(_currentSubSol);
	}

	public void set_listElements(List<List<SubIndexProvider>> _listElements) {
		this._listElements = _listElements;
	}

	public void init() {
		_currentValue = 0;
		_currentSubSol = 0;
		for(List<SubIndexProvider> l : _listElements)
			for(SubIndexProvider e : l)
				e.init();
	}

	public int getValue() {
		return _currentSubSol;
	}

	/**
	 * This function is used to increment the configuration of the indexes by one.
	 * @return An overflow has been reached
	 */
	public boolean increment() {
		boolean overflow = false;
		
		/*
		 * The following loop try to increment the _currentValue of the _currentList as long as an
		 * overflow is detected.
		 * The condition of the loop fails when no overflow is detected or when the _currentValue is
		 * greater than number of elements in the _currentList.
		 */
		while(_currentValue < _listElements.get(_currentSubSol).size() && (overflow = _listElements.get(_currentSubSol).get(_currentValue).increment()
				)){
			_currentValue++;
		}
		
		/*
		 * When an overflow is detected there are some configurations to check :
		 * 	- can we try to increment a first level list (which correspond to an other subsolution)
		 *  - if we already are on the last element of the first level list, we must return an overflow
		 *  otherwise, no overflow is detected and the next call to increment will try to increment _currentValue = 0.
		 *  This algorithm work like the +1 algorithm. You always start to increment the least significant number.
		 */
		if(overflow && _currentSubSol<_listElements.size()-1){//Increment the first level list
			_currentSubSol++;
			_currentValue=0;
			return false;
		}else if(overflow){//Real overflow
			this.init();
			return true;
		}else{//It's not an overflow at any level
			_currentValue = 0;
			return false;
		}
	}
	
	public SubIndexProvider getElement(){
		return _listElements.get(_currentSubSol).get(_currentValue);
	}
	
	public String toString(){
		String result = "";
		int i = 0;
		for(List<SubIndexProvider> lSip : _listElements) {
			result += "[ "+i+" : ";
			for(SubIndexProvider sip : lSip) {
				result += sip.toString();
			}
			result += "]";
			
			i++;
		}
		
		/*String result = "("+_currentSubSol+"/"+_listElements.size();
		//result += _currentValue;
		result += ") {"+_dependantIndexes+"}";
		for(SubIndexProvider e : _listElements.get(_currentSubSol))
			result+= (e.toString()+" ");*/
		return result;
	}
	
	public void setValue(int v) {
		_currentSubSol = v;
	}
	
	/**
	 * Compute the number of different configurations possible
	 */
	public BigInteger getNumberOfElements() {
		BigInteger result = BigInteger.valueOf(0);
		
		for(List<SubIndexProvider> l : _listElements){
			BigInteger resulttmp = BigInteger.valueOf(1);
			for(SubIndexProvider e : l){
				try{
				resulttmp = resulttmp.
						multiply(
						e.getNumberOfElements());
				}catch(Exception ex){
					return BigInteger.valueOf(0);
				}
			}
			result = result.add(resulttmp);
		}
		return result;
	}

	public boolean isValid() {
		List<Integer> valuesIndexProvider;
		boolean isCurrentIndexValid;
		
		for(SubIndexProvider e : _listElements.get(_currentSubSol)){
			
			if(!e.isValid())
				return false;
		}
		
		for(List<Integer> l : _dependantIndexes){
			valuesIndexProvider = new ArrayList<Integer>();
			isCurrentIndexValid = true;
			//TODO : Vérifier le l != null
			
			if(l!=null){
				for(int n : l){
					
					if(valuesIndexProvider.contains(_listElements.
							get(_currentSubSol).
							get(n).getValue())){
						isCurrentIndexValid = false;
						break;
					}else{
						valuesIndexProvider.add(_listElements.get(_currentSubSol).get(n).getValue());
					}
				}
				if(!isCurrentIndexValid){
					
					return false;
				}
			}
		}
		
		return true;
	}

	public void merge(SubIndexProviderSolution sipSol) {
		_listElements.addAll(sipSol._listElements);
	}
}
