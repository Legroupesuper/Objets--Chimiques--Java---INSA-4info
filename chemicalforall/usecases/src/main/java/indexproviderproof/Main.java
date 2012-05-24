package indexproviderproof;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.user.InertEvent;
import fr.insa.rennes.info.chemical.user.InertEventListener;

public class Main {
	public static void testDummy1() {
		
	}
	
	public static void main(String[] args) {
		Solution solution = new Solution();

		for(Integer i = 0; i < 3; i++) {
			solution.add(i);
		}

		for(Integer i = 0; i < 10; i++) {
			solution.add("string"+i);
		}

		Solution subSolution0 = new Solution();
		subSolution0.add(new Integer(3));

		Solution subSolution1 = new Solution();
		subSolution1.add(new String("a"));
		subSolution1.add(new String("b"));
		subSolution1.add(new String("c"));

		Solution subSolution2 = new Solution();
		subSolution2.add(new Integer(1));
		subSolution2.add(new Integer(2));
		
		Solution subSubSolution42 = new Solution();
		subSubSolution42.add("x");
		subSubSolution42.add("y");
		
		Solution subsubSolution0 = new Solution();
		subsubSolution0.add(new Integer(4));
		subsubSolution0.add(new Integer(5));
		subsubSolution0.add(new Integer(6));
		subsubSolution0.add(new String("d"));
		subsubSolution0.add(new String("e"));
		subsubSolution0.add(new String("f"));
		subsubSolution0.add(new String("g"));

		Solution subsubSolution1 = new Solution();
		subsubSolution1.add(new Integer(7));
		subsubSolution1.add(new String("h"));
		subsubSolution1.add(new String("i"));
		subsubSolution1.add(new String("j"));

		
		Solution subSubSubSolution0 = new Solution();
		subSubSubSolution0.add(new Integer(12));
		subSubSubSolution0.add(new Integer(42));
		subSubSubSolution0.add("subsubstring0");
		subSubSubSolution0.add("subsubstring1");
		
		subsubSolution0.add(subSubSubSolution0);
		subSolution0.add(subSubSolution42);
		subSolution1.add(subsubSolution0);
		subSolution2.add(subsubSolution1);
		solution.add(subSolution0);
		solution.add(subSolution1);
		solution.add(subSolution2);

//		solution.add(new DummyReaction());
//		solution.add(new DummyReaction2());
		solution.add(new DummyReactionSub());
//		solution.add(new DummyReactionSubSub());


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
