package fr.insa.rennes.info.chemical.backend;

import java.math.BigInteger;

import junit.framework.TestCase;

/**
 * @author ArthurTemple
 * Unit tests for IndexProvider
 */

public class SubIndexProviderElementTest extends TestCase {

	private SubIndexProviderElement testIndexProviderSimpleElement;
	private final int numberElementsInSolution;
	
	/**
	 * @param name
	 */
	public SubIndexProviderElementTest(String name) {
		super(name);
		numberElementsInSolution = 12;
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.testIndexProviderSimpleElement = new SubIndexProviderElement(numberElementsInSolution);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link IndexProviderSimpleElement#get_nummberElementsInSolution()}.
	 */
	public void testGet_nummberElementsInSolution() {
		assertTrue(numberElementsInSolution == testIndexProviderSimpleElement.get_nummberOfElementsInSolution());
	}
	
	/**
	 * Test method for {@link IndexProviderSimpleElement#set_nummberElementsInSolution(int)}.
	 */
	/*public void testSet_nummberElementsInSolution() {
		int testInt = 42;
		testIndexProviderSimpleElement.set_nummberOfElementsInSolution(testInt);
		assertTrue(testInt == testIndexProviderSimpleElement.get_nummberOfElementsInSolution());
	}*/

	/**
	 * Test method for {@link IndexProviderSimpleElement#getValue()}.
	 */
	public void testGetValue() {
		assertNotNull(testIndexProviderSimpleElement.getValue());
	}

	/**
	 * Test method for {@link IndexProviderSimpleElement#init()}.
	 */
	public void testInit() {
		testIndexProviderSimpleElement.init();
		assertTrue(0 == testIndexProviderSimpleElement.getValue());
	}

	/**
	 * Test method for {@link IndexProviderSimpleElement#increment()}.
	 */
	public void testIncrement() {
		boolean overflow = false;
		for(int i = 0 ; i < numberElementsInSolution ; i++){
			overflow |= testIndexProviderSimpleElement.increment();
		}
		assertTrue(overflow);
		overflow = false;
		testIndexProviderSimpleElement.init();
		int localMax = numberElementsInSolution-1;	//Maximum acceptable value without overflow
		for(int i = 0 ; i < localMax ; i++){
			overflow |= testIndexProviderSimpleElement.increment();
		}
		assertFalse(overflow);
		assertTrue(localMax == testIndexProviderSimpleElement.getValue());
	}

	/**
	 * Test method for {@link IndexProviderSimpleElement#setValue(int)}.
	 */
	public void testSetValue() {
		int test = 42;
		testIndexProviderSimpleElement.setValue(test);
		assertTrue(test == testIndexProviderSimpleElement.getValue());
	}

	/**
	 * Test method for {@link IndexProviderSimpleElement#getNumberOfElements()}.
	 */
	public void testGetNumberOfElements() {
		assertTrue(BigInteger.valueOf(numberElementsInSolution) == testIndexProviderSimpleElement.getNumberOfElements());
	}

	/**
	 * Test method for {@link IndexProviderSimpleElement#isValid()}.
	 */
	public void testIsValid() {
		assertTrue(testIndexProviderSimpleElement.isValid());
	}
}
