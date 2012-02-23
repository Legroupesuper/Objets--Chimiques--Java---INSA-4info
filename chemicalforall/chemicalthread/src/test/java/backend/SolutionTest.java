package backend;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import fr.insa.rennes.info.chemical.backend.Dontreact;
import fr.insa.rennes.info.chemical.backend.InertEvent;
import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.backend.Solution.Strategy;
import fr.insa.rennes.info.chemical.user.InertEventListener;
import fr.insa.rennes.info.chemical.user.ReactionRule;
import fr.insa.rennes.info.chemical.user.ReactionRule.Multiplicity;

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
		this.testSolution = new Solution();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
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
		verification &= testSolution.isEmpty();
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
		ReactionRule rr = new ReactionRule(){
			public Object[] computeResult(){
				return null;
			}
			public boolean computeSelect(){
				return true;
			}
			public ReactionRule.Multiplicity getMultiplicity(){
				return ReactionRule.Multiplicity.ONE_SHOT;
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
		testSolution = new Solution();
		
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
		testSolution.clear();
		
		assertTrue(testSolution.isEmpty());
		
	}

	/**
	 * Test method for {@link Solution#deleteReaction(ReactionRule)}.
	 */
	public void testDeleteReaction() {
		testSolution = new Solution();
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
		};
		testSolution.add(rr);
		testSolution.addInertEventListener(new InertEventListener() {
			
			public void isInert(InertEvent e) {
				assertTrue(testSolution.isEmpty());
			}
		});
		
	}
	
	


}
