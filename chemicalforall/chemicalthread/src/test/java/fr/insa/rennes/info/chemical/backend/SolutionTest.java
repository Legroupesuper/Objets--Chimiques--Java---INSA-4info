package fr.insa.rennes.info.chemical.backend;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import fr.insa.rennes.info.chemical.backend.Solution.Strategy;
import fr.insa.rennes.info.chemical.user.InertEvent;
import fr.insa.rennes.info.chemical.user.InertEventListener;
import fr.insa.rennes.info.chemical.user.ReactionRule;

/**
 * @author ArthurTemple
 * Unit tests for Solution
 */

public class SolutionTest extends TestCase {

	private Solution testSolution;

	/**
	 * @param name
	 */
	public SolutionTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.testSolution = new Solution();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link Solution#Solution(Strategy)}.
	 */
	public void testConstructor() {
		testSolution = new Solution(Strategy.ORDERED);
		boolean verification = true;
		verification &= testSolution instanceof Collection;
		verification &= testSolution.isEmpty();
		verification &= !testSolution.is_inert();
		assertTrue(verification == true);
	}

	/**
	 * Test method for {@link Solution#add(Object)}.
	 */
	public void testAddInteger() {
		testSolution.add(Integer.valueOf(12));
		assertTrue(this.testSolution.contains(12));
	}

	/**
	 * Test method for {@link Solution#add(Object)}.
	 */
	public void testAddObject() {
		Object foo = new Object();
		testSolution.add(foo);
		assertTrue(this.testSolution.contains(foo));
	}

	/**
	 * Test method for {@link Solution#add(Object)}.
	 */
	public void testAddReactionRule() {

		ReactionRule rr = new ReactionRule() {
			public Object[] computeResult(){
				return null;
			}
			public boolean computeSelect(){
				return true;
			}
			public ReactionRule.Multiplicity getMultiplicity(){
				return ReactionRule.Multiplicity.ONE_SHOT;
			}

			public void setThis$0(Object t) {

			}
		};

		testSolution.add(rr);
		assertTrue(this.testSolution.contains(rr));
	}

	/**
	 * Test method for {@link Solution#add(Object)}.
	 */
	public void testAddSolution() {
		Solution barSol = new Solution();
		barSol.add(new Object());
		testSolution.add(barSol);
		assertTrue(this.testSolution.contains(barSol));
	}

	/**
	 * Test method for {@link Solution#addAll(Collection)}.
	 */
	public void testAddAll() {
		boolean test = true;
		Collection<Object> coll = new ArrayList<Object>();
		coll.add(12);
		coll.add(new Solution());
		coll.add(new Object());
		coll.add(new LinkedList<Byte>());
		coll.add(new boolean[]{true, false, true, true});

		testSolution.addAll(coll);

		for(Object elem : coll){
			test &= testSolution.contains(elem);
		}

		assertTrue(test);
	}

	/**
	 * Test method for {@link Solution#contains(Object)}.
	 */
	public void testContains() {
		Collection<Object> coll = new ArrayList<Object>();

		coll.add(12);
		testSolution.add(12);

		Solution subSol = new Solution();
		coll.add(subSol);
		testSolution.add(subSol);

		Object foo = new Object();
		coll.add(foo);
		testSolution.add(foo);

		List<?> subColl = new LinkedList<Byte>();
		coll.add(subColl);
		testSolution.add(subColl);

		boolean[] subTab = new boolean[]{true, false, true, true};
		coll.add(subTab);
		testSolution.add(subTab);

		Iterator<Object> it = testSolution.iterator();
		while(it.hasNext()){
			assertTrue(coll.contains(it.next()));
		}

	}

	/**
	 * Test method for {@link Solution#containsAll(Collection)}.
	 */
	public void testContainsAll() {
		Collection<Object> coll = new ArrayList<Object>();

		coll.add(12);
		testSolution.add(12);

		Solution subSol = new Solution();
		coll.add(subSol);
		testSolution.add(subSol);

		Object foo = new Object();
		coll.add(foo);
		testSolution.add(foo);

		List<?> subColl = new LinkedList<Byte>();
		coll.add(subColl);
		testSolution.add(subColl);

		boolean[] subTab = new boolean[]{true, false, true, true};
		coll.add(subTab);
		testSolution.add(subTab);

		assertTrue(testSolution.containsAll(coll));
	}

	/**
	 * Test method for {@link Solution#addInertEventListener(InertEventListener)}.
	 */
	public void testAddInertEventListener() {

		testSolution.addInertEventListener(new InertEventListener() {

			public void isInert(InertEvent e) {
				assertTrue(true);
			}
		});

		testSolution.react();

	}

	/**
	 * Test method for {@link Solution#clear()}.
	 */
	public void testClear() {
		testSolution.add(12);
		testSolution.clear();

		assertTrue(testSolution.isEmpty());

	}

	/**
	 * Test method for {@link Solution#deleteReaction(ReactionRule)}.
	 */
	public void testDeleteReaction() {
		ReactionRule rr = new ReactionRule() {

			public Multiplicity getMultiplicity() {
				return Multiplicity.ONE_SHOT;
			}

			public boolean computeSelect() {
				return true;
			}

			public Object[] computeResult() {
				return new Object[]{1, 2, 3};
			}
			public void setThis$0(Object t)  {

			}
		};
		testSolution.add(rr);
		testSolution.addInertEventListener(new InertEventListener() {

			public void isInert(InertEvent e) {
				assertTrue(testSolution.isEmpty());
			}
		});

	}

