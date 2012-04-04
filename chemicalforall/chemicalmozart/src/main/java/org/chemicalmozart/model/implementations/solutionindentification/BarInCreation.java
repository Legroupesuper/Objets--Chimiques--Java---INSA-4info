package org.chemicalmozart.model.implementations.solutionindentification;
/**
 * This class is used to identify a solution which represents a bar in creation.<br /><br />
 * This class memorize the state of the solution.
 * @see BarInCreationState
 * @author cedricandreolli
 *
 */
public class BarInCreation {
	/**
	 * This enumeration is used to define the current state of a barInCreation solution.<br />
	 * <br />
	 * It can take the following values :<br />
	 * <ul>
	 * 		<li><b>HARMONICRR</b> : means that the solution is waiting the action of a HarmonicRR</li>
	 * 		<li><b>RYTHMEHRR</b> : means that the solution is waiting the action of a RythmeHRR</li>
	 * </ul>
	 * @author cedricandreolli
	 *
	 */
	public enum BarInCreationState {HARMONICRR, RYTHMEHRR}

	/**
	 * Represents the current state of the BarInCreation solution
	 */
	BarInCreationState _state;

	/**
	 * @return the _state
	 */
	public BarInCreationState get_state() {
		return this._state;
	}

	/**
	 * @param _state the _state to set
	 */
	public void set_state(BarInCreationState _state) {
		this._state = _state;
	}

}
