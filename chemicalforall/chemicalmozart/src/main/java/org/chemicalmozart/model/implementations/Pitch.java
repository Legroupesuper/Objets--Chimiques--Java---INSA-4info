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
	 * @return the octave
	 */
	public int getOctave() {
		return _octave;
	}
	/**
	 * @param octave the octave to set
	 */
	public void setOctave(int octave) {
		this._octave = octave;
	}
	/**
	 * @return the degree
	 */
	public DegreeImpl getDegree() {
		return _degree;
	}
	/**
	 * @param degree the degree to set
	 */
	public void setDegree(DegreeImpl degree) {
		this._degree = degree;
	}

}