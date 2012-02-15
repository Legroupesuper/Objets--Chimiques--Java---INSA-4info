package backend;
import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.user.ReactionRule;
import junit.framework.TestCase;

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


}
