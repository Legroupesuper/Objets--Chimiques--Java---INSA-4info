package functionnaltests;
import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.user.InertEvent;
import fr.insa.rennes.info.chemical.user.InertEventListener;


public class Test2 {
	public static void main(String[] args) {
		final Solution mainSol = new Solution();
		Solution innerSol = new Solution();
		Solution innerSol2 = new Solution();
		innerSol.add(1);
		innerSol.add(2);
		innerSol.add(3);
		innerSol.add(4);
		mainSol.add(new DeleteRR());
		mainSol.add(innerSol);
		mainSol.add(new DeleteRuleMax());
		mainSol.addInertEventListener(new InertEventListener() {
			
			public void isInert(InertEvent e) {
				System.out.println("Après : ");
				System.out.println(mainSol);
			}
		});
		
		System.out.println("Avant : ");
		System.out.println(mainSol);
		mainSol.react();
	}
}
