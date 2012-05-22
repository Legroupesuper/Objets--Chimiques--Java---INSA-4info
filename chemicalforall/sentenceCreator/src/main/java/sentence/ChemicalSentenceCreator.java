/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChemicalLibSuper.

    ChemicalLibSuper is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChemicalLibSuper is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with ChemicalLibSuper.  If not, see <http://www.gnu.org/licenses/>
 */
package sentence;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.user.InertEvent;
import fr.insa.rennes.info.chemical.user.InertEventListener;

public class ChemicalSentenceCreator {

	public static void test1(){
		// Initiliazing the solutions
		Solution subjectsSolution = new Solution();
		Solution prepositionsSolution = new Solution();
		Solution placesSolution = new Solution();
		Solution complementSolution = new Solution();
		Solution verbsSolution = new Solution();
		Solution mainSolution = new Solution();

		// Preparing the solution which contains subjects
		subjectsSolution.add(new Subject("The cat"));
		subjectsSolution.add(new Subject("The dog"));
		subjectsSolution.add(new Subject("The bird"));
		subjectsSolution.add(new Subject("A wild pikachu"));
		subjectsSolution.add(new Subject("Chloe Boulanger"));
		subjectsSolution.add(new SubjectType());

		// Preparing the solution which contains verbs
		verbsSolution.add(new Verb("eats"));
		verbsSolution.add(new Verb("runs"));
		verbsSolution.add(new Verb("appears"));
		verbsSolution.add(new Verb("is sexy and knows it"));
		verbsSolution.add(new VerbType());

		// Preparing the solution which contains complements
		// preposition + places
		placesSolution.add("the roof");
		placesSolution.add("the garden");
		placesSolution.add("the kitchen");
		placesSolution.add(new PlaceType());

		prepositionsSolution.add("in");
		prepositionsSolution.add("on");
		prepositionsSolution.add("above");
		prepositionsSolution.add("below");
		prepositionsSolution.add(new PrepositionType());

		// Preparing the main solution
		mainSolution.add(new ChooseComplementRR());
		mainSolution.add(new ChooseSubjectRR());
		mainSolution.add(new ChooseVerbRR());
		mainSolution.add(new PutWordsTogetherRR());
		mainSolution.add(prepositionsSolution);
		mainSolution.add(placesSolution);
		mainSolution.add(complementSolution);
		mainSolution.add(verbsSolution);
		mainSolution.add(subjectsSolution);
		
		//letsgo
		lauchReaction(mainSolution);
	}

	public static void test2() {
		Solution mainSolution = new Solution();
		Solution subjectsSolution = new Solution();
		
		subjectsSolution.add(new Subject("The cat"));
		subjectsSolution.add(new Subject("The dog"));
		subjectsSolution.add(new Subject("The bird"));
		subjectsSolution.add(new Subject("A wild pikachu"));
		subjectsSolution.add(new Subject("Chloe Boulanger"));
		subjectsSolution.add(new SubjectType());
		
		mainSolution.add(subjectsSolution);
		mainSolution.add(new ChooseSubjectRR());
		
		lauchReaction(mainSolution);
	}
	
	public static void test3() {
		Solution mainSolution = new Solution();
		
		mainSolution.add(new Subject("The cat"));	
		mainSolution.add(new Verb("is eating sausages"));
		mainSolution.add(new Complement("on the couch"));
		mainSolution.add(new PutWordsTogetherRR());
		
		lauchReaction(mainSolution);
	}
	
	public static void test4(){
		Solution mainSolution = new Solution();
		Solution sol1 = new Solution();
		Solution sol2 = new Solution();
		Solution sol3 = new Solution();
		Solution sol4 = new Solution();
		sol4.add(1);
		sol3.add(sol4);
		sol2.add(sol3);
		sol1.add(sol2);
		mainSolution.add(sol1);
		
		for(int i=0;i<10;i++){
			sol3.add(new Subject(""+i));
		}
		for(int i=0;i<10;i++){
			sol2.add(new Subject(""+i));
		}
		for(int i=0;i<10;i++){
			sol1.add(new Subject(""+i));
		}
		lauchReaction(mainSolution);

	}
	
	public static void lauchReaction(final Solution s){
		System.out.println("-- Before --");
		System.out.println(s);
		s.react();
		s.addInertEventListener(new InertEventListener() {		
			public void isInert(InertEvent e) {
				System.out.println("-- After --");
				System.out.println(s);
			}
		});
	}

	public static void main(String[] args) {
		test1();
	}

}