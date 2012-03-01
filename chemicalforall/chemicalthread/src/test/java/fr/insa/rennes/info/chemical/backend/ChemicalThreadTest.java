package fr.insa.rennes.info.chemical.backend;

import junit.framework.TestCase;
import fr.insa.rennes.info.chemical.user.ReactionRule;

/**
 * @author ArthurTemple
 * Unit tests for ChemicalThread
 */

public class ChemicalThreadTest extends TestCase {

	private ChemicalThread testChemicalThread;
	private ReactionRule testReactionRule;
	private Solution testSolution;
	
	/**
	 * @param name
	 */
	public ChemicalThreadTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.testSolution = new Solution();
		this.testReactionRule = new ReactionRule(){
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
		testSolution.add(testReactionRule);
		
		this.testChemicalThread = new ChemicalThread(testReactionRule, testSolution, new ThreadGroup("test"));
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link ChemicalThread#get_reactionRule()}.
	 */
	public void testGet_reactionRule() {
		assertTrue(testReactionRule == testChemicalThread.get_reactionRule());
	}

	/**
	 * Test method for {@link ChemicalThread#get_solutionContainer()}.
	 */
	public void testGet_solutionContainer() {
		assertTrue(testSolution == testChemicalThread.get_solutionContainer());
	}

	/**
	 * Test method for {@link ChemicalThread#set_reactionRule(ReactionRule)}.
	 */
	public void testSet_reactionRule() {
		ReactionRule rr = new ReactionRule(){
			public Object[] computeResult(){
				return null;
			}
			public boolean computeSelect(){
				return true;
			}
			public ReactionRule.Multiplicity getMultiplicity(){
				return ReactionRule.Multiplicity.INFINITY_SHOT;
			}
		};
		testChemicalThread.set_reactionRule(rr);
		assertTrue(rr == testChemicalThread.get_reactionRule());
	}

	/**
	 * Test method for {@link ChemicalThread#set_solutionContainer(Solution)}.
	 */
	public void testSet_solutionContainer() {
		Solution s = new Solution();
		testChemicalThread.set_solutionContainer(s);
		assertTrue(s == testChemicalThread.get_solutionContainer());
	}
}
