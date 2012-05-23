package indexproviderproof;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.user.InertEvent;
import fr.insa.rennes.info.chemical.user.InertEventListener;

public class Main {
	public static void main(String[] args) {
		Solution solution = new Solution();
		
		for(Integer i = 0; i < 4; i++) {
			solution.add(i);
		}
		
		for(Integer i = 0; i < 10; i++) {
			solution.add("string"+i);
		}
		
		Solution subSolution1 = new Solution();
		subSolution1.add(new String("a"));
		subSolution1.add(new String("b"));
		subSolution1.add(new String("c"));
		
		Solution subSolution2 = new Solution();
		subSolution2.add(new Integer(1));
		subSolution2.add(new Integer(2));
		subSolution2.add(new Integer(3));
		
		solution.add(subSolution1);
		solution.add(subSolution2);
		
//		solution.add(new DummyReaction());
//		solution.add(new DummyReaction2());
		solution.add(new DummyReactionSub());
		
		solution.addInertEventListener(new InertEventListener() {
			public void isInert(InertEvent e) {
				System.out.println("End of reaction");
				System.out.println(e.getSource());
			}
		});
		
		System.out.println("Solution initiale : "+solution);
		solution.react();
	}

}
