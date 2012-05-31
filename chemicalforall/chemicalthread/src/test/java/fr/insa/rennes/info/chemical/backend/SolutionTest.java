/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChLOE.

    ChLOE is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChLOE is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with ChLOE.  If not, see <http://www.gnu.org/licenses/>
*/
package fr.insa.rennes.info.chemical.backend;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import fr.insa.rennes.info.chemical.backend.Solution.Strategy;
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
		assertTrue("Every Solution should be a collection", testSolution instanceof Collection);
		assertTrue("Every new Solution should be empty",testSolution.isEmpty());
		assertTrue("Every new Solution should be inert", testSolution.is_inert());
	}

	/**
	 * Test method for {@link Solution#add(Object)}.
	 */
	public void testAddInteger() {
		testSolution.add(Integer.valueOf(12));
		assertTrue("One should be able to add integers into a Solution",this.testSolution.contains(12));
	}

	/**
	 * Test method for {@link Solution#add(Object)}.
	 */
	public void testAddObject() {
		Object foo = new Object();
		testSolution.add(foo);
		assertTrue("One should be able to add objects into a Solution",this.testSolution.contains(foo));
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

			/*
			 * Following method is only aimed at running unit tests properly
			 * This is only an incompatibility between jUnit and the present library
			 */
			@SuppressWarnings("unused")
			public void setThis$0(Object t) {}
		};

		testSolution.add(rr);
		assertTrue("One should be able to add a ReactionRule to a Solution", this.testSolution.contains(rr));
	}

	/**
	 * Test method for {@link Solution#add(Object)}.
	 */
	public void testAddSolution() {
		Solution barSol = new Solution();
		barSol.add(new Object());
		testSolution.add(barSol);
		assertTrue("One should be able to add a Solution into a Solution", this.testSolution.contains(barSol));
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

		assertTrue("One should be able to add a Collection into a Solution", test);
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
		boolean result = true;
		while(it.hasNext()){
			result &= coll.contains(it.next());
		}
		assertTrue("One should be able to find an item they put into a Solution", result);

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

		assertTrue("One should be able to find a Collection in a Solution", testSolution.containsAll(coll));
	}

	/**
	 * Test method for {@link Solution#clear()}.
	 */
	public void testClear() {
		testSolution.add(12);
		testSolution.clear();

		assertTrue("Clearing a Solution should make it empty", testSolution.isEmpty());

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

			/*
			 * Following method is only aimed at running unit tests properly
			 * This is only an incompatibility between jUnit and the present library
			 */
			@SuppressWarnings("unused")
			public void setThis$0(Object t) {}
			
		};
		testSolution.add(rr);
		assertFalse("Adding a ReactionRule should make the Solution non-empty", testSolution.isEmpty());
		testSolution.deleteReaction(rr);
		assertTrue("Adding the ReactionRule should make the Solution empty again", testSolution.isEmpty());

	}

	/**
	 * Test method for {@link Solution#is_inert()}.
	 */
	//TODO correct this when specs are OK.
//	public void testIsInert() {
//		testSolution.add(12);
//		testSolution.add("toto");
//		testSolution.add(16);
//		testSolution.add(new ReactionRule() {
//
//			private String a;
//
//			@SuppressWarnings("unused")
//			public void setA(String aA){
//				this.a = aA;
//			}
//
//			public Multiplicity getMultiplicity() {
//				return Multiplicity.INFINITY_SHOT;
//			}
//
//			public boolean computeSelect() {
//				return true;
//			}
//
//			public Object[] computeResult() {
//				return new Object[]{};
//			}
//			
//			/*
//			 * Following method is only aimed at running unit tests properly
//			 * This is only an incompatibility between jUnit and the present library
//			 */
//			@SuppressWarnings("unused")
//			public void setThis$0(Object t)  {
//
//			}
//		});
//		testSolution.addInertEventListener(new InertEventListener() {
//			public void isInert(InertEvent e) {
//			}
//		});
//		testSolution.react();
//		System.out.println(testSolution.is_inert());
//		while(!testSolution.is_inert());
//		assertTrue("If the reaction ends, the Solution should be inert", testSolution.is_inert());
//	}

	/**
	 * Test method for {@link Solution#isEmpty()}.
	 */
	public void testIsEmpty() {
		testSolution.add(12);
		testSolution.add("toto");
		testSolution.add(16);
		assertFalse("A Solution containing elements should be non-empty (!)", testSolution.isEmpty());
		testSolution.clear();
		assertTrue("An empty Solution should be empty (strange, huh ?)", testSolution.isEmpty());
	}

	/**
	 * Test method for {@link Solution#iterator()}.
	 */
	public void testIterator() {
		assertTrue("A Solution iterator should be an unmodifiableList iterator", testSolution.iterator().getClass().equals(Collections.unmodifiableList(new LinkedList<Object>()).iterator().getClass()));
	}

	/**
	 * Test method for {@link Solution#react()}.
	 */
	//TODO not working for the moment...
