package fr.insa.rennes.info.chemical.backend;

import java.util.List;

public class ElementList implements ChemicalElement{
	private List<Object> _elements;
	private List<Class<? extends Object>> _types;

	public List<Object> getElementList(){
		return _elements;
	}

	public List<Class<? extends Object>> getTypeList() {
		return _types;
	}

	public void setElementList(List<Object> l) {
		_elements = l;
	}

	public void setTypeList(List<Class<? extends Object>> l) {
		_types = l;
	}



}
