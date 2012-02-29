package fr.insa.rennes.info.chemical.backend;

import java.util.List;



public final class SubSolution<T extends ChemicalElement> implements ChemicalElement {
	private T _element;

	public SubSolution(T e){
		_element = e;
	}
	
	public List<Object> getElementList(){
		return _element.getElementList();
	}

	public List<Class<? extends Object>> getTypeList() {
		return _element.getTypeList();
	}

	public void setElementList(List<Object> l) {
		_element.setElementList(l);
	}

	public void setTypeList(List<Class<? extends Object>> l) {
		_element.setTypeList(l);
	}


}
