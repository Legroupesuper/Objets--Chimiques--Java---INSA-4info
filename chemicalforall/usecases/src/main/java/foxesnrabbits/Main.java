package foxesnrabbits;

import java.util.Random;

import fr.insa.rennes.info.chemical.backend.Solution;

public class Main {
	
	
	public static void main(String[] args) {
		Solution solution = new Solution();

		Cell c;
		Random rand = new Random();
		int r;
		int nbReactionRules = 0;
		/*for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				c = new Cell(i, j);
				solution.add(c);
				
				r = rand.nextInt(100-nbReactionRules/2);
				if(r <= 33) {
					solution.add(new Rabbit(50, 0.13, c));
					nbReactionRules++;
				} else if(r <= 66) {
					solution.add(new Fox(125, 0.06, c));
					nbReactionRules++;
				}
			}
		}*/
	}
}