	/**
	 * Test method for {@link Solution#fireInertEvent(InertEvent)}.
	 */
	public void testFireInertEvent() {
		Solution subSol = new Solution();
		final InertEvent ie = new InertEvent(subSol);
		testSolution.addInertEventListener(new InertEventListener() {
			public void isInert(InertEvent e) {
				assertTrue(e.equals(ie));
			}
		});
		testSolution.add(subSol);
		subSol.fireInertEvent(ie);
	}

	/**
	 * Test method for {@link Solution#is_inert()}.
	 */
	public void testIsInert() {
		testSolution.add(12);
		testSolution.add("toto");
		testSolution.add(16);
		testSolution.add(new ReactionRule() {

			private int a;
			private int b;

			public void setA(int aA){
				this.a = aA;
			}

			public void setB(int aB){
				this.b = aB;
			}

			public Multiplicity getMultiplicity() {
				return Multiplicity.ONE_SHOT;
			}

			public boolean computeSelect() {
				return true;
			}

			public Object[] computeResult() {
				return new Object[]{a+b, a-b};
			}
			public void setThis$0(Object t)  {

			}
		});
		testSolution.addInertEventListener(new InertEventListener() {
			public void isInert(InertEvent e) {
				assertTrue(testSolution.is_inert());
			}
		});
		testSolution.react();
	}

	/**
	 * Test method for {@link Solution#isEmpty()}.
	 */
	public void testIsEmpty() {
		testSolution.add(12);
		testSolution.add("toto");
		testSolution.add(16);
		testSolution.clear();
		assertTrue(testSolution.isEmpty());
	}

	/**
	 * Test method for {@link Solution#iterator()}.
	 */
	public void testIterator() {
		assertTrue(testSolution.iterator().getClass().equals(Collections.unmodifiableList(new LinkedList<Object>()).iterator().getClass()));
	}

	/**
	 * Test method for {@link Solution#react()}.
	 */
	public void testReact() {
		testSolution.add(12);
		testSolution.add("toto");
		testSolution.add(16);
		testSolution.add(new ReactionRule() {

			private int a;
			private int b;

			public void setA(int aA){
				this.a = aA;
			}

			public void setB(int aB){
				this.b = aB;
			}

			public Multiplicity getMultiplicity() {
				return Multiplicity.INFINITY_SHOT;
			}

			public boolean computeSelect() {
				return true;
			}

			public Object[] computeResult() {
				return new Object[]{a+b, a-b};
			}
			public void setThis$0(Object t)  {

			}
		});
		testSolution.add(new ReactionRule() {

			private ReactionRule a;
			private String b;

			public void setA(ReactionRule aA){
				this.a = aA;
			}

			public void setB(String aB){
				this.b = aB;
			}

			public Multiplicity getMultiplicity() {
				return Multiplicity.ONE_SHOT;
			}

			public boolean computeSelect() {
				return true;
			}

			public Object[] computeResult() {
				return new Object[]{};
			}

			public void setThis$0(Object t)  {

			}
		});
		
		testSolution.addInertEventListener(new InertEventListener() {
			public void isInert(InertEvent e) {
				assertTrue(testSolution.size() == 1);
			}
		});
		testSolution.react();
		
		
		/*
		 * Hey, c'est antoine.
		 * Ce qui selon moi devrait marcher: attente active obligatoire pour rester dans le thread
		 * "main".
		 * Et supprimer listener carrément. Comme ca en tout cas, ca fait failure.
		while(!testSolution.is_inert()) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e1) {
				//Loop
			}
		}
		
		assertTrue(testSolution.size() == 1);*/
		
	}

	/**
	 * Test method for {@link Solution#remove(Object)}.
	 */
	public void testRemove() {
		testSolution.clear();
		testSolution.add(12);
		assertTrue(testSolution.remove(12));
	}

	/**
	 * Test method for {@link Solution#removeAll(Collection)}.
	 */
	public void testRemoveAll() {
		LinkedList<Integer> ll = new LinkedList<Integer>();
		ll.add(0, Integer.valueOf(12));
		testSolution.add(12);
		assertTrue(testSolution.removeAll(ll));
	}

	/**
	 * Test method for {@link Solution#requestForParameters(ReactionRule)}.
	 */
	/*
	public void testRequestForParameters() {
		testSolution.clear();
		testSolution.add(12);
		testSolution.add(16);
		ReactionRule rr = new ReactionRule() {

			private int a;
			private int b;

			public void setA(int aA){
				this.a = aA;
			}
			public void setB(int aB){
				this.b = aB;
			}

			public Multiplicity getMultiplicity() {
				return Multiplicity.ONE_SHOT;
			}

			public boolean computeSelect() {
				return true;
			}

			public Object[] computeResult() {
				return null;
			}
		};
		testSolution.add(rr);
		try {
			assertTrue(testSolution.requestForParameters(rr));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 */

	/**
	 * Test method for {@link Solution#retainAll(Collection)}.
	 */
	public void testRetainAll() {
		LinkedList<Object> ll = new LinkedList<Object>();
		testSolution.add(12);
		ll.add(12);
		testSolution.add(15);
		ll.add(15);
		String foo1 = "Chilean wine amateur";
		String foo2 = "French connoisseur";
		String foo0 = "one hundred";
		testSolution.add(foo0);
		ll.add(foo0);
		testSolution.add(foo1);
		ll.add(foo2);

		assertTrue(testSolution.retainAll(ll));

		assertFalse(testSolution.contains(foo1));
		assertFalse(testSolution.contains(foo2));
		assertTrue(testSolution.contains(foo0));
	}




}