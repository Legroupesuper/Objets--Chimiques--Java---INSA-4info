package org.chemicalmozart.model.implementations;




/**
 * Description of the class DegreeImpl.
 * It represents the classical notation of a degree in classical music
 *
 */
public class DegreeImpl {
	/**
	 * The value of the degree (I-II-III-IV-V-VI-VII)
	 */
	private int _value;

	/**
	 * The default constructor of a DegreeImpl.<br /><br/>
	 * For example, if you want to create a I degree :<br />
	 * DegreeImpl deg = new DegreeImpl(1);
	 * @param _value This value represents the degree of the DegreeImpl
	 */
	public DegreeImpl(int _value) {
		super();
		this._value = _value;
	}

	/**
	 * @return the _value
	 */
	public int get_value() {
		return this._value;
	}

	/**
	 * @param _value the _value to set
	 */
	public void set_value(int _value) {
		this._value = _value;
	}


}