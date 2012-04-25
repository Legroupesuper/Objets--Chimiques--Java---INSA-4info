package fr.insa.rennes.info.chemical.backend;

import java.util.List;

import fr.insa.rennes.info.chemical.user.ReactionRule;



/**
 * This class implements {@link SubSolutionReactivesAccessor} and is part of a recursive structure constructed from {@link SubSolution} and
 * {@link SubSolutionElements} objects. The parameterized type of this class has to be one of those two types.<br />
 * This class is meant to be used as attribute (i.e as reactive) in an implementation class of {@link ReactionRule}, in order to specify
 * that the reaction rule needs reactives that are in an inner solution.<br />
 * For example, to find elements in a sub solution, use <code>SubSolution&lt;SubSolutionElements&gt;</code>; and to find elements in a sub-sub-solution, use
 * <code>SubSolution&lt;SubSolution&lt;SubSolutionElements&gt;&gt;</code> as attribute in a reaction rule class. 
 * <br />
 * All the functions of this class execute a simple recursive call. For example, <code>this.getElements()</code>
 * is <code>return this.parameterized_type.getElements()</code>.
 * 
 * @author Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 *
 * @param <T> A {@link SubSolutionReactivesAccessor} object: it has to be either {@link SubSolution} or {@link SubSolutionElements}.
 */
public final class SubSolution<T extends SubSolutionReactivesAccessor> implements SubSolutionReactivesAccessor {
	/**
	 * An object implementing {@link SubSolutionReactivesAccessor}
	 */
	private T _element;
	
	/**
	 * Builds a SubSolution object, with the specified {@link SubSolutionReactivesAccessor} implementation object.
	 * @param e the {@link SubSolutionReactivesAccessor} implementation object.
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


}
