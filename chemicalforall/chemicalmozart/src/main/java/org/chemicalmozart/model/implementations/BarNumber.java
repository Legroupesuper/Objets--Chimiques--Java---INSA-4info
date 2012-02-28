package org.chemicalmozart.model.implementations;




/**
 * Description of the class MeasureNumber.
 *
 *
 */
public class BarNumber extends Number {

	public BarNumber(int _value) {
		super(_value);
	}

	public void increment(){
		setValue(getValue()+1);
	}
}