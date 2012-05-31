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

import java.util.List;

import fr.insa.rennes.info.chemical.user.ReactionRule;



/**
 * This class implements {@link SubSolutionReagentsAccessor} and is part of a recursive structure constructed from {@link SubSolution} and
 * {@link SubSolutionElements} objects. The parameterized type of this class has to be one of those two types.<br />
 * This class is meant to be used as attribute (i.e as reagent) in an implementation class of {@link ReactionRule}, in order to specify
 * that the reaction rule needs reagents that are in an inner solution.<br />
 * For example, to find elements in a sub solution, use <code>SubSolution&lt;SubSolutionElements&gt;</code>; and to find elements in a sub-sub-solution, use
 * <code>SubSolution&lt;SubSolution&lt;SubSolutionElements&gt;&gt;</code> as attribute in a reaction rule class. 
 * <br />
 * All the functions of this class execute a simple recursive call. For example, <code>this.getElements()</code>
 * is <code>return this.parameterized_type.getElements()</code>.
 * 
 * @author Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 *
 * @param <T> A {@link SubSolutionReagentsAccessor} object: it has to be either {@link SubSolution} or {@link SubSolutionElements}.
 */
public final class SubSolution<T extends SubSolutionReagentsAccessor> implements SubSolutionReagentsAccessor {
	/**
	 * An object implementing {@link SubSolutionReagentsAccessor}
	 */
	private T _element;
	
	/**
	 * Default constructor. Automatically builds the inner object characterized by the parameterized type.
	 */
	public SubSolution(){
		if(_element instanceof SubSolution) {
			_element = (T) new SubSolution();
		} else {
			_element = (T) new SubSolutionElements();
		}
	}
	
	/**
	 * Builds a SubSolution object, with the specified {@link SubSolutionReagentsAccessor} implementation object.
	 * @param e the {@link SubSolutionReagentsAccessor} implementation object.
	 */
	public SubSolution(T e){
		_element = e;
	}
	
	public List<Object> getElements(){
		return _element.getElements();
	}

	public List<Class<? extends Object>> getTypeList() {
		return _element.getTypeList();
	}

	public void setElements(List<Object> l) {
		_element.setElements(l);
	}

	public void setTypeList(List<Class<? extends Object>> l) {
		_element.setTypeList(l);
	}

	public Solution getSolution() {
		return _element.getSolution();
	}

	public void setSolution(Solution s) {
		_element.setSolution(s);
	}

	public void addType(Class<? extends Object> type) {
		_element.addType(type);
	}


}