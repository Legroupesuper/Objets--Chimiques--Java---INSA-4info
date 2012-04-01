package org.chemicalmozart.model.implementations;




/**
 * Description of the class Number.
 *
 *
 */
public class Number {

	private int _value;


	public Number(int _value) {
		super();
		this._value = _value;
	}

	public Number(Number n){
		super();
		this._value = n._value;
	}

	/**
	 * Description of the method getValue.
	 *
	 * @return null
	 */
	public int getValue() {
		return this._value;
	}

	/**
	 * Description of the method increment.
	 *
	 */
	public void increment() {
		this._value++;
	}

	/**
	 * Description of the method setValue.
	 *
	 * @param value
	 */
	public void setValue(int value) {
		this._value = value;
	}

}