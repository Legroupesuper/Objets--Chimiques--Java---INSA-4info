package fr.insa.rennes.info.chemical.backend;

import java.util.List;

import fr.insa.rennes.info.chemical.user.ReactionRule;

/**
 * This interface provides an access to reactives within an inner solution.
 * This type of object implementing this interface should appear as 
 * an attribute of a reaction rule (see {@link ReactionRule}), and allows 
 * the user of the library to select reactives in inner solutions. For more details, see the two implementations 
 * {@link SubSolution} and {@link SubSolutionElements}.
 * @author Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 */
interface SubSolutionReactivesAccessor {
	/**
	 * Getter for the concerned inner solution. Returns the inner solution
	 * in which the elements are. Meant to be used by the user.
	 * @return The inner solution containing the selected reactives.
	 */
	public Solution getSolution();
	/**
	 * Setter for the concerned solution. Meant to be used internally (not by the user), 
	 * in order to set the solution in which the reactives are found (if they were found). 
	 * @param s The concerned solution
	 */
	public void setSolution(Solution s);
	/**
	 * Setter for the type list. This type list correspond to the desired reactives in the any inner solution,
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
	 * Returns the type list, the list of the types of the desired reactives. This function
	 * is meant to be called internally (not by the user), in order to know the desired types
	 * of reactives.
	 * @return the list of types
	 */
	public List<Class<? extends Object>> getTypeList();
	/**
	 * Returns the set of elements/reactives - if the desired types were found in the inner solution.
	 * This method is meant to be called by the user in {@link ReactionRule#computeResult()} or {@link ReactionRule#computeSelect()}.
	 * @return the set of elements/reactives corresponding to the types specified with {@link #setTypeList(List)}.
	 */
	public List<Object> getElements();
	/**
	 * Sets the elements/reactives, when found. This method is used internally.
	 * @param l the list of elements/reactives found.
	 */
	public void setElements(List<Object> l);
}
