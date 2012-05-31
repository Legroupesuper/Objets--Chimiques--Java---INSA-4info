

package sentence;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.user.InertEvent;
import fr.insa.rennes.info.chemical.user.InertEventListener;

public class ChemicalSentenceCreator {

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

	public static void test1(){
		// Initiliazing the solutions
		Solution subjectsSolution = new Solution();
		Solution prepositionsSolution = new Solution();
		Solution placesSolution = new Solution();
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
		verbsSolution.add(new Verb("is sexy and knows it"));
		verbsSolution.add(new Verb("eats"));
		verbsSolution.add(new Verb("runs"));
		verbsSolution.add(new Verb("appears"));
		
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
		mainSolution.add(new ChooseVerbRR());
		mainSolution.add(new ChooseSubjectRR());
		mainSolution.add(new PutWordsTogetherRR());
		mainSolution.add(prepositionsSolution);
		mainSolution.add(placesSolution);
		mainSolution.add(subjectsSolution);
		mainSolution.add(verbsSolution);

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

		mainSolution.add(new Subject("Ondine"));
		mainSolution.add(new Verb("is fucking"));
		mainSolution.add(new Complement("on the couch"));
		mainSolution.add(new PutWordsTogetherRR());

		lauchReaction(mainSolution);
	}

	public static void test4(){
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
		mainSolution.add(new Verb("is eating sausages"));
		mainSolution.add(new Complement("on the couch"));
		mainSolution.add(new PutWordsTogetherRR());
		mainSolution.add(new Subject("Ondine"));
		mainSolution.add(new Verb("is fucking"));
		mainSolution.add(new Complement("on the sofa"));

		lauchReaction(mainSolution);
	}

}