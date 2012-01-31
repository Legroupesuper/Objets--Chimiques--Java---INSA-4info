import java.util.List;

import fr.insa.rennes.info.chemical.backend.ChemicalElement;
import fr.insa.rennes.info.chemical.backend.InertEvent;
import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.backend.Solution.Strategy;
import fr.insa.rennes.info.chemical.user.InertEventListener;



public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//		try {
		//			System.setOut(new PrintStream(new File("bimbamboum.txt")));
		//		} catch (FileNotFoundException e1) {
		//			e1.printStackTrace();
		//		}
		final Solution s = new Solution(Solution.Strategy.RANDOM);
		Solution s2 = new Solution();
		Solution s3 = new Solution();
		s3.add(5);
		s3.add(5);
		s3.add(6);
//		s2.add(5);
//		s2.add(5);
//		s2.add(6);
		s3.add("toto");
		s2.add(s3);
		s.add(s2);
		s.add(s2);
		//s.add(s3);
		s.add(new Integer(5));
		s.add(new Integer(15));
		s.add(new Integer(35));
		s.add(new Integer(150));
		s.add(new Integer(-5));
		s.add(new Integer(10));
		s.add(45768);
		s.add(1);
		s.add(2);
		s.add(3);
		s.add(4);
		s.add(5);
		s.add(6);
		s.add(7);
		s.add(8);
		s.add(9);
		s.add(" Babar ");
		s.add(" Le ");
		s.add(" Gros ");
		s.add(" Eléphant ");
		ChemicalElement c = new ChemicalElement() {

			public List<Object> getElementList() {
				// TODO Auto-generated method stub
				return null;
			}

			public List<Class<? extends Object>> getTypeList() {
				// TODO Auto-generated method stub
				return null;
			}

			public void setElementList(List<Object> l) {
				// TODO Auto-generated method stub

			}

			public void setTypeList(List<Class<? extends Object>> l) {
				// TODO Auto-generated method stub

			}
		};
		s.add(c);
		s.add(new MaxIntRR());
		s.add(new ConcatRR());
		//s.add(new RandomNumberRR());
		//	s.add(new TrucRR());
		System.out.println("AVANT : ");
		System.out.println(s);


		s.addInertEventListener(new InertEventListener() {
			public void isInert(InertEvent e) {
				System.out.println("Resultat : ");
				System.out.println(s);
			}
		});

		try {
			s.react();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
}
