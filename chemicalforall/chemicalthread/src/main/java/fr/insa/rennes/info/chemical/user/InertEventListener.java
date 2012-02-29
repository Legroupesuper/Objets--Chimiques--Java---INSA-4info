package fr.insa.rennes.info.chemical.user;

import java.util.EventListener;


public interface InertEventListener extends EventListener {
	public void isInert(InertEvent e);
}
