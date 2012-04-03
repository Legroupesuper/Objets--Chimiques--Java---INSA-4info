package org.chemicalmozart.model.interfaces;

import java.util.List;

import org.chemicalmozart.model.implementations.Note;
/**
 * This interface is used to describe a Rythme class.
 * @author cedricandreolli
 *
 */
public interface Rythme {
	/**
	 * It returns a list of notes
	 * @return a list of notes with a specific rythme
	 */
	List<Note> getListNotes();
}
