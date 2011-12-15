package fr.insa.rennes.info.chemical.backend;

import java.util.List;

public interface ChemicalElement{
	public List<Object> getElementList();
	public List<Class<? extends Object>> getTypeList();
	public void setElementList(List<Object> l);
	public void setTypeList(List<Class<? extends Object>> l);
}
