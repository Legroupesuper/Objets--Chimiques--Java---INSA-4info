package fr.insa.rennes.info.chemical.user;

import java.util.EventListener;


/**
 * This interface describes a special EventListener which can be used to detect the inertia of a solution.
 * If implemented and used, the <code>isInert</code> method will be called automatically when the solution reaches an inert state,
 * which means that every reaction is finished, or cannot react anymore. 
 * @see EventListener 
 * @see InertEvent
 * @author Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 **/
public interface InertEventListener extends EventListener {
	/**
	 * Will be automatically called when a solution reaches the inert state, which means that
	 * every reaction is finished, or cannot react anymore. Implementation may contains
	 * specific instructions to execute when the reaction is over.
	 * @see InertEvent
	 * @param e the InertEvent fired by the inert solution.
	 */
	public void isInert(InertEvent e);
}
