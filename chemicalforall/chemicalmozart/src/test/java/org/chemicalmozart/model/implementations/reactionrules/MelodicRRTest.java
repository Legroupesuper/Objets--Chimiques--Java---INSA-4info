package org.chemicalmozart.model.implementations.reactionrules;

import junit.framework.TestCase;

import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.Note;
import org.chemicalmozart.model.implementations.Pitch;
import org.junit.Test;

public class MelodicRRTest extends TestCase{

	@Override
	protected void setUp() throws Exception {
		MelodicRR rr = new MelodicRR();
		Pitch pitch = new Pitch();
		pitch.setOctave(3);
		pitch.setDegree(new DegreeImpl(1));
		Note note = new Note();
		note.set_chord(_chord)
		rr.set_activated(true);
		rr.set_max(5);
		rr.set_melodicNumber(0);
		rr.set_note(note);
		rr.set_pitch(pitch);
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
