import java.util.List;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.user.InertEvent;
import fr.insa.rennes.info.chemical.user.InertEventListener;



public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final Solution s = new Solution(Solution.Strategy.RANDOM);
		Solution s2 = new Solution();
		Solution s3 = new Solution();
		Solution s4 = new Solution();
		Solution s5 = new Solution();
		Solution s6 = new Solution();
		
		s4.add(12);
		s4.add(12);
		s4.add(12);
		s4.add(12);
		s4.add(12);
		s4.add(12);
		s3.add(5);
		s3.add(5);
		s3.add(6);
		s3.add(6);
		s3.add(6);
		s3.add(6);
		s3.add(6);
		s3.add(6);
		s3.add(6);
		s3.add(6);
		s3.add(6);
		s3.add(6);
		s3.add(6);
		s3.add(6);
//		s2.add(5);
//		s2.add(5);
//		s2.add(6);
		s3.add("toto");
		s3.add("toto");
		s3.add("toto");
		s3.add("toto");
		s3.add("toto");
		s3.add("toto");
		s3.add("toto");
		s3.add("toto");
		s3.add("toto");
		s3.add("toto");
		s3.add("toto");
		s3.add("toto");
		s3.add("toto");
//		s3.add(new ConcatRR());
//		s3.add(new MaxIntRR());
		s5.add(56);
		s5.add(56);
		s5.add(56);
		s5.add(56);
		s5.add(56);
		s5.add(56);
		s5.add(56);
		s5.add("test");
		s5.add("test");
		s5.add("test");
		s5.add("test");
		s5.add("test");
		s5.add("test");
		s5.add("test");
		s5.add("test");
		s5.add("test");
		s5.add("test");
		s6.add(s5);
		
		s2.add(s3);
		s.add(s2);
		s.add(s6);
		s.add(s4);
		//s.add(s2);
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
		/*ChemicalElement c = new ChemicalElement() {

			public List<Object> getElements() {
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
		s.add(c);*/
		s.add(new MaxIntRR(0));
		s.add(new ConcatRR());
		//s.add(new RandomNumberRR());
		//	s.add(new TrucRR());
		System.out.println("AVANT : ");
		System.out.println(s);
		final long time = System.currentTimeMillis();

		s.addInertEventListener(new InertEventListener() {
			public void isInert(InertEvent e) {
				System.out.println("Time : "+(System.currentTimeMillis()-time)+" millisecondes");
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
