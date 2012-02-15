package fr.insa.rennes.info.chemical.backend;

import java.math.BigInteger;

public class IndexProviderSimpleElement  implements IndexProviderElement{
	private int _numberElementsInSolution;
	private int _currentValue;
	
	public int get_nummberElementsInSolution() {
		return _numberElementsInSolution;
	}

	public void set_nummberElementsInSolution(int _nummberElementsInSolution) {
		this._numberElementsInSolution = _nummberElementsInSolution;
	}

	public IndexProviderSimpleElement(int _nummberElementsInSolution) {
		super();
		this._numberElementsInSolution = _nummberElementsInSolution;
	}

	public void init() {
		_currentValue = 0;
	}
	
	public int getValue(){
		return _currentValue;
	}
	
	public boolean increment(){
		if(++_currentValue >= _numberElementsInSolution){
			_currentValue = 0;
			return true;
		}
			return false;
	}
	
	public String toString(){
		return "|"+_currentValue;
	}

	public void setValue(int v) {
		_currentValue = v;
	}

	public BigInteger getNumberOfElements() {
		return BigInteger.valueOf(_numberElementsInSolution);
	}

	public boolean isValid() {
		return true;
	}
}
