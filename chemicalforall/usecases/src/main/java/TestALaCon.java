import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.user.InertEvent;
import fr.insa.rennes.info.chemical.user.InertEventListener;


public class TestALaCon {
	public static void main(String[] args) {
		final Solution sol = new Solution();
		sol.add(1);
		sol.add(2);
		sol.add(3);
		sol.add(4);
		sol.add(5);
		sol.add(6);
		sol.add(7);
		sol.add(new MaxIntRR(0));
		System.out.println("Avant : ");
		System.out.println(sol);
		sol.addInertEventListener(new InertEventListener() {
			public void isInert(InertEvent e) {
				System.out.println("Apres");
				System.out.println(sol);
			}
		});
		sol.react();
	}
}
