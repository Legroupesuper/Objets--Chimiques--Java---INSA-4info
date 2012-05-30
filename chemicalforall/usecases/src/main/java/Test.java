/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of libchloe.

    libchloe is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    libchloe is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with libchloe.  If not, see <http://www.gnu.org/licenses/>
*/
import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.user.InertEvent;
import fr.insa.rennes.info.chemical.user.InertEventListener;



public class Test {
	public static final Solution s = new Solution(Solution.Strategy.RANDOM);
	public static Solution s2 = new Solution();
	public static Solution s3 = new Solution();
	public static Solution s4 = new Solution();
	public static Solution s5 = new Solution();
	public static Solution s6 = new Solution();
	
	public static boolean _finit = false;
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		MaxIntSubSolRR lala = new MaxIntSubSolRR();
//		
//		lala.computeResult();
//		
//		System.out.println(lala);
//		
//		if(true)
//			return;
		
		
		
		s4.add(12);
		s4.add(12);
		s4.add(12);
		s4.add(12);
		s4.add(12);
		s4.add(12);
		s3.add(5);
		s3.add(5);
		s3.add(9);
		s3.add(6);
		s3.add(18);
		s3.add(12);
		s3.add(6);
		s3.add(23);
		s3.add(6);
		s3.add(98);
		s3.add(6);
		s3.add(21);
		s3.add(786);
//		s2.add(5);
//		s2.add(5);
//		s2.add(6);
		s3.add("toto");
		s3.add("est");
		s3.add("toto");
		s3.add("naze");
		s3.add("gros");
		s3.add("un");
//		s3.add(new ConcatRR());
//		s3.add(new MaxIntRR(0));
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
//		s.add(new MaxIntRR(0));
//		s.add(new MaxIntSubSolRR());
//		s.add(new ConcatRR());
//		s.add(new RandomNumberRR());
//		s.add(new TrucRR());
		s.add(new ConcatSubSolRR());
		System.out.println("AVANT : ");
		System.out.println(s);
		final long time = System.currentTimeMillis();

		s.addInertEventListener(new InertEventListener() {
			public void isInert(InertEvent e) {
				System.out.println("Time : "+(System.currentTimeMillis()-time)+" millisecondes");
				System.out.println("Resultat : ");
				System.out.println(s);
				
				Test._finit = true;
			}
		});
	
		
		try {
			s.react();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		/*int i = 0;
		while(!s.is_inert() && i*5 < 60) {
			try {
				Thread.sleep(2);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			System.out.println("\n"+s);
			i++;
		}*/
		
		
	}
}