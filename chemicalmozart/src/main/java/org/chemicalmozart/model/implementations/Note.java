package org.chemicalmozart.model.implementations;




/**
 * Description of the class Note.
 *
 *
 */
public class Note {

	private DegreeImpl _degreeImpl;
	private int _octave;
	private Rythme _rythme;
	private int _position;
	private Pitch _pitch;
	private DegreeImpl _degree;
	/**
	 * @return the degreeImpl
	 */
	public DegreeImpl getDegreeImpl() {
		return _degreeImpl;
	}
	/**
	 * @param degreeImpl the degreeImpl to set
	 */
	public void setDegreeImpl(DegreeImpl degreeImpl) {
		this._degreeImpl = degreeImpl;
	}
	/**
	 * @return the octave
	 */
	public int get_octave() {
		return _octave;
	}
	/**
	 * @param octave the octave to set
	 */
	public void set_octave(int octave) {
		this._octave = octave;
	}
	/**
	 * @return the rythme
	 */
	public Rythme get_rythme() {
		return _rythme;
	}
	/**
	 * @param rythme the rythme to set
	 */
	public void set_rythme(Rythme rythme) {
		this._rythme = rythme;
	}
	/**
	 * @return the position
	 */
	public int get_position() {
		return _position;
	}
	/**
	 * @param position the position to set
	 */
	public void set_position(int position) {
		this._position = position;
	}
	/**
	 * @return the pitch
	 */
	public Pitch get_pitch() {
		return _pitch;
	}
	/**
	 * @param pitch the pitch to set
	 */
	public void set_pitch(Pitch pitch) {
		this._pitch = pitch;
	}
	/**
	 * @return the degree
	 */
	public DegreeImpl get_degree() {
		return _degree;
	}
	/**
	 * @param degree the degree to set
	 */
	public void set_degree(DegreeImpl degree) {
		this._degree = degree;
	}

	
}