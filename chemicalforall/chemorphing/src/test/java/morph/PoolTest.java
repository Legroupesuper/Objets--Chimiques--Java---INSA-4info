package morph;
import demo_backend.Pool;
import junit.framework.TestCase;

/**
 * @author ArthurTemple
 * Unit tests for Chemorphing's Pool class
 */

public class PoolTest extends TestCase {

	private Pool pool;
	
	/**
	 * @param name
	 */
	public PoolTest(String name) {
		super(name);
		this.pool = new Pool();
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
	 * Test method for {@link Pool#loadPool()}.
	 */
	public void testLoad() {
		assertTrue(this.pool.loadPool());
	}

	/**
	 * Test method for {@link Pool#savePool()}.
	 */
	public void testSave() {
		this.pool.loadPool();
		assertTrue(this.pool.savePool());
	}


}
