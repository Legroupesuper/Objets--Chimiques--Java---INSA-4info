package fr.insa.rennes.info.chemical.user;

import java.util.EventListener;

import fr.insa.rennes.info.chemical.backend.InertEvent;

public interface InertEventListener extends EventListener {
	public void isInert(InertEvent e);
}
