package fr.insa.rennes.info.chemical.backend;

import java.util.List;



public final class SubSolution<T extends SubSolutionReactivesAccessor> implements SubSolutionReactivesAccessor {
	private T _element;

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
