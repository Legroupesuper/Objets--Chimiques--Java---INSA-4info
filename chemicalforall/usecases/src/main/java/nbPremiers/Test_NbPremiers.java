
package nbPremiers;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.user.InertEvent;
import fr.insa.rennes.info.chemical.user.InertEventListener;

public class Test_NbPremiers {
	public static long time = 0;

	public static void main(String[] args) {
		Solution s = new Solution();
		for(Integer i = 2; i<200; i++){
			s.add(i);
		}
		s.add(new calculNbPremierRR());

		s.addInertEventListener(new InertEventListener() {
			public void isInert(InertEvent e) {
				time = System.currentTimeMillis()-time;
				System.out.println("\n\nRéaction terminée :");
				System.out.println(e.getSource());
				System.out.println("Time : "+time+"ms");
			}
		});

		System.out.println(s);
		time = System.currentTimeMillis();
		s.react();
	}
}