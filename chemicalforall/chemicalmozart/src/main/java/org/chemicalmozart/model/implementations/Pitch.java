package org.chemicalmozart.model.implementations;


/**
 * Description of the class Pitch.
 *
 *
 */
public class Pitch {

	private int _octave;
	private DegreeImpl _degree;

	/**
	 * @return the degree
	 */
	public DegreeImpl getDegree() {
		return this._degree;
	}
	/**
	 * @return the octave
	 */
	public int getOctave() {
		return this._octave;
	}
	/**
	 * @param degree the degree to set
	 */
	public void setDegree(DegreeImpl degree) {
		this._degree = degree;
	}
	/**
	 * @param octave the octave to set
	 */
	public void setOctave(int octave) {
		this._octave = octave;
	}

}