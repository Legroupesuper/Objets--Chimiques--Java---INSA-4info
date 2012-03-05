package fr.insa.rennes.info.chemical.backend;

import java.lang.reflect.Field;
import java.util.logging.Logger;

import junit.framework.TestCase;
import fr.insa.rennes.info.chemical.user.ReactionRule;
import fr.insa.rennes.info.chemical.user.ReactionRule.Multiplicity;

/**
 * @author ArthurTemple
 * Unit tests for ChemicalThread
 */

public class UtilsTest extends TestCase {
	
	private String packageName = "fr.insa.rennes.info.chemical";
	
	/**
	 * @param name
	 */
	public UtilsTest(String name) {
		super(name);
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
	 * Test method for {@link Utils#logger}.
	 */
	public void testLogger() {
		assertFalse(Utils.logger.equals(Logger.getAnonymousLogger()));
		assertTrue(Utils.logger.equals(Logger.getLogger(packageName)));
	}

	/**
	 * Test method for {@link Utils#getMethodFromReactionRule()}.
	 */
	public void testGetMethodFromReactionRule() {
		ReactionRule rr = new ReactionRule() {
			
			int foo;
			
			public void setFoo(int aFoo){
				this.foo = aFoo;
			}
			
			public Multiplicity getMultiplicity() {
				return Multiplicity.ONE_SHOT;
			}
			
			public boolean computeSelect() {
				return true;
			}
			
			public Object[] computeResult() {
				return new Object[]{foo+12};
			}
		};
		try {
			assertTrue(Utils.getMethodFromReactionRule(rr, "set", rr.getClass().getDeclaredField("foo")).getName().equalsIgnoreCase("setFoo"));
		} catch (NoSuchFieldException e) {
			fail("NoSuchFieldException");
		} catch (SecurityException e) {
			fail("SecurityException");
		}
	}
}
