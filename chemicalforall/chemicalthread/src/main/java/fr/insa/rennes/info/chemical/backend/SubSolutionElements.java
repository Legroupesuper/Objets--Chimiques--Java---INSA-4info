/* 
	Copyright (C) 2012 Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

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

import java.util.List;

/**
 * This class implements {@link SubSolutionReagentsAccessor}, and is actually the bottom of the recursive structure formed with an overlapping of
 * {@link SubSolution} instances. This class really contains the type list (set by the user), the element list (set internally) and the 
 * reference of the sub solution object containing the elements/reagents selected. This class is not supposed to be used alone, it should
 * be within a {@link SubSolution} object, as parameterized type.
 * 
 * @author Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 */
public final class SubSolutionElements implements SubSolutionReagentsAccessor{
	/**
	 * The list of elements/reagents.
	 */
	private List<Object> _elements;
	/**
	 * The inner solution, that contains the elements of {@link #_elements}.
	 */
	private Solution _containingSolution;
	/**
	 * The type list, giving the types of the desired reagents by the user.
	 */
	private List<Class<? extends Object>> _types;

	public List<Object> getElements(){
		return _elements;
	}

	public List<Class<? extends Object>> getTypeList() {
		return _types;
	}

	public void setElements(List<Object> l) {
		_elements = l;
	}

	public void setTypeList(List<Class<? extends Object>> l) {
		_types = l;
	}
	
	public void setSolution(Solution s) {
		_containingSolution = s;
	}
	
	public Solution getSolution() {
		return _containingSolution;
	}


}