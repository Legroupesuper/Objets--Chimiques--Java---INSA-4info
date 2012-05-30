/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChLoe.

    ChLoe is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChLoe is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with ChLoe.  If not, see <http://www.gnu.org/licenses/>
*/
package indexproviderproof;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.user.InertEvent;
import fr.insa.rennes.info.chemical.user.InertEventListener;

public class Main {
	public static void launchReaction(Solution solution){
		solution.addInertEventListener(new InertEventListener() {
			public void isInert(InertEvent e) {
				System.out.println("End of reaction");
				System.out.println(e.getSource());
			}
		});

		System.out.println("Solution initiale : "+solution);
		solution.react();
	}

	public static void main(String[] args) {
		Solution solution = new Solution();
		testDummy3(solution);
		launchReaction(solution);
	}

	public static void testDummy1(Solution solution) {

		for(Integer i = 0; i < 3; i++) solution.add(i);
		for(Integer i = 0; i < 3; i++) solution.add("string"+i);

		Solution subSolution0 = new Solution();
		subSolution0.add(new Integer(3));

		solution.add(subSolution0);
		solution.add(new DummyReaction());
	}

	public static void testDummy2(Solution solution) {
		for(Integer i = 0; i < 3; i++) solution.add(i);
		for(Integer i = 0; i < 3; i++) solution.add("string"+i);

		Solution subSolution0 = new Solution();
		subSolution0.add(new Integer(3));

		solution.add(subSolution0);
		solution.add(new DummyReaction2());
	}

	public static void testDummy3(Solution solution) {
		for(Integer i = 0; i < 3; i++) solution.add(i);
		for(Integer i = 0; i < 3; i++) solution.add("string"+i);

		Solution subSolution0 = new Solution();
		subSolution0.add(new Integer(3));

		Solution subSolution1 = new Solution();
		subSolution1.add(new String("a"));
		subSolution1.add(new String("b"));
		subSolution1.add(new String("c"));

		Solution subSolution2 = new Solution();
		subSolution2.add(new Integer(1));
		subSolution2.add(new Integer(2));
		subSolution2.add(new String("d"));
		subSolution2.add(new String("e"));

		solution.add(subSolution0);
		solution.add(subSolution1);
		solution.add(subSolution2);

		solution.add(new DummyReactionSub());
	}



	public static void testDummy4(Solution solution) {

		for(Integer i = 0; i < 3; i++) solution.add(i);
		for(Integer i = 0; i < 3; i++) solution.add("string"+i);

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


		solution.add(new DummyReactionSubSub());
	}
}