package fr.insa.rennes.info.chemical.backend;

import java.util.List;

public class SubSolutionElements implements SubSolutionReactivesAccessor{
	private List<Object> _elements;
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



}
