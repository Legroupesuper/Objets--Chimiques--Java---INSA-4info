package fr.insa.rennes.info.chemical.backend;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.naming.LimitExceededException;

import fr.insa.rennes.info.chemical.backend.Solution.Strategy;



/**
 * This class is used to obtain an index counter.
 * We use this class during the reactives research process to try every cases (every n-uplet) :
 * an index in the _index array corresponds to an index in the main HashMap
 * in the class Solution.
 * @author Cédric Andreolli, Chloé Boulanger, Olivier Cléro, Antoine Guellier, Sébastien Guilloux, Arthur Templé
 */
class IndexProvider {
	private IndexProviderSubSolution _solution;
	private IncrementStrategy _strategy;
	private boolean _overflowReached;
	
	public IndexProvider(IndexProviderSubSolution solution, Strategy s) throws ChemicalException {
		super();
		this._solution = solution;
		_overflowReached = false;
		if (s==Strategy.RANDOM){
			_strategy = new RandomIncrementStrategy(solution);
		}
		else {
			_strategy = new RandomIncrementStrategy(solution);
		}
		
		_solution.init();
		while(!_solution.isValid()){
			increment();
			if(_overflowReached)
				throw new ChemicalException("It's not possible to create the IndexProvider");
		}
	}
	
	IndexProviderSubSolution getSubSolution(){
		return _solution;
	}
	public IndexProvider(IndexProviderSubSolution solution) throws ChemicalException {
		this(solution, Strategy.RANDOM);
	}
	
	public IndexProviderSubSolution increment(){		
		do{
			try{
				_solution = _strategy.increment(_solution);
			} catch (ChemicalException e) {
				_overflowReached = true;
				System.err.println(e.getMessage());
				return null;
			}
		}while(!_solution.isValid());
		return _solution;
	}
	
	
	public static void main(String[] args) {
		IndexProviderSimpleElement se1 = new IndexProviderSimpleElement(3);
		IndexProviderSimpleElement se2 = new IndexProviderSimpleElement(2);
		IndexProviderSimpleElement se3 = new IndexProviderSimpleElement(2);
		IndexProviderSimpleElement se4 = new IndexProviderSimpleElement(4);
		IndexProviderSimpleElement se5 = new IndexProviderSimpleElement(6);
		List<IndexProviderElement> l = new LinkedList<IndexProviderElement>();
		
		List<IndexProviderElement> l1 = new LinkedList<IndexProviderElement>();
		l1.add(se3);
		l1.add(se4);
		List<IndexProviderElement> l2 = new LinkedList<IndexProviderElement>();
		l2.add(se3);
		l2.add(se5);
		List<List<IndexProviderElement>> temp = new ArrayList<List<IndexProviderElement>>();
		temp.add(l1);
		temp.add(l2);
		
		List<List<Integer>> lincomp = new ArrayList<List<Integer>>();
		List<List<Integer>> lincomp2 = new ArrayList<List<Integer>>();
		List<Integer> lbis = new ArrayList<Integer>();
		lbis.add(0);
		lbis.add(1);
		List<Integer> lbis2 = new ArrayList<Integer>();
		lbis2.add(0);
		lbis2.add(2);
		
		lincomp.add(lbis);
		lincomp2.add(lbis2);
		IndexProviderSubSolution ss1 = new IndexProviderSubSolution(temp, lincomp);

		
		l.add(se1);
		l.add(se2);		
		l.add(ss1);

		
		List<List<Integer>>tab = new ArrayList<List<Integer>>();
		List<Integer> li = new ArrayList<Integer>();
		li.add(0);
		li.add(1);
		tab.add(li);
		List<List<IndexProviderElement>> ltemp = new ArrayList<List<IndexProviderElement>>();
		ltemp.add(l);
		IndexProviderSubSolution solution = new IndexProviderSubSolution(ltemp, lincomp2);
		IndexProvider index = null;
		try {
			index = new IndexProvider(solution, Strategy.RANDOM);
		} catch (ChemicalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int i=0;
		while(!index.is_overflowReached()){
			index.increment();
		}
	}
	
	
	public String toString(){
		String result = "";
		return _solution.toString();
	}
	
	boolean is_overflowReached(){
		return _overflowReached;
	}
	
	public BigInteger getNumberOfElements(){
		return _solution.getNumberOfElements();
	}
}
