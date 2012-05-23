package testOperationsSurDesNombres;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.user.InertEvent;
import fr.insa.rennes.info.chemical.user.InertEventListener;

public class Test {
	public static long time = 0;

	public static void main(String[] args) {
		Solution mainSol = new Solution();
		Solution sousSol = new Solution();

		sousSol.add(new Integer(2));
		sousSol.add(new Integer(5));
		sousSol.add(new Mult2RR(5));

		mainSol.add(new Integer(17));
		mainSol.add(new Add10_17());
		mainSol.add(new Affiche());
		mainSol.add(sousSol);

		mainSol.addInertEventListener(new InertEventListener() {
			public void isInert(InertEvent e) {
				time = System.currentTimeMillis()-time;
				System.out.println("\n\nRéaction terminée :");
				System.out.println(e.getSource());
				System.out.println("Time : "+time+"ms");
			}
		});

		System.out.println(mainSol);
		time = System.currentTimeMillis();
		mainSol.react();
	}
}
