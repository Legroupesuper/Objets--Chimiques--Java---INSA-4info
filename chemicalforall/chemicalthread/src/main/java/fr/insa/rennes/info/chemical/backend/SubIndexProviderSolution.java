/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChemicalLibSuper.

    ChemicalLibSuper is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChemicalLibSuper is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with ChemicalLibSuper.  If not, see <http://www.gnu.org/licenses/>
*/
package fr.insa.rennes.info.chemical.backend;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This class represents a sub index provider on a solution. That is to say that it does not simply
 * iterates over 1 reagent, but several reagents. Moreover, it can also iterate on reagents in inner
 * solutions, using a recursive structure (based on {@link SubIndexProviderSolution} and 
 * {@link SubIndexProviderElement}). Like all index providers, this sub index provider iterates
 * overs elements in a given solution that we will call <em>main solution</em>. Note that a main
 * solution can also be another solution's inner solution.<br />
 * </p>
 * <p>
 * On contrary to a sub index provider on a simple element, this sub index provider has 
 * a quite complex structure. Basically, it handles a list of list of sub index providers.<br />
 * In the first list, each entry corresponds to a sub solution in the main solution. 
 * For each one of these sub solution, a list of sub index provider is given. In the basic case, 
 * this list contains only instances of the {@link SubIndexProviderElement} class, 
 * one for each type of reagent searched in the sub solution. For example, if a reaction rule
 * is requesting 2 integers and a string in a sub solution, if we consider that the main 
 * solution contains 2 sub solution, the list of list will be: <br />
 * [ [SubIndexProviderElement(int), SubIndexProvider(int), SubIndexProvider(string)], [SubIndexProviderElement(int), SubIndexProvider(int), SubIndexProvider(string)] ]<br />
 * In the general case, one or several {@link SubIndexProviderSolution} objects can be in the list. 
 * This case occurs when searching for elements in sub-sub-solutions (or deeper).
 * The recursion then appears and everything is transparent for this sub index provider:
 * the function called on the sub index providers of the lists is {@link SubIndexProvider#increment()},
 * and it will be called until it reaches an overflow. The implementation does not matter.<br /> 
 * </p>
 * <p>
 * With this structure, this sub index provider is able to try EVERY combination
 * of reagents for the reaction rule. The first level of the list of list of sub index provider
 * is taken in order (that is to say, the sub solutions are considered one after the other), and
 * each element in the second level is incremented (with {@link SubIndexProvider#increment()})
 * in the right order, until all the sub index provider reach the overflow. Then the next sub solution
 * (next entry in the first level list) is tried, and so on. The overflow of this sub index provider
 * is detected when all the sub solution were tested.
 * </p>
 * @author Cédric Andreolli, Chloé Boulanger, Olivier Cléro, Antoine Guellier, Sébastien Guilloux, Arthur Templé
 *
 */
public class SubIndexProviderSolution implements SubIndexProvider{
	/**
	 * The main structure of the class: the list of list of sub index provider.
	 * In the first list, each entry corresponds to a sub solution in the main solution. 
	 * For each one of these sub solution, there is a list of sub index provider. See
	 * the class description for more details.
	 */
	private List<List<SubIndexProvider>> _listSubIP;
	/**
	 * Index of the first level of the list: index of the currently tested 
	 * sub solution.
	 */
	private int _currentSubSol;
	/**
	 * Index of the second level list: index of the currently incremented
	 * sub index provider.
	 */
	private int _currentSubIP;
	/**
	 * The list of dependent indexes. Two indexes are said dependent when they are
	 *  pointing on the same type of object, in the same solution. The structure
	 *  is a list of list of integer because the is several set of dependent indexes.
	 */
	private List<List<Integer>> _dependentIndexes;

	/**
	 * Builds a sub index provider on a solution, with the given list of list of sub index provider,  
	 * and the specified dependent indexes.
	 * @param listSIP The list of list of sub index provider (already fully built)
	 * @param dependentIndexes The dependent indexes. The structure is a list of list of integer because the is several set of dependent indexes
	 */
	public SubIndexProviderSolution(List<List<SubIndexProvider>> listSIP, List<List<Integer>> dependentIndexes) {
		super();
		this._listSubIP = listSIP;
		this._dependentIndexes = dependentIndexes;
	}

	/**
	 * Returns the list of sub index providers of the currently
	 * tested sub solution.
	 * @return The list of sub index providers of the currently tested sub solution.
	 */
	public List<SubIndexProvider> get_listSubIP() {
		return _listSubIP.get(_currentSubSol);
	}

	/**
	 * Initializes the sub index provider. Sets the 
	 * lists indexes to 0, and call {@link SubIndexProvider#init()}
	 * on every sub index provider of the list.
	 */
	public void init() {
		_currentSubIP = 0;
		_currentSubSol = 0;
		for(List<SubIndexProvider> l : _listSubIP)
			for(SubIndexProvider e : l)
				e.init();
	}

	/**
	 * Returns the index of the current sub solution tested.
	 * @return The index of the current sub solution tested.
	 */
	public int getValue() {
		return _currentSubSol;
	}

	/**
	 * Increments the configuration of the indexes by one step.
	 * Tries to increment the sub index provider at [0] of the list [_currentSubSol],
	 * and if an overflow is detected on this sub index provider, it is is reported on the next sub 
	 * index provider (just like a carry).
	 * When the "carry" has been reported correctly (that may take several steps/increments), 
	 * just return false.When the carry can not be reported (_currentSubSol > number of sub solutions),
	 * that means a "general" overflow is detected: this sub index provider raises an overflow, and 
	 * this function returns <code>true</code>
	 * @return <code>true</code> if an overflow was reached.
	 */
	public boolean increment() {
		boolean overflow = false;

		/*
		 * The following loop tries to increment the sub index provider at [0] (_currentSubIP is always 0 
		 * when entering this function)of the list [_currentSubSol], and if an overflow is detected, 
		 * the overflow is reported on the next sub index provider (just like a carry).
		 * The condition of the loop fails when no overflow is detected or when the _currentSubIP is
		 * greater than number of elements in the _currentSubSol.
		 */
		while(_currentSubIP < _listSubIP.get(_currentSubSol).size() && (overflow = _listSubIP.get(_currentSubSol).get(_currentSubIP).increment())
				){
			_currentSubIP++;
		}

		/*
		 * When an overflow is detected there are some configurations to check :
		 * 	- can we try to increment _currentSubSol (hat means try another sub solution)
		 *  - if we already are on the last element of the first level list, we must return an overflow (there is no more sub solution to test).
		 *  otherwise, no overflow is detected and the next call to increment will try to increment one more time.
		 *  This algorithm work like the +1 algorithm. You always start to increment the least significant number.
		 */
		if(overflow && _currentSubSol<_listSubIP.size()-1){//Increment the first level list
			_currentSubSol++;
			_currentSubIP=0;
			return false;
		}else if(overflow){//Real overflow
			this.init();
			return true;
		}else{//It's not an overflow at any level
			_currentSubIP = 0;
			return false;
		}
	}

	/**
	 * Constructs and returns a string representing the sub index provider. The string 
	 * representation consists in a list of the lists of sub index provider. 
	 * Each sub list is enclosed in square brackets ("[]") and begins with
	 * its range in the first level list; each sub index provider 
	 * is transformed in string with <code>toString()</code>.
	 * @return A string representing the sub index provider
	 */
	public String toString(){
		String result = "";
		int i = 0;
		for(List<SubIndexProvider> lSip : _listSubIP) {
			result += "{ "+i+" : ";
			for(SubIndexProvider sip : lSip) {
				result += sip.toString();
			}
			result += "}";

			i++;
		}
		return result;
	}


	/**
	 * Computes the number of different combination of reagents possible.
	 * This number can be consequent (thus the {@link BigInteger} value returned).
	 * Basically, this function multiplies the value returned by
	 * {@link SubIndexProvider#getNumberOfElements()} for each sub index provider,
	 * and sums these values for all the sub solutions.
	 * @return the number of different combination of reagents possible
	 */
	public BigInteger getNumberOfElements() {
		BigInteger result = BigInteger.valueOf(0);

		for(List<SubIndexProvider> l : _listSubIP){
			BigInteger aux = BigInteger.valueOf(1);
			for(SubIndexProvider e : l){
				try{
					aux = aux.
							multiply(
									e.getNumberOfElements());
				}catch(Exception ex){
					return BigInteger.valueOf(0);
				}
			}
			result = result.add(aux);
		}
		return result;
	}
	
	/**
	 * Computes the number of increments that this sub index provider can (and must) perform.
	 * This number may differ from the number returned by {@link #getNumberOfElements()}.
	 * This number can be consequent (thus the {@link BigInteger} value returned).
	 * Basically, this function multiplies the value returned by
	 * {@link SubIndexProvider#getNumberOfIncrements()} for each sub index provider,
	 * and sums these values for all the sub solutions.
	 * @return the number of increments that this sub index provider can (and must) perform.
	 */
	public BigInteger getNumberOfIncrements() {
		BigInteger result = BigInteger.valueOf(0);

		for(List<SubIndexProvider> l : _listSubIP){
			BigInteger aux = BigInteger.valueOf(1);
			for(SubIndexProvider e : l){
				try{
					aux = aux.
							multiply(
									e.getNumberOfIncrements());
				}catch(Exception ex){
					return BigInteger.valueOf(0);
				}
			}
			result = result.add(aux);
		}
		return result;
	}

	/**
	 * Returns <code>true</code> if the current state of the sub index provider is valid.
	 * A valid state is a state where there is no conflict between two dependent indexes. Two indexes
	 * are said dependent when they are pointing on the same type of object, in the same sub solution.
	 * For example, an index provider having two indexes pointing on the same integer in a given sub solution
	 * can not be valid (this integer would be considered as two integers in the reaction !).
	 * @return <code>true</code> if the current combination of the sub index provider is valid.
	 */
	public boolean isValid() {
		List<Integer> valuesIndexProvider;
		boolean isCurrentIndexValid;

		for(SubIndexProvider e : _listSubIP.get(_currentSubSol)){
			if(!e.isValid())
				return false;
		}
		System.out.println("Pour sip : "+_listSubIP.get(_currentSubSol));
		System.out.println("depIndList : "+_dependentIndexes);
		/*System.out.println("_currentSubSol/max = "+_currentSubSol+"/"+_listSubIP.size());*/
		
		for(List<Integer> l : _dependentIndexes){
			valuesIndexProvider = new ArrayList<Integer>();
			isCurrentIndexValid = true;
			//TODO : Vérifier le l != null
			
			if(l!=null){
				for(int n : l){
					if(valuesIndexProvider.contains(_listSubIP.
							get(_currentSubSol).
							get(n).getValue())){
						isCurrentIndexValid = false;
						break;
					}else{
						valuesIndexProvider.add(_listSubIP.get(_currentSubSol).get(n).getValue());
					}
				}
				if(!isCurrentIndexValid){

					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Merges two sub index provider on a solution in one. Used when
	 * building the whole index provider structure. Actually
	 * this functions just operates a merge of the lists (of lists) of 
	 * sub index providers.
	 * @param sipSol The sub index provider on a solution that needs to be merged with this sub index provider.
	 */
	public void merge(SubIndexProviderSolution sipSol) {
		System.err.println("Merge de "+this+" ("+this._dependentIndexes+")");
		System.err.println("\t et "+sipSol+" ("+sipSol._dependentIndexes+")");
		
		
		_listSubIP.addAll(sipSol._listSubIP);
		_dependentIndexes.addAll(sipSol._dependentIndexes);
		
		System.err.println("Resultat "+this+" ("+this._dependentIndexes+")");
	}
}