package foxesnrabbits;

import java.util.ArrayList;
import java.util.List;

import fr.insa.rennes.info.chemical.user.ReactionRule;

public class Rabbit extends Animal  {
	public Rabbit(int defaultAge, Cell c) {
		super(defaultAge, 50, 0.15, 5, 3, c);
	}
}
