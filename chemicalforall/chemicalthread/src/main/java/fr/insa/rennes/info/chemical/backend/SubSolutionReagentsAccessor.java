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

import fr.insa.rennes.info.chemical.user.ReactionRule;

/**
 * This interface provides an access to reagents within an inner solution.
 * This type of object implementing this interface should appear as 
 * an attribute of a reaction rule (see {@link ReactionRule}), and allows 
 * the user of the library to select reagents in inner solutions. For more details, see the two implementations 
 * {@link SubSolution} and {@link SubSolutionElements}.
 * @author Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 */
interface SubSolutionReagentsAccessor {
	/**
	 * Getter for the concerned inner solution. Returns the inner solution
	 * in which the elements are. Meant to be used by the user.
	 * @return The inner solution containing the selected reagents.
	 */
	public Solution getSolution();
	/**
	 * Setter for the concerned solution. Meant to be used internally (not by the user), 
	 * in order to set the solution in which the reagents are found (if they were found). 
	 * @param s The concerned solution
	 */
	public void setSolution(Solution s);
	/**
	 * Setter for the type list. This type list correspond to the desired reagents in the any inner solution,
	 * and it is set by the user in the constructor of its reaction rule class.<br />
	 * Example of parameter for this function: if you want to request an integer, a string and 
	 * an instance of the class "MyObject", you should call this function with the following parameter.<br />
	 * <code>
	 * List<Class> l = new LinkedList<Class>();<br />
	 * l.add(Integer.class, String.class, MyObject.class);
	 * </code>
	 * @param l The list of types, specified by the user.
	 */
	public void setTypeList(List<Class<? extends Object>> l);
	/**
	 * Returns the type list, the list of the types of the desired reagents. This function
	 * is meant to be called internally (not by the user), in order to know the desired types
	 * of reagents.
	 * @return the list of types
	 */
	public List<Class<? extends Object>> getTypeList();
	/**
	 * Returns the set of elements/reagents - if the desired types were found in the inner solution.
	 * This method is meant to be called by the user in {@link ReactionRule#computeResult()} or {@link ReactionRule#computeSelect()}.
	 * @return the set of elements/reagents corresponding to the types specified with {@link #setTypeList(List)}.
	 */
	public List<Object> getElements();
	/**
	 * Sets the elements/reagents, when found. This method is used internally.
	 * @param l the list of elements/reagents found.
	 */
	public void setElements(List<Object> l);
}