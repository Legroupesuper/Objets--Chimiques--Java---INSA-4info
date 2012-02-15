package backend;
import fr.insa.rennes.info.chemical.backend.Solution;
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
	public void testAdd() {
		testSolution.add(new Integer(12));
		assertTrue(this.testSolution.contains(12));
	}


}
