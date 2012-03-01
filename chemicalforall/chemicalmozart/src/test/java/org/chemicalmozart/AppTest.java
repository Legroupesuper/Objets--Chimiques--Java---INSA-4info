package org.chemicalmozart;

import org.chemicalmozart.model.implementations.reactionrules.CreateBarRRTest;
import org.chemicalmozart.model.implementations.reactionrules.HarmonicRR1Test;
import org.chemicalmozart.model.implementations.reactionrules.PickOneRRTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
    	TestSuite testSuite = new TestSuite();
    	testSuite.addTest(new TestSuite(CreateBarRRTest.class));
    	testSuite.addTest(new TestSuite(HarmonicRR1Test.class));
    	testSuite.addTest(new TestSuite(PickOneRRTest.class));
    	return testSuite;
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
}
