package fr.insa.rennes.info.chemical.user;

import java.util.EventListener;
import java.util.EventObject;



/**
 * This class represents a special EventObject which will be fired during the detection of a solution inertia.
 * Only to be used with the InertEventListener.
 * @see EventListener 
 * @see EventObject
 * @author Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 **/
public class InertEvent extends EventObject{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the InertEvent. 
	 * @see EventObject
	 * @param source the object on which the event initially occured.
	 */
	public InertEvent(Object source) {
		super(source);
	}

}
