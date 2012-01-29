package fr.insa.rennes.info.chemical.backend;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.text.html.HTMLDocument.Iterator;


/**
 * This class implements an random strategy: the elements are randomly selected in order
 * to simulate a chemical reaction.
 * @author Cédric Andreolli, Chloé Boulanger, Olivier Cléro, Antoine Guellier, Sébastien Guilloux, Arthur Templé
 */
class RandomIncrementStrategyBis implements IncrementStrategyBis {
	/**
	 * This interface is used to get an association between a real index and a false index.
	 * It's designed to work with a List<IndexProviderElement>
	 * @author Cédric Andreolli, Chloé Boulanger, Olivier Cléro, Antoine Guellier, Sébastien Guilloux, Arthur Templé
	 *
	 */
//	public interface PermutElement{
//		/**
//		 * Shuffle the list
//		 */
//		void shuffle();
//		/**
//		 * Get the list of associations between values of the List<IndexProviderElement> and
//		 * the random generated values.
//		 * @return The list of associations to translate the values of the List<IndexProviderElement>
//		 */
//		List<Integer> getAssociations();
//		int get(int i);
//	}
//	public class PermutSimpleElement implements PermutElement{
//		private List<Integer> _listIntegers;
//		
//		/**
//		 * Constructor
//		 * @param n The number of elements you want to permute
//		 */
//		public PermutSimpleElement(IndexProviderSimpleElement e){
//			_listIntegers = new ArrayList<Integer>();
//			for(int i=0; i<e.get_nummberElementsInSolution(); i++)
//				_listIntegers.add(i);
//		}
//		
//		public void shuffle() {
//			Collections.shuffle(_listIntegers);
//		}
//
//		public List<Integer> getAssociations() {
//			return _listIntegers;
//		}
//
//		public int get(int i) {
//			return _listIntegers.get(i);
//		}
//		
//	}
//	public class PermutCompounedElement implements PermutElement{
//		private List<Integer> _listIntegers;
//		private List<PermutElement> _listElements;
//		public PermutCompounedElement(IndexProviderSubSolution e) throws ChemicalException{
//			_listIntegers = new ArrayList<Integer>();
//			_listElements = new ArrayList<PermutElement>();
//			PermutElementFactory factory = new PermutElementFactory();
//			for(int i=0; i<e.get_listElements().size(); i++){
//				_listIntegers.add(i);
//				_listElements.add(factory.buildElement(e.get_listElements().get(i)));
//			}
//		}
//		
//		public void shuffle() {
//			Collections.shuffle(_listIntegers);
//			for(PermutElement p : _listElements)
//				p.shuffle();
//		}
//
//		public List<Integer> getAssociations() {
//			return _listIntegers;
//		}
//		
//		public int get(int i) {
//			System.out.print("On traduit "+i+" par "+_listIntegers.get(i)+" - ");
//			return _listIntegers.get(i);
//		}
//		
//		public PermutElement getElement(int i){
//			return _listElements.get(i);
//		}
//	}
//	
//	public class PermutElementFactory{
//		public PermutSimpleElement buildElement(IndexProviderSimpleElement e){
//			return new PermutSimpleElement(e);
//		}
//		
//		public PermutCompounedElement buildElement(IndexProviderSubSolution e) throws ChemicalException{
//			return new PermutCompounedElement(e);
//		}
//		
//		public PermutElement buildElement(IndexProviderElement e) throws ChemicalException{
//			if(e instanceof IndexProviderSimpleElement)
//				return buildElement((IndexProviderSimpleElement)e);
//			else if(e instanceof IndexProviderSubSolution)
//				return buildElement((IndexProviderSubSolution)e);
//			throw new ChemicalException("Unrecognized type in buildElement");
//		}
//	}
//
//	
	/**
	 * A boolean needed for the first execution of increment
	 */
	boolean firstRun = true;
	private BigInteger _currentIndex;
	private BigInteger _numberOfIndex;
	private List<BigInteger> _shuffleList;
	/**
	 * Sole constructor, with the maxIndex array that is needed to know how many 
	 * columns there is for this index provider.
	 * @param maxIndex The maximum index array
	 */
	public RandomIncrementStrategyBis(List<IndexProviderElement> index){
		_currentIndex = BigInteger.valueOf(0);
		_numberOfIndex = BigInteger.valueOf(1);
		for(IndexProviderElement e : index){
			_numberOfIndex = _numberOfIndex.multiply(e.getNumberOfElements());
		}
		_shuffleList = new ArrayList<BigInteger>();
		
		for(BigInteger i= BigInteger.valueOf(0); _numberOfIndex.add(i.negate()).signum()>0; i=i.add(BigInteger.valueOf(1))){
			_shuffleList.add(new BigInteger(i.toByteArray()));
		}
		Collections.shuffle(_shuffleList);
		System.out.println("Number of element : "+_numberOfIndex);
	}
	
	/**
	 * The function is pretty much the same as the one in the OrderedIncrementStrategy class
	 * but it "translates" the ordered value to the "random" value with the map
	 * @param _index The index array: the values of the index
	 * @param _maxIndex The maximum values of the indexes
	 * @return A table of integers that is the new value of the indexes
	 * @throws ChemicalException
	 */	
	public List<List<Integer>> increment(List<IndexProviderElement> list) throws ChemicalException{
		for(IndexProviderElement e : list){
			e.init();
		}
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		java.util.Iterator<BigInteger> it = _shuffleList.iterator();
		BigInteger position = BigInteger.valueOf(0);
		BigInteger i = BigInteger.valueOf(0);
		if(_currentIndex.equals(_numberOfIndex))
			throw new ChemicalException("Overflow reached first");
		while(it.hasNext()){
			if(i.equals(_currentIndex)){
				_currentIndex = _currentIndex.add(BigInteger.valueOf(1));
				position = it.next();
				break;
			}
			
			i=i.add(BigInteger.valueOf(1));
			it.next();
		}
		System.err.println("Position : "+position);
		for(i=BigInteger.valueOf(0); position.add(i.negate()).signum()>0; i=i.add(BigInteger.valueOf(1))){
			list = incrementOnce(list);
		}
		for(int ii=0; ii<list.size(); ii++){
			List<Integer> l = new ArrayList<Integer>();
			IndexProviderElement element = list.get(ii);
			while(element instanceof IndexProviderSubSolution){
				l.add(((IndexProviderSubSolution)element).getValue());
				element = ((IndexProviderSubSolution)element).getElement();
			}
			l.add(element.getValue());
			result.add(l);
		}
		return result;
	}
	
	List<IndexProviderElement> incrementOnce(List<IndexProviderElement> list) throws ChemicalException{
		int i=0;
		//Just increment the list of IndexProviderElements
		while(i<list.size() && list.get(i).increment()){
			i++;
		}
		if(i==list.size())
			throw new ChemicalException("Overflow reached");
		return list;
	}

}
