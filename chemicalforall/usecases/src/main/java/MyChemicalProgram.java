import java.lang.reflect.InvocationTargetException;

import fr.insa.rennes.info.chemical.backend.Solution;


public class MyChemicalProgram {

	public static void main(String[] args) {	
		Position p1 = new Position(3,3);
		Position p2 = new Position(5,8);
		Position p3 = new Position(6,11);
		Position p4 = new Position(1,42);
		Position p5 = new Position(0,1);
		
		Solution maSolution = new Solution();
		
		maSolution.add(p1);
		maSolution.add(p2);
		maSolution.add(p3);
		maSolution.add(p4);
		maSolution.add(p5);
		
		maSolution.add(new MaxRR());
		maSolution.add(new MinRR());
		maSolution.add(new MoyenneDesPosRR());
		
		System.out.println(maSolution.toString());
		
		try {
			maSolution.run();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(!maSolution.is_innert())
			System.out.println(maSolution.toString());	
				
		System.out.println(maSolution.toString());	
		
	}

}
