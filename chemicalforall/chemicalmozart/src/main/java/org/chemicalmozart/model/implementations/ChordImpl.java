package org.chemicalmozart.model.implementations;




/**
 * Description of the class ChordImpl.
 *
 * A ChordImpl is quite close to a DegreeImpl but it add the notion of rythme to the DregreeImpl.
 * A chord has a position in the bar.
 */
public class ChordImpl {
	/**
	 * The degree of the chord
	 */
	private DegreeImpl _degrees;
	/**
	 * The position of this chord in the bar. For exemple, the first generated chord in
	 * a bar will get the position 0, the second will have the position 1, etc
	 */
	private int _position;
	/**
	 * @return the _degrees
	 */
	public DegreeImpl get_degrees() {
		return this._degrees;
	}
	/**
	 * @return the _position
	 */
	public int get_position() {
		return this._position;
	}
	/**
	 * @param _degrees the _degrees to set
	 */
	public void set_degrees(DegreeImpl _degrees) {
		this._degrees = _degrees;
	}
	/**
	 * @param _position the _position to set
	 */
	public void set_position(int _position) {
		this._position = _position;
	}

}