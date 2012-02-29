package fr.insa.rennes.info.chemical.backend;

import java.math.BigInteger;

import junit.framework.TestCase;
import fr.insa.rennes.info.chemical.user.ReactionRule;

/**
 * @author ArthurTemple
 * Unit tests for IndexProvider
 */

public class IndexProviderSubSolutionTest extends TestCase {

	private IndexProviderSubSolution testIndexProviderSubSolution;
	
	/**
	 * @param name
	 */
	public IndexProviderSubSolutionTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
//		testIndexProviderSubSolution = new IndexProviderSubSolution(_listElements, dependantIndexes);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

//	/**
//	 * Test method for {@link IndexProviderSimpleElement#getValue()}.
//	 */
//	public void testGetValue() {
//		assertNotNull(testIndexProviderSubSolution.getValue());
//	}
//
//	/**
//	 * Test method for {@link IndexProviderSimpleElement#init()}.
//	 */
//	public void testInit() {
//		testIndexProviderSubSolution.init();
//		assertTrue(0 == testIndexProviderSubSolution.getValue());
//	}
//
//	/**
//	 * Test method for {@link IndexProviderSimpleElement#increment()}.
//	 */
//	public void testIncrement() {
//		boolean overflow = false;
//		for(int i = 0 ; i < numberElementsInSolution ; i++){
//			overflow |= testIndexProviderSubSolution.increment();
//		}
//		assertTrue(overflow);
//		overflow = false;
//		testIndexProviderSubSolution.init();
//		int localMax = numberElementsInSolution-1;	//Maximum acceptable value without overflow
//		for(int i = 0 ; i < localMax ; i++){
//			overflow |= testIndexProviderSubSolution.increment();
//		}
//		assertFalse(overflow);
//		assertTrue(localMax == testIndexProviderSubSolution.getValue());
//	}
//
//	/**
//	 * Test method for {@link IndexProviderSimpleElement#setValue(int)}.
//	 */
//	public void testSetValue() {
//		int test = 42;
//		testIndexProviderSubSolution.setValue(test);
//		assertTrue(test == testIndexProviderSubSolution.getValue());
//	}
//
//	/**
//	 * Test method for {@link IndexProviderSimpleElement#getNumberOfElements()}.
//	 */
//	public void testGetNumberOfElements() {
//		assertTrue(BigInteger.valueOf(numberElementsInSolution) == testIndexProviderSubSolution.getNumberOfElements());
//	}
//
//	/**
//	 * Test method for {@link IndexProviderSimpleElement#isValid()}.
//	 */
//	public void testIsValid() {
//		assertTrue(testIndexProviderSubSolution.isValid());
//	}
	
	/**
	 * Test method for {@link IndexProviderSimpleElement#fooTest()}.
	 */
	public void testFooTest() {
		assertTrue(true);
	}
}