//	public void testReact() {
//		
//		ReactionRule addIntRR = new ReactionRule() {
//
//			private int a;
//			private int b;
//
//			@SuppressWarnings("unused")
//			public void setA(int aA){
//				this.a = aA;
//			}
//
//			@SuppressWarnings("unused")
//			public void setB(int aB){
//				this.b = aB;
//			}
//
//			public Multiplicity getMultiplicity() {
//				return Multiplicity.INFINITY_SHOT;
//			}
//
//			public boolean computeSelect() {
//				System.out.println("J'ESSAIE "+a+" - "+b);
//				return true;
//			}
//
//			public Object[] computeResult() {
//				return new Object[]{a+b, a-b};
//			}
//			
//			/*
//			 * Following method is only aimed at running unit tests properly
//			 * This is only an incompatibility between jUnit and the present library
//			 */
//			@SuppressWarnings("unused")
//			public void setThis$0(Object t)  {}
//		};
//		
//		ReactionRule suppressRRRR = new ReactionRule() {
//
//			@SuppressWarnings("unused")
//			private ReactionRule a;
//			@SuppressWarnings("unused")
//			private String b;
//
//			@SuppressWarnings("unused")
//			public void setA(ReactionRule aA){
//				this.a = aA;
//			}
//
//			@SuppressWarnings("unused")
//			public void setB(String aB){
//				this.b = aB;
//			}
//
//			public Multiplicity getMultiplicity() {
//				return Multiplicity.ONE_SHOT;
//			}
//
//			public boolean computeSelect() {
//				return true;
//			}
//
//			public Object[] computeResult() {
//				return new Object[]{};
//			}
//
//			
//			/*
//			 * Following method is only aimed at running unit tests properly
//			 * This is only an incompatibility between jUnit and the present library
//			 */
//			@SuppressWarnings("unused")
//			public void setThis$0(Object t)  {
//
//			}
//		};
//		
//		testSolution.add(12);
//		testSolution.add("toto");
//		testSolution.add(16);
////		testSolution.add(addIntRR);
////		testSolution.add(suppressRRRR);
//		
//		testSolution.addInertEventListener(new InertEventListener() {
//			
//			public void isInert(InertEvent e) {
//				System.out.println(testSolution);
//			}
//		});
//		
//		testSolution.react();
//		
//		while(!testSolution.is_inert()) {
//			try {
//				Thread.sleep(200);
//			} catch (InterruptedException e1) {
//				// Loop
//			}
//		}
//		System.out.println("COUCOU\n"+testSolution);
//
//		assertTrue("A Solution should have the right amount of elements after a deterministic reaction", testSolution.size() == 1);
//		
//	}

	/**
	 * Test method for {@link Solution#remove(Object)}.
	 */
	public void testRemove() {
		testSolution.clear();
		testSolution.add(12);
		assertTrue("Removal of an item previously added should be effective", testSolution.remove(12));
	}

	/**
	 * Test method for {@link Solution#removeAll(Collection)}.
	 */
	public void testRemoveAll() {
		LinkedList<Integer> ll = new LinkedList<Integer>();
		ll.add(0, Integer.valueOf(12));
		testSolution.add(12);
		assertTrue("One should be able to remove a Collection from a Solution", testSolution.removeAll(ll));
	}

	/**
	 * Test method for {@link Solution#requestForParameters(ReactionRule)}.
	 */
	//TODO not working for the moment...
//	public void testRequestForParameters() {
//		testSolution.clear();
//		testSolution.add(12);
//		testSolution.add(16);
//		ReactionRule rr = new ReactionRule() {
//
//			@SuppressWarnings("unused")
//			private int a;
//			@SuppressWarnings("unused")
//			private int b;
//
//			@SuppressWarnings("unused")
//			public void setA(int aA){
//				this.a = aA;
//			}
//			@SuppressWarnings("unused")
//			public void setB(int aB){
//				this.b = aB;
//			}
//
//			public Multiplicity getMultiplicity() {
//				return Multiplicity.ONE_SHOT;
//			}
//
//			public boolean computeSelect() {
//				return true;
//			}
//
//			public Object[] computeResult() {
//				return null;
//			}
//			
//			/*
//			 * Following method is only aimed at running unit tests properly
//			 * This is only an incompatibility between jUnit and the present library
//			 */
//			@SuppressWarnings("unused")
//			public void setThis$0(Object t)  {
//		
//			}
//		};
//		testSolution.add(rr);
//		try {
//			assertTrue("The request for parameters inside a Solution should be effective", testSolution.requestForParameters(rr));
//		} catch (Exception e) {
//			// No report in unit tests, guys
//		}
//	}

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

		assertTrue("A Solution must be able to retain from a Collection", testSolution.retainAll(ll));

		assertFalse(testSolution.contains(foo1));
		assertFalse(testSolution.contains(foo2));
		assertTrue(testSolution.contains(foo0));
	}




}