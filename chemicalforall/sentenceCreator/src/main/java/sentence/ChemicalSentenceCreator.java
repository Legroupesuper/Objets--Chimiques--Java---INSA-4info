package sentence;

import fr.insa.rennes.info.chemical.backend.Solution;

public class ChemicalSentenceCreator {

	public static void main(String[] args) {
		Solution subjectsSolution = new Solution();
		Solution prepositionsSolution = new Solution();
		Solution placesSolution = new Solution();
		Solution complementSolution = new Solution();
		Solution verbsSolution = new Solution();
		Solution mainSolution = new Solution();
		
		subjectsSolution.add("The cat");
		subjectsSolution.add("The dog");
		subjectsSolution.add("The bird");
		subjectsSolution.add("A wild pikachu");
		subjectsSolution.add("Chloe Boulanger");
		SubjectType subjectType = null;
		subjectsSolution.add(subjectType);
		
		verbsSolution.add("eats");
		verbsSolution.add("runs");
		verbsSolution.add("appears");
		verbsSolution.add("is sexy and knows it");
		VerbType verbType = null;
		verbsSolution.add(verbType);
		
		placesSolution.add("the roof");
		placesSolution.add("the garden");
		placesSolution.add("the kitchen");
		PlaceType placeType = null;
		placesSolution.add(placeType);
		
		prepositionsSolution.add("in");
		prepositionsSolution.add("on");
		prepositionsSolution.add("above");
		prepositionsSolution.add("below");
		PrepositionType prepositionType = null;
		prepositionsSolution.add(prepositionType);
		
		complementSolution.add(prepositionsSolution);
		complementSolution.add(placesSolution);
		
		mainSolution.add(new ChooseComplementRR());
		mainSolution.add(new ChooseSubjectRR());
		mainSolution.add(new ChooseVerbRR());
		mainSolution.add(complementSolution);
		mainSolution.add(verbsSolution);
		mainSolution.add(subjectsSolution);
		
		
		try {
			mainSolution.react();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		while (!mainSolution.is_inert());
		System.out.println(mainSolution);
	}

}
