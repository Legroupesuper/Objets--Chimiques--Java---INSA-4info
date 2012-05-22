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