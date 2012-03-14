package fr.insa.rennes.info.chemical.backend;

import java.math.BigInteger;

import fr.insa.rennes.info.chemical.backend.Solution.Strategy;



/**
 * This class is used to obtain an index counter.
 * We use this class during the reactives research process to try every cases (every n-uplet) :
 * an index in the _index array corresponds to an index in the main HashMap
 * in the class Solution.
 * @author Cédric Andreolli, Chloé Boulanger, Olivier Cléro, Antoine Guellier, Sébastien Guilloux, Arthur Templé
 */
class IndexProvider {
	private SubIndexProviderSolution _indexProviderSubSolution;
	private IncrementStrategy _strategy;
	private boolean _overflowReached;
	
	public IndexProvider(SubIndexProviderSolution ipss, Strategy s) throws ChemicalException {
		super();
		this._indexProviderSubSolution = ipss;
		_overflowReached = false;
		if (s==Strategy.RANDOM){
			_strategy = new RandomIncrementStrategy(ipss);
		}
		else {
			_strategy = new RandomIncrementStrategy(ipss);
		}
		
		_indexProviderSubSolution.init();
		while(!_indexProviderSubSolution.isValid()){
			increment();
			if(_overflowReached)
				throw new ChemicalException("It's not possible to create the IndexProvider");
		}
	}
	
	public SubIndexProviderSolution getSubSolution(){
		return _indexProviderSubSolution;
	}
	
	public IndexProvider(SubIndexProviderSolution solution) throws ChemicalException {
		this(solution, Strategy.RANDOM);
	}
	
	public SubIndexProviderSolution increment(){		
		do{
			try{
				_indexProviderSubSolution = _strategy.increment(_indexProviderSubSolution);
			} catch (ChemicalException e) {
				_overflowReached = true;
				return null;
			}
		}while(!_indexProviderSubSolution.isValid());
		
		return _indexProviderSubSolution;
	}
	
	
	public String toString(){
		return _indexProviderSubSolution.toString();
	}
	
	public boolean is_overflowReached(){
		return _overflowReached;
	}
	
	public BigInteger getNumberOfElements(){
		return _indexProviderSubSolution.getNumberOfElements();
	}
	
	/*public static void main(String[] args) {
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
}*/
}
