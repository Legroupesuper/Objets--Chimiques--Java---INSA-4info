package fr.insa.rennes.info.chemical.backend;

import java.util.List;

/**
 * This class implements {@link SubSolutionReactivesAccessor}, and is actually the bottom of the recursive structure formed with an overlapping of
 * {@link SubSolution} instances. This class really contains the type list (set by the user), the element list (set internally) and the 
 * reference of the sub solution object containing the elements/reactives selected. This class is not supposed to be used alone, it should
 * be within a {@link SubSolution} object, as parameterized type.
 * 
 * @author Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 */
public final class SubSolutionElements implements SubSolutionReactivesAccessor{
	/**
	 * The list of elements/reactives.
	 */
	private List<Object> _elements;
	/**
	 * The inner solution, that contains the elements of {@link #_elements}.
	 */
	private Solution _containingSolution;
	/**
	 * The type list, giving the types of the desired reactives by the user.
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
