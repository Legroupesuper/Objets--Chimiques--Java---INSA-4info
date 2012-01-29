package fr.insa.rennes.info.chemical.backend;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import fr.insa.rennes.info.chemical.backend.Solution.Strategy;



/**
 * This class is used to obtain an index counter.
 * We use this class during the reactives research process to try every cases (every n-uplet) :
 * an index in the _index array corresponds to an index in the main HashMap
 * in the class Solution.
 * @author Cédric Andreolli, Chloé Boulanger, Olivier Cléro, Antoine Guellier, Sébastien Guilloux, Arthur Templé
 */
class IndexProviderBis {
	private List<IndexProviderElement> _listElements;
	private List<List<Integer>> _dependantIndexes;
	private IncrementStrategyBis _strategy;
	private boolean _overflowReached;
	public IndexProviderBis(List<IndexProviderElement> listElements, List<List<Integer>> dependantIndexes, Strategy s) {
		super();
		this._listElements = listElements;
		_dependantIndexes = dependantIndexes;
		_overflowReached = false;
		if (s==Strategy.RANDOM){
			_strategy = new RandomIncrementStrategyBis(listElements);
		}
		else {
			_strategy = new RandomIncrementStrategyBis(listElements);
		}
		
		for(IndexProviderElement e : _listElements){
			e.init();
		}
	}
	
	public IndexProviderBis(List<IndexProviderElement> listElements) {
		super();
		this._listElements = listElements;
		for(IndexProviderElement e : _listElements){
			e.init();
		}
	}
	
	public List<List<Integer>> increment(){
		List<List<Integer>> result;
		do{
			try{
				result = _strategy.increment(_listElements);
			} catch (ChemicalException e) {
				System.err.println("Overflow reached : "+e.getMessage());
				_overflowReached = true;
				return null;
			}
		}while(!checkForDuplicates(result));
		return result;
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
		IndexProviderSubSolution ss1 = new IndexProviderSubSolution(temp);

		
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
		IndexProviderSubSolution solution = new IndexProviderSubSolution(ltemp);
		IndexProviderBis index = new IndexProviderBis(l, tab, Strategy.RANDOM);

		int i=0;
			System.out.println(solution.getNumberOfElements().intValue());
			while(i<solution.getNumberOfElements().intValue()){
				
				//List<List<Integer>> resTab = index.increment();		
//				System.out.println("************");
//				System.out.println(index.toString());
//				System.out.println(resTab);
				System.out.println(solution);
				solution.increment();
				System.out.println("i="+i);
				i++;
			}	
	}
	
	private boolean checkForDuplicates(List<List<Integer>> result) {
		List<Integer> valuesIndexProvider;
		boolean isCurrentIndexValid;
		for(List<Integer> l : _dependantIndexes){
			valuesIndexProvider = new ArrayList<Integer>();
			isCurrentIndexValid = true;

			for(int n : l){

				if(valuesIndexProvider.contains(_listElements.get(n).getValue())){
					isCurrentIndexValid = false;
					break;
				}else{
					valuesIndexProvider.add(_listElements.get(n).getValue());
				}
			}
			if(!isCurrentIndexValid){
				return false;
			}
		}
		return true;
	}

	public String toString(){
		String result = "";
		for(int i=0; i<_listElements.size(); i++){
			result += _listElements.get(i).toString() + " - ";
		}
		return result;
	}
	
	boolean is_overflowReached(){
		return _overflowReached;
	}
	
	public BigInteger getNumberOfElements(){
		BigInteger numberOfIndex = BigInteger.valueOf(1);
		for(IndexProviderElement e : _listElements){
			numberOfIndex = numberOfIndex.multiply(e.getNumberOfElements());
		}
		return numberOfIndex;
	}
}
