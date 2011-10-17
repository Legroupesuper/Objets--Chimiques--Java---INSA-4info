import java.lang.reflect.InvocationTargetException;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Solution s = new Solution();
		s.add(new Integer(5));
		s.add(new Integer(15));
		s.add(new Integer(35));
		s.add(new Integer(150));
		s.add(new Integer(-5));
		s.add(new Integer(10));
		s.add("Babar");
		s.add(" Le");
		s.add(" Gros");
		s.add(" Eléphant ");
		s.add(new ConcatRR());
		s.add(new MaxRR());
		
		try {
			s.run();
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
		System.out.println("Resultat : ");
		for(Object o : s){
			System.out.println(o);
		}

	}

}
