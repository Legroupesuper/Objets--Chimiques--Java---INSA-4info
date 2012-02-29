package fr.insa.rennes.info.chemical.backend;

import java.math.BigInteger;

class SubIndexProviderElement  implements SubIndexProvider{
	private int _numberOfElementsInSolution;
	private int _currentValue;

	public int get_nummberOfElementsInSolution() {
		return _numberOfElementsInSolution;
	}

	public void set_nummberOfElementsInSolution(int _nummberOfElementsInSolution) {
		this._numberOfElementsInSolution = _nummberOfElementsInSolution;
	}

	public SubIndexProviderElement(int _nummberOfElementsInSolution) {
		super();
		this._numberOfElementsInSolution = _nummberOfElementsInSolution;
	}

	public void init() {
		_currentValue = 0;
	}

	public int getValue(){
		return _currentValue;
	}

	public boolean increment(){
		if(++_currentValue >= _numberOfElementsInSolution){
			_currentValue = 0;
			return true;
		}
		return false;
	}

	public String toString(){
		return "|"+_currentValue+"/"+_numberOfElementsInSolution;
	}

	public void setValue(int v) {
		_currentValue = v;
	}

	public BigInteger getNumberOfElements() {
		return BigInteger.valueOf(_numberOfElementsInSolution);
	}

	public boolean isValid() {
		return true;
	}
}
